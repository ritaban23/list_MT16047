package com.example.rivu.myapp2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    private ViewPager view;
    SwipeAdapter swipe;
    private int pos;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
// setting up the toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getting the postion of the item clicked
        Intent intent = getIntent();
// getting the position of the cards
        if (null != intent) {
            pos = getIntent().getIntExtra("Position", -1);

        }
        // setting the contents of the view pager
        view = (ViewPager) findViewById(R.id.viewpager);
        view.setPageTransformer(true, new ZoomOutPageTransformer());
        swipe = new SwipeAdapter(this);
        view.setAdapter(swipe);
        view.setCurrentItem(pos);


    }
}
