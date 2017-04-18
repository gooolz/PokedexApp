package com.example.juliafoote.pokedexapphw1;

/**
 * Created by Gooolia on 10/07/2016.
 */

public class Poke
{
    //Return the resource ID representing the pokemon's name
    public int getmPokeName()
    {
        return mPokeName;
    }
    //Return the resource ID representing the pokemon's image
    public int getmPokeImage()
    {
        return mPokeImage;
    }

    //Constructor for a pokemon sets its name, image, CP, type, attack and evolution resources by parameter
    // and sets the location to "" and star rating to 0
    public Poke(int pokeName, int pokeImage, int pokeCP, int pokeType, int pokeAttack, int pokeEvo)
    {
        mPokeName = pokeName;
        mPokeImage = pokeImage;
        mPokeCP = pokeCP;
        mPokeType = pokeType;
        mPokeAttack = pokeAttack;
        mPokeEvo = pokeEvo;
        pokeRating = 0;
        mLocation = "";
    }

    //Return the resource ID representing the pokemon's CP
    public int getmPokeCP()
    {
        return mPokeCP;
    }

    //Return the resource ID representing the pokemon's type
    public int getmPokeType()
    {
        return mPokeType;
    }

    //Return the resource ID representing the pokemon's attack
    public int getmPokeAttack()
    {
        return mPokeAttack;
    }

    //Return the resource ID representing the pokemon's evolution
    public int getmPokeEvo()
    {
        return mPokeEvo;
    }

    //Return the resource ID representing the pokemon's star rating
    public float getPokeRating() {
        return pokeRating;
    }

    //Set the resource ID representing the pokemon's star rating
    public void setPokeRating(float pokeRating) {
        this.pokeRating = pokeRating;
    }

    //Return the resource ID representing the pokemon's location
    public String getLocation() {
        return mLocation;
    }

    //Set the resource ID representing the pokemon's location
    public void setLocation(String location) {
        this.mLocation = location;
    }


    //Refers to the strings defined in strings.xml
    private int mPokeName;
    private int mPokeImage;
    private int mPokeCP;
    private int mPokeType;
    private int mPokeAttack;
    private int mPokeEvo;
    private float pokeRating;
    private String mLocation;
}
