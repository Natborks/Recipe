package com.borketey.bk.recipe;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class search extends AppCompatActivity {

    private EditText search;
    private Button mSearchButton;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRecipeDatabasereference;
    private SearchAdapter mSearchAdapter;
    private ListView mMessageListView;
    ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = findViewById(R.id.searchEdit);
        mSearchButton = findViewById(R.id.searchButton);
        mMessageListView = (ListView) findViewById(R.id.messageListView);

        List<Recipe> recipes = new ArrayList<>();
        mSearchAdapter = new SearchAdapter(this, R.layout.item_message, recipes);
        mMessageListView.setAdapter(mSearchAdapter);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRecipeDatabasereference = mFirebaseDatabase.getReference().child("recipe");
    }

    public void lookup(View view) {
        String query = search.getText().toString();

        doSearch(query);
    }

    private void doSearch (final String query) {
        mRecipeDatabasereference.orderByChild("id").equalTo(query);

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Recipe recipe = dataSnapshot.getValue(Recipe.class);
                mSearchAdapter.add(recipe);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mRecipeDatabasereference.addChildEventListener(mChildEventListener);
    }

}
