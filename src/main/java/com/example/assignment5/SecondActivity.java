package com.example.assignment5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Second activity page to display advice based on bmi
 *
 * @author Michael Cardoso
 * @author Shimank Dhondiyal
 */
public class SecondActivity extends AppCompatActivity {
    private double NORMAL = 18.5;
    private double OVERWEIGHT = 25;
    private double OBESE = 30;

    ImageView underweight;
    ImageView normal;
    ImageView overweight;
    ImageView obese;

    /**
     * onCreate method to display image when needed
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Advice based on the BMI");
        setContentView(R.layout.second_activity_main);
        underweight = (ImageView) findViewById(R.id.underweight);
        normal = (ImageView) findViewById(R.id.normal);
        overweight = (ImageView) findViewById(R.id.overweight);
        obese = (ImageView) findViewById(R.id.obese);

        Intent intent = this.getIntent();
        double bmi = intent.getDoubleExtra("BMI", 0);

        if(bmi >= OBESE) {
            obese.setVisibility(View.VISIBLE);
        }
        else if(bmi >= OVERWEIGHT) {
            overweight.setVisibility(View.VISIBLE);
        }
        else if(bmi >= NORMAL) {
            normal.setVisibility(View.VISIBLE);
        }
        else {
            underweight.setVisibility(View.VISIBLE);
        }
    }

    /**
     * onPuase method to clear the second activity when no longer needed
     * Fixes potential bug by calling onStop()
     */
    protected void onPause() {
        super.onPause();
        super.onStop();
    }
}