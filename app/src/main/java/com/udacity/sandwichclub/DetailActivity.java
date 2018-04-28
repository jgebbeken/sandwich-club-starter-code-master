package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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
    private String items;

    // Setup the TextViews
    private TextView tvDescription;
    private TextView tvOrigin;
    private TextView tvAka;
    private TextView tvIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        tvDescription = findViewById(R.id.description_tv);
        tvOrigin = findViewById(R.id.origin_tv);
        tvAka = findViewById(R.id.also_known_tv);
        tvIngredients = findViewById(R.id.ingredients_tv);



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



        // Set the TextViews
        tvDescription.setText(sandwich.getDescription());
        tvOrigin.setText(sandwich.getPlaceOfOrigin());

        // Need a List of String for the AKA.
        List<String> akaList =  sandwich.getAlsoKnownAs();

        // Once AKA list is completed extracting in the StringBuilder set text in UI.
        tvAka.setText(extractedList(akaList));

        // Create a new List of Strings for ingredients.
        List<String> ingredientsList = sandwich.getIngredients();

        // Once the StringBuilder operation is complete place it in the UI.
        tvIngredients.setText(extractedList(ingredientsList));



    }

    public String extractedList (List<String> sandwiches) {

        String finished ="";

        // Get a StringBuilder object to start extracting the List of Strings
        StringBuilder extractedAKAList = new StringBuilder();

        for(int i = 0; i < sandwiches.size(); i++) {

            extractedAKAList.append(sandwiches.get(i));

            // If the sandwich has multiple AKAs add commas and spacing but only if it's not the
            // end
            if(i < sandwiches.size() -1){
                extractedAKAList.append(", ");
            }
            finished = extractedAKAList.toString();


        }
        return finished;
    }

}
