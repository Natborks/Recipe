package com.borketey.bk.recipe;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    // Member variables.
    private ArrayList<Recipe> recipeData;
    private Context mContext;
    private ImageView mRecipeImage;

    public RecipeAdapter (Context context, ArrayList<Recipe> recipe) {
        this.mContext = context;
        this.recipeData = recipe;
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeViewHolder(LayoutInflater.from(mContext).
        inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder recipeViewHolder, int position) {

        //Get current recipe
        Recipe currentRecipe = recipeData.get(position);

        //populate view with data
        recipeViewHolder.bindTo(currentRecipe);
    }

    @Override
    public int getItemCount() {
        return recipeData.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        //final RecipeAdapter mAdapter;

        // Member Variables for the TextViews
        public final TextView title;
        public final TextView description;


        //member Variables for buttons;
        //public Button like;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tileTextView);
            description = itemView.findViewById(R.id.descTextView);
            mRecipeImage = itemView.findViewById(R.id.recipeImage);

            itemView.setOnClickListener(this);
        }

        void bindTo(Recipe currentRecipe) {
            title.setText(currentRecipe.getTitle().toString());
            description.setText(currentRecipe.getDescription().toString());
            /*Glide.with(mContext).load(currentRecipe.getPhoto_url())
                    .into(mRecipeImage);*/

        }

        @Override
        public void onClick(View v) {
            Recipe currentRecipe = recipeData.get(getAdapterPosition());

            Intent detailIntent = new Intent(mContext, RecipeDetail.class);

            detailIntent.putExtra("title", currentRecipe.getTitle());
            detailIntent.putExtra("description", currentRecipe.getDescription());
            detailIntent.putExtra("image_url", currentRecipe.getPhoto_url());
            mContext.startActivity(detailIntent);
        }
    }
}
