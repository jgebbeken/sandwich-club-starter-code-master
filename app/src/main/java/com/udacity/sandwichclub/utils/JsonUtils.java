package com.udacity.sandwichclub.utils;


import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


// ParseSandwichJson Developed By Josh Gebbeken for Android Development Nanodegree. 4/25/2018


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

            // Checking to see if any data was added to the list. If there is no data put One String
            // with a space in it to fix any null pointer exceptions.
            if(alsoKnownAsList.isEmpty()) {
                alsoKnownAsList.add(" ");
            }

            // Now add alsoKnownAsList to the sandwich object
            sandwich.setAlsoKnownAs(alsoKnownAsList);



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

            // Make sure there are any ingredients in the list before proceeding. If the list is
            // empty, then put one empty String with a space in it to remove any null pointer exceptions
            if(ingredientsList.isEmpty()) {
                ingredientsList.add(" ");
            }
            sandwich.setIngredients(ingredientsList);


        } catch (JSONException e) {
            // Catch any errors if JSON caused an error
            e.printStackTrace();
            //Return null if you cannot continue
            return null;
        }


        return sandwich;
    }
}
