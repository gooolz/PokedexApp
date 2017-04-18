package com.example.juliafoote.pokedexapphw1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.text.TextWatcher;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.*;

public class PokeActivity extends AppCompatActivity {

    //declared variables and fields
    private float starIndex;
    private int editIndex;
    private int mCurrentIndex = 0;
    private final String TAG = "pokémon";
    private final String FILENAME = "poke_data";
    private static final String KEY_POKE_INDEX = "poke_index";
    private static final String stars = "poke_stars";
    private static final String textChange = "poke_edit";

    //views, buttons and rating bar
    private ImageView mPokeImage;
    private TextView mPokeName;
    private TextView mPokeType, mPokeAttack, mPokeEvo;
    private EditText mPokeCP;
    private RatingBar ratingBar;


    //array of 9 different pokemon
    private final Poke[] mPokémon = new Poke[]
            {
                 new Poke(R.string.bulbasaurPokemon, R.drawable.bulbasaur, R.string.bulbasaurCP, R.string.bulbasaurType, R.string.bulbasaurAttacks, R.string.bulbasaurEvo),
                 new Poke(R.string.charmanderPokemon, R.drawable.charmander, R.string.charmanderCP, R.string.charmanderType, R.string.charmanderAttacks, R.string.charmanderEvo),
                 new Poke(R.string.eeveePokemon, R.drawable.eevee, R.string.eeveeCP, R.string.eeveeType, R.string.eeveeAttacks, R.string.eeveeEvo),
                 new Poke(R.string.growlithePokemon, R.drawable.growlithe, R.string.growlitheCP, R.string.growlitheType, R.string.growlitheAttacks, R.string.growlitheEvo),
                 new Poke(R.string.laprasPokemon, R.drawable.lapras, R.string.laprasCP, R.string.laprasType, R.string.laprasAttacks, R.string.laprasEvo),
                 new Poke(R.string.raichuPokemon, R.drawable.raichu, R.string.raichuCP, R.string.raichuType, R.string.raichuAttacks, R.string.raichuEvo),
                 new Poke(R.string.slowpokePokemon, R.drawable.slowpoke, R.string.slowpokeCP, R.string.slowpokeType, R.string.slowpokeAttacks, R.string.slowpokeEvo),
                 new Poke(R.string.squirtlePokemon, R.drawable.squirtle, R.string.squirtleCP, R.string.squirtleType, R.string.squirtleAttacks, R.string.squirtleEvo),
                 new Poke(R.string.wigglytuffPokemon, R.drawable.wigglytuff, R.string.wigglytuffCP, R.string.wigglytuffType, R.string.wigglytuffAttacks, R.string.wigglytuffEvo)
             };


    //initializes the pokemon app activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke);

        try(FileInputStream in = openFileInput(FILENAME))
        {
            String line;
            char next;

            for(int i = 0; i < 9; i++)
            {
                //first initializes line then increments it after each loop
                line = "";
                //loop to set the location of the pokemon
                while ((next = (char) in.read()) != '\n')
                {
                    line += next;
                }
                mPokémon[i].setLocation(line);
                //initializes line so that it doesn't have the location value in it anymore
                line = "";
                //loop to get the user rating for the rating bar
                while ((next = (char) in.read()) != '\n')
                {
                    line += next;
                }
                Float f = new Float(10);
                mPokémon[i].setPokeRating(f.parseFloat(line));

                Log.d(TAG, "Read the following location from file: " + line);
            }
            in.close();
        }
        catch (java.io.FileNotFoundException fnfe)
        {
            Log.d(TAG, "FileNotFoundException when trying to load file" + fnfe);
        }
        catch(java.io.IOException ioe)
        {
            Log.d(TAG, "IOException when trying to load file" + ioe);
        }

        //saves the state when onCreate is called again
        if (savedInstanceState != null)
        {
            mCurrentIndex = savedInstanceState.getInt(KEY_POKE_INDEX, 0);
            starIndex = savedInstanceState.getInt(stars, 0);
            editIndex = savedInstanceState.getInt(textChange,0);
        }

        //sets the attributes for the views
        Button mPokeNext = (Button) findViewById(R.id.nextButton);
        Button mPokePrevious = (Button) findViewById(R.id.prevButton);
        mPokeImage = (ImageView) findViewById(R.id.pokeImageView);
        mPokeName = (TextView) findViewById(R.id.pokeNameTextView);
        mPokeType = (TextView) findViewById(R.id.pokeTypeDef);
        mPokeAttack = (TextView) findViewById(R.id.pokeAttackDef);
        mPokeEvo = (TextView) findViewById(R.id.pokeEvoDef);
        ratingBar = (RatingBar) findViewById(R.id.ratingBarView);
        mPokeCP = (EditText) findViewById(R.id.cpEditText);

        mPokeCP.addTextChangedListener(cpEditTextWatcher);
        //set CP and location for EditText
        mPokeCP.setText(mPokémon[mCurrentIndex].getLocation());
        mPokeCP.setText(mPokémon[mCurrentIndex].getmPokeCP());

        //calls the update function to give appropriate attributes for current state
        update();

        //sets the ratingBar listener to be called when the rating changes
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
            {
                mPokémon[mCurrentIndex].setPokeRating(rating);
            }
        });

        //sets the actions to be performed when the next button is clicked
        mPokeNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCurrentIndex = (mCurrentIndex + 1) % 9;

                update();

                mPokeCP.setText(mPokémon[mCurrentIndex].getLocation());
            }
        });

        //sets the actions to be performed when the previous button is clicked
        mPokePrevious.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCurrentIndex = (mCurrentIndex - 1);

                if(mCurrentIndex == -1)
                    mCurrentIndex = 8;

                update();

                mPokeCP.setText(mPokémon[mCurrentIndex].getLocation());
            }
        });

    }
    //final call received before the activity is destroyed
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");

        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(FILENAME, Context.MODE_PRIVATE);

            for(int i = 0; i < 9; i++)
            {
                //writes each 9 pokemon locations to a new line
                outputStream.write((mPokémon[i].getLocation() + '\n').getBytes());
                outputStream.write((Float.valueOf(mPokémon[i].getPokeRating()).toString() + '\n').getBytes());
                Log.d(TAG, "Writing location: " + mPokémon[i].getLocation() + '\n');
            }
            outputStream.close();
        } catch (FileNotFoundException fnfe)
        {
            Log.d(TAG, "FileNotFound Exception when trying to write output");
        } catch (IOException ioe)
        {
            Log.d(TAG, "IOException when trying to write output");
        }
    }

    //TextWatcher for EditText: cpEditText
    private final TextWatcher cpEditTextWatcher = new TextWatcher()
    {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            mPokémon[mCurrentIndex].setLocation(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    };
    //saves which pokemon it is currently on
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_POKE_INDEX, mCurrentIndex);
        savedInstanceState.putFloat(stars, starIndex);
        savedInstanceState.putInt(textChange, editIndex);

    }
    //updates each pokemon attribute when the next/previous buttons are clicked and when textWatcher is used
    private void update()
    {
        mPokeImage.setImageResource(mPokémon[mCurrentIndex].getmPokeImage());
        mPokeName.setText(mPokémon[mCurrentIndex].getmPokeName());
        mPokeType.setText(mPokémon[mCurrentIndex].getmPokeType());
        mPokeAttack.setText(mPokémon[mCurrentIndex].getmPokeAttack());
        mPokeEvo.setText(mPokémon[mCurrentIndex].getmPokeEvo());
        ratingBar.setRating(mPokémon[mCurrentIndex].getPokeRating());
        mPokeCP.setText(mPokémon[mCurrentIndex].getLocation());
    }
}