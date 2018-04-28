package com.udacity.sandwichclub.utils;


import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;




// ParseSandwichJson Developed By Josh Gebbeken for Android Development Nanodegree. 4/25/2018


public class JsonUtils {

    private final static String NAME = "name";
    private final static String MAIN_NAME = "mainName";
    private final static String ALSO_KNOWN_AS = "alsoKnownAs";
    private final static String POE = "placeOfOrigin";
    private final static String DESCRIPTION = "description";
    private final static String IMAGE = "image";
    private final static String INGREDIENTS = "ingredients";



    public static Sandwich parseSandwichJson(String json) {




        //Create the sandwich object
        Sandwich sandwich = new Sandwich();

        // In the JSON the object has an JSON array called ingredients. We need a list to store this
        // Data. The AKA has another JSON array as well. So another list is needed.
        List<String> ingredientsList;
        List<String> alsoKnownAsList;



        //This JSON has an object within an object so we get the "name" JSON object.
        try {
            JSONObject sandwiches = new JSONObject(json).getJSONObject(NAME);

            //Set the main name
            sandwich.setMainName(sandwiches.getString(MAIN_NAME));



            // The array has a list of Strings for the AKA. We don't know how many there is so we
            // have to grab each of them if any.
            JSONArray sandwichArray = sandwiches.getJSONArray(ALSO_KNOWN_AS);

            alsoKnownAsList = extractList(sandwichArray);

            // Now add alsoKnownAsList to the sandwich object
            sandwich.setAlsoKnownAs(alsoKnownAsList);



            // We were done with object "name". Going back to the first object
            sandwiches = new JSONObject(json);

            //Set each item using the sandwich setter.
            sandwich.setPlaceOfOrigin(sandwiches.getString(POE));
            sandwich.setDescription(sandwiches.getString(DESCRIPTION));
            sandwich.setImage(sandwiches.getString(IMAGE));


            //Create the JSON ingredients array
            JSONArray sandwichIngredients = sandwiches.getJSONArray(INGREDIENTS);

            // Since we don't know how many ingredients will be in the array. We need loop in till
            // we have all of it.
            ingredientsList = extractList(sandwichIngredients);

            sandwich.setIngredients(ingredientsList);


        } catch (JSONException e) {
            // Catch any errors if JSON caused an error
            e.printStackTrace();
            //Return null if you cannot continue
            return null;
        }


        return sandwich;
    }

    private static List<String> extractList(JSONArray incoming) {

        List<String> extracted = new ArrayList<>();
        for (int i = 0; i < incoming.length(); i++) {

            // Add item in the Object array.
            try {
                extracted.add(incoming.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        if (extracted.isEmpty()){
            extracted.add(" ");
        }

        return extracted;
    }
}
