package com.borketey.bk.recipe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    // Member variables.
    private ArrayList<Recipe> recipeData;
    private Context mContext;
    private Recipe currentRecipe;

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
        currentRecipe = recipeData.get(position);

        //recipeViewHolder.update(currentRecipe, String.valueOf(2));

            //populate view with data
        recipeViewHolder.bindTo(currentRecipe);
    }

    @Override
    public int getItemCount() {
        return recipeData.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        //private FirebaseDatabase mFirebaseDatabase;
        //private DatabaseReference mRecipeDatabasereference;

        //final RecipeAdapter mAdapter;

        // Member Variables for the TextViews
        public final TextView title;
        public final TextView description;
        public final TextView likes;
        public ImageView mRecipeImage;
        public ImageView likeButton;

        //member Variables for buttons;
        //public Button like;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tileTextView);
            description = itemView.findViewById(R.id.descTextView);
            likes = itemView.findViewById(R.id.favCount);
            mRecipeImage = itemView.findViewById(R.id.recipeImage);
            likeButton= itemView.findViewById(R.id.favourite);

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentRecipe.setLikes("2");
                    likeButton.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);

                }
            });

           // mFirebaseDatabase = FirebaseDatabase.getInstance();
           // mRecipeDatabasereference = mFirebaseDatabase.getReference();

            /*mRecipeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // update(currentRecipe, String.valueOf(2));
                    Toast.makeText(mContext, "th", Toast.LENGTH_SHORT).show();
                }
            });*/

            itemView.setOnClickListener(this);
        }

        /*public void update(final Recipe recipe, final String likes) {
            recipe.setLikes(likes);
            /*mRecipeDatabasereference.child("recipe").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String key = dataSnapshot.getKey();

                    mRecipeDatabasereference.child(key)
                            .child("likes")
                            .setValue(recipe.getLikes());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }*/

        void bindTo(Recipe currentRecipe) {

            boolean isPhoto = currentRecipe.getPhoto_url() != null;

            if (isPhoto) {
                title.setText(currentRecipe.getTitle().toString());
                description.setText(currentRecipe.getDescription().toString());
                likes.setText(currentRecipe.getLikes().toString());
                //Glide.with(mRecipeImage.getContext()).load(currentRecipe.getPhoto_url())
                 //       .into(mRecipeImage);
            } else {
                title.setText(currentRecipe.getTitle().toString());
                description.setText(currentRecipe.getDescription().toString());
                likes.setText(currentRecipe.getLikes().toString());
            }
        }

        @Override
        public void onClick(View v) {
            Recipe currentRecipe = recipeData.get(getAdapterPosition());

            Intent detailIntent = new Intent(mContext, RecipeDetail.class);

            detailIntent.putExtra("title", currentRecipe.getTitle());
            detailIntent.putExtra("description", currentRecipe.getDescription());
            //detailIntent.putExtra("image_url", currentRecipe.getPhoto_url());
            mContext.startActivity(detailIntent);
        }
    }
}
