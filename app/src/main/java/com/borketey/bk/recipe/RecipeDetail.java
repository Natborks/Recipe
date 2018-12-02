package com.borketey.bk.recipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class RecipeDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        TextView recipeTitle = findViewById(R.id.titleDetail);
        ImageView recipeImage = findViewById(R.id.imageDetail);

        recipeTitle.setText(getIntent().getStringExtra("title"));

       Glide.with(recipeImage.getContext()).load(getIntent().getStringExtra("image_url"))
                .into(recipeImage);
    }
}
