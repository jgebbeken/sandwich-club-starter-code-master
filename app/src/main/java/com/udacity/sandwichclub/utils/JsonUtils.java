package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {


        //Create the sandwich object
        Sandwich sandwich = new Sandwich();

        // In the JSON the object has an JSON array called ingredients. We need a list to store this
        // Data. The AKA has another JSON array as well. So another list is needed.
        List<String> ingredientsList = new ArrayList<>();
        List<String> alsoKnownAsList = new ArrayList<>();



        //This JSON has an object within an object so we get the "name" JSON object.
        try {
            JSONObject sandwiches = new JSONObject(json).getJSONObject("name");

            //Set the main name
            sandwich.setMainName(sandwiches.getString("mainName"));



            // The array has a list of Strings for the AKA. We don't know how many there is so we
            // have to grab each of them if any.
            JSONArray sandwichArray = sandwiches.getJSONArray("alsoKnownAs");

            for(int i = 0; i < sandwichArray.length(); i++) {

                // Add item in the Object array.
                alsoKnownAsList.add(sandwichArray.getString(i));
            }

            // Checking to see if any data was added to the list. Add only if there is data.
            if(!alsoKnownAsList.isEmpty()) {
                sandwich.setAlsoKnownAs(alsoKnownAsList);
            }


            // We were done with object "name". Going back to the first object
            sandwiches = new JSONObject(json);

            //Set each item using the sandwich setter.
            sandwich.setPlaceOfOrigin(sandwiches.getString("placeOfOrigin"));
            sandwich.setDescription(sandwiches.getString("description"));
            sandwich.setImage(sandwiches.getString("image"));


            //Create the JSON ingredients array
            JSONArray sandwichIngredients = sandwiches.getJSONArray("ingredients");

            // Since we don't know how many ingredients will be in the array. We need loop in till
            // we have all of it.
            for(int i = 0; i < sandwichIngredients.length(); i++){

                ingredientsList.add(sandwichIngredients.getString(i));
            }

            // Make sure there are any ingredients in the list before proceeding
            if(!ingredientsList.isEmpty()) {
                sandwich.setIngredients(ingredientsList);
            }


        } catch (JSONException e) {
            // Catch any errors if JSON caused an error
            e.printStackTrace();
        }


        return sandwich;
    }
}
