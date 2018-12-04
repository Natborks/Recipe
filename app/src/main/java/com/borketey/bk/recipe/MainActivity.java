package com.borketey.bk.recipe;
//sem project

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    // Choose an arbitrary request code value
    private static final int RC_SIGN_IN = 123;

    public static final String EXTRA_MESSAGE =
            "com.example.android.twoactivities.extra.MESSAGE";

    // Member variables.
    private RecyclerView mRecyclerView;
    private ArrayList<Recipe> mRecipeData;
    private RecipeAdapter mAdapter;
    private Context mContext;
    private String username;
    private EditText searchView;
    TextWatcher amountEditTextWatcher;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRecipeDatabasereference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    private final String ANONYMOUS = "anonymous";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = MainActivity.this;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mRecipeDatabasereference = mFirebaseDatabase.getReference().child("recipe");

        // Initialize the RecyclerView.
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        //set the layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //Initialize the ArrayList that will contain the data
        mRecipeData = new ArrayList<>();

        //Initialize the adapter and set it to the recyclerView.
        mAdapter = new RecipeAdapter(this, mRecipeData);
        mRecyclerView.setAdapter(mAdapter);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if(firebaseUser != null) {
                    //user signed in
                    onSignedInInitialize(firebaseUser.getDisplayName());
                    Toast.makeText(MainActivity.this, firebaseUser.getDisplayName(), Toast.LENGTH_SHORT).show();
                } else {
                    //user signed out
                    onSignedOutCleaned();
                    startActivityForResult(
                            // Get an instance of AuthUI based on the default app
                            AuthUI.getInstance()
                            .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()
                                    ))
                                    .build()
                                    ,RC_SIGN_IN);
                }
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addRecipe = new Intent(mContext, AddRecipe.class);
                addRecipe.putExtra(EXTRA_MESSAGE, username);
                mContext.startActivity(addRecipe);
            }
        });
    }

    private void onSignedInInitialize(String displayName) {
        username = displayName;
        attachDatabaseReadListener();
    }

    private void onSignedOutCleaned() {
        username = ANONYMOUS;
        mRecipeData.clear();
        detachDatabaseReadListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                //signed out
                AuthUI.getInstance().signOut(this);
                return true;
            case R.id.ChefSpecial:
                Intent intent = new Intent(mContext, FoodList.class);
                mContext.startActivity(intent);
                return true;
            case R.id.search:
                Intent searchIntent = new Intent(mContext, search.class);
                mContext.startActivity(searchIntent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "signed-in", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "sign-in cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }

        detachDatabaseReadListener();
        mRecipeData.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void attachDatabaseReadListener() {

        if(mChildEventListener == null) {

            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {

                            Recipe recipe = dataSnapshot.getValue(Recipe.class);
                            mRecipeData.add(new Recipe(recipe.getUsername(),recipe.getDescription(),
                                    recipe.getTitle(),recipe.getMethod(),recipe.getPhoto_url(), recipe.getLikes()));

                            // Notify the adapter of the change.
                            mAdapter.notifyDataSetChanged();

                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    // Notify the adapter of the change.
                    mAdapter.notifyDataSetChanged();
                }
                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {            }
                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {            }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {            }
            };

            mRecipeDatabasereference.addChildEventListener(mChildEventListener);
        }
    }

    private void detachDatabaseReadListener() {
        if(mChildEventListener != null) {
            mRecipeDatabasereference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }

    }

    public void search () {

    }

}
