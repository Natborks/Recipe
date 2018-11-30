package com.borketey.bk.recipe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRecipe extends AppCompatActivity {

    private final String ANONYMOUS = "anonymous";

    private EditText mTitle;
    private EditText mDescription;
    private EditText mDetails;
    private Button mPostButton;
    private String mUsername;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRecipeDatabasereference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        Intent intent = getIntent();
        mUsername = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Toast.makeText(AddRecipe.this, mUsername, Toast.LENGTH_SHORT).toString();

        mTitle = findViewById(R.id.input_title);
        mDescription = findViewById(R.id.input_description);
        mDetails = findViewById(R.id.input_preparation);
        mPostButton = findViewById(R.id.btn_save);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRecipeDatabasereference = mFirebaseDatabase.getReference().child("recipe");

        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Recipe recipe = new Recipe(mUsername, mDetails.getText().toString(),
                        mTitle.getText().toString(),
                        mDescription.getText().toString(),
                        null);

                mRecipeDatabasereference.push().setValue(recipe);

                mTitle.setText("");
                mDescription.setText("");
                mDetails.setText("");
            }
        });
    }

}
