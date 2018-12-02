package com.borketey.bk.recipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FoodList extends AppCompatActivity {

    private String fried = "fried rice";
    private String groundnut = "groundnut";
    private String kimchi = "kimchi";
    private String whiteGourd = "white gourd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
    }

    public void fried(View view) {
        Intent intent = new Intent(getApplicationContext(), ChefsDetail.class);
        intent.putExtra("food", fried);
        getApplication().startActivity(intent);
    }

    public void groundnut(View view) {
        Intent intent = new Intent(getApplicationContext(), ChefsDetail.class);
        intent.putExtra("food", groundnut);
        getApplication().startActivity(intent);
    }

    public void kimchi(View view) {
        Intent intent = new Intent(getApplicationContext(), ChefsDetail.class);
        intent.putExtra("food", kimchi);
        getApplication().startActivity(intent);
    }

    public void white_gourd_soup(View view) {
        Intent intent = new Intent(getApplicationContext(), ChefsDetail.class);
        intent.putExtra("food", whiteGourd);
        getApplication().startActivity(intent);
    }
}
