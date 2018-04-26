package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // Setup the TextViews
        TextView tvDescription = findViewById(R.id.description_tv);
        TextView tvOrigin = findViewById(R.id.origin_tv);
        TextView tvAka = findViewById(R.id.also_known_tv);
        TextView tvIngredients = findViewById(R.id.ingredients_tv);

        // Set the TextViews
        tvDescription.setText(sandwich.getDescription());
        tvOrigin.setText(sandwich.getPlaceOfOrigin());

        // Need a List of String for the AKA.
        List<String> akaList =  sandwich.getAlsoKnownAs();

        // Get a StringBuilder object to start extracting the List of Strings
        StringBuilder extractedAKAList = new StringBuilder();

        for(int i = 0; i < akaList.size(); i++) {

            extractedAKAList.append(akaList.get(i));

            // If the sandwich has multiple AKAs add commas and spacing but only if it's not the
            // end
            if(i < akaList.size() -1){
                extractedAKAList.append(", ");
            }

        }

        // Once AKA list is completed extracting in the StringBuilder set text in UI.
        tvAka.setText(extractedAKAList.toString());

        // Create a new StringBuilder object for the ingredients
        StringBuilder extractedIngredients = new StringBuilder();

        // Create a new List of Strings for ingredients.
        List<String> ingredientsList = sandwich.getIngredients();

        for(int i = 0; i < ingredientsList.size(); i++ ) {

            extractedIngredients.append(ingredientsList.get(i));

            // If there are multiple ingredients in the list. Put it on the next line.
            if(i < ingredientsList.size() -1) {
                extractedIngredients.append("\n");
            }
        }

        // Once the StringBuilder operation is complete place it in the UI.
        tvIngredients.setText(extractedIngredients.toString());



    }
}
