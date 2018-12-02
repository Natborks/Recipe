package com.borketey.bk.recipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ChefsDetail extends AppCompatActivity {

    private TextView mHeaderTextView;
    private TextView mSubHeaderTextView;
    private TextView mBody;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chefs_detail);

        mHeaderTextView = findViewById(R.id.heading);
        mSubHeaderTextView = findViewById(R.id.subheading);
        mBody = findViewById(R.id.article);

        Intent receiver = getIntent();

        String food =  receiver.getStringExtra("food");

        if(receiver.getStringExtra("food").equals("fried rice")) {
            mSubHeaderTextView.setText(getResources().getString(R.string.fried_rice_title));
            mBody.setText(getResources().getString(R.string.fried_rice_recipe));

        } else if(receiver.getStringExtra("food").equals("groundnut")) {
            mSubHeaderTextView.setText(getResources().getString(R.string.groundnut_title));
            mBody.setText(getResources().getString(R.string.groundnut_recipe));

        } else if(receiver.getStringExtra("food").equals("kimchi")) {
            mSubHeaderTextView.setText(getResources().getString(R.string.kimchi_title));
            mBody.setText(getResources().getString(R.string.kimchi));

        } else if(receiver.getStringExtra("food").equals("white gourd")) {
            mSubHeaderTextView.setText(getResources().getString(R.string.white_gourd_title));
            mBody.setText(getResources().getString(R.string.white_gourd_recipe));
        }
    }
}
