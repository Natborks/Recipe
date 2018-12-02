package com.borketey.bk.recipe;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.ref.SoftReference;

public class AddRecipe extends AppCompatActivity {

    private final String ANONYMOUS = "anonymous";

    private static final int  RC_PHOTO_PICKER = 1;

    Uri mImageUri = null;

    private EditText mTitle;
    private EditText mDescription;
    private EditText mDetails;
    private Button mPostButton;
    private ImageButton mAddImage;
    private String mUsername;;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRecipeDatabasereference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        Intent intent = getIntent();
        mUsername = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        mTitle = findViewById(R.id.input_title);
        mDescription = findViewById(R.id.input_description);
        mDetails = findViewById(R.id.input_preparation);
        mPostButton = findViewById(R.id.btn_save);
        mAddImage = findViewById(R.id.imageSelect);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage =  FirebaseStorage.getInstance();
        mRecipeDatabasereference = mFirebaseDatabase.getReference().child("recipe");
        mStorageReference = mFirebaseStorage.getReference().child("recipe_photos");

        mAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                //intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });


    }

    private void startPosting() {

        final String description = mDescription.getText().toString().trim();
        final String title = mTitle.getText().toString().trim();
        final String details = mDetails.getText().toString();

        if (!TextUtils.isEmpty(description) && !TextUtils.isEmpty(title)
                && !TextUtils.isEmpty(details) && mImageUri != null) {

            StorageReference filepath = mStorageReference.child("recipe_photos").child(mImageUri.getLastPathSegment());


            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = Uri.parse(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());

                    /*DatabaseReference databaseReference = mRecipeDatabasereference.push();

                    databaseReference.child("username").setValue(mUsername);
                    databaseReference.child("title").setValue(title);
                    databaseReference.child("description").setValue(description);
                    databaseReference.child("method").setValue(details);
                    databaseReference.child("photo_url").setValue(downloadUrl.toString());*/

                    Recipe recipe = new Recipe(mUsername, mDetails.getText().toString(),
                            mTitle.getText().toString(),
                            mDescription.getText().toString(),
                            downloadUrl.toString(), String.valueOf(0));

                    mRecipeDatabasereference.push().setValue(recipe);



                    startActivity(new Intent(AddRecipe.this, MainActivity.class));

                }
            });

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            mAddImage.setImageURI(mImageUri);
        }
    }

    public void submit(View view) {
        Toast.makeText(getApplicationContext(), "submitting", Toast.LENGTH_SHORT).show();
        startPosting();
    }
}
