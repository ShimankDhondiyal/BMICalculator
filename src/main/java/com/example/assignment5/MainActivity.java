package com.example.assignment5;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * Main activity page to allow input and calculate BMI
 *
 * @author Michael Cardoso
 * @author Shimank Dhondiyal
 */
public class MainActivity extends AppCompatActivity {
    private int ENGLISH_CONSTANT = 703;
    RadioGroup radioGroup;
    EditText weight, height;
    Button calculate, advice;
    RadioButton english, metric;
    TextView output;
    double BMIMetric;
    double BMIEnglish;

    /**
     * onCreate method to instantiate variables
     * @param savedInstanceState    Previous instance of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("BMI Calculator");
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup) findViewById(R.id.rad_group);
        english = (RadioButton) findViewById(R.id.radioButton_english);
        metric = (RadioButton) findViewById(R.id.radioButton_metric);
        weight = (EditText) findViewById(R.id.editText_weight);
        height = (EditText) findViewById(R.id.editText_height);
        calculate = (Button) findViewById(R.id.button_calculate);
        advice = (Button) findViewById(R.id.button_advice);
        output = (TextView) findViewById(R.id.textView_output);
    }

    /**
     * onResume method to be called whenever app is resumed
     * Handles onclick events
     */
    protected void onResume() {
        super.onResume();
        /**
         * Change text hints based on metric/english button
         */
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton_english) {
                    weight.setHint("Enter weight in pounds");
                    height.setHint("Enter height in inches");
                }
                if (checkedId == R.id.radioButton_metric) {
                    weight.setHint("Enter weight in kilograms");
                    height.setHint("Enter height in meters");
                }
            }
        });

        /**
         * Calculates BMI when button is clicked by calling getBMI()
         */
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBMI(v);
            }
        });

        /**
         * recalculates BMI (if info changed), calls second activity
         */
        advice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBMI(v);
                if(weight.getText().toString() == "" || height.getText().toString() == ""
                 || !(english.isChecked() || metric.isChecked())) {
                    showMissingInfoToast(v);
                    return;
                }
                if(output.getText().toString() == "") {
                    showCalculateBMIToast(v);
                    return;
                }
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                if(BMIEnglish != 0) {
                    intent.putExtra("BMI", BMIEnglish);
                    startActivity(intent);
                }
                if(BMIMetric != 0) {
                    intent.putExtra("BMI", BMIMetric);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Calculates BMI
     * @param v view
     */
    public void getBMI(View v) {
        //english
        if(english.isChecked()) {
            String tempW = weight.getText().toString();
            String tempH = height.getText().toString();
            double w = 0;
            double h = 0;
            if(!"".equals(tempW) && !"".equals(tempH)) {
                w = Double.parseDouble(tempW);
                h = Double.parseDouble(tempH);
            }
            else {
                showMissingInfoToast(v);
                return;
            }
            BMIEnglish = ((w*ENGLISH_CONSTANT)/(h*h));
            String out = String.format("%.2f", BMIEnglish);
            output.setText(out);
        }
        //metric
        else if(metric.isChecked()){
            String tempW = weight.getText().toString();
            String tempH = height.getText().toString();
            double w = 0;
            double h = 0;
            if(!"".equals(tempW) && !"".equals(tempH)) {
                w = Double.parseDouble(tempW);
                h = Double.parseDouble(tempH);
            }
            else {
                showMissingInfoToast(v);
                return;
            }
            BMIMetric = (w/(h*h));
            String out = String.format("%.2f", BMIMetric);
            output.setText(out);
        }
        else {
            Toast toast = Toast.makeText(this, "Select a measurement", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Error message: when BMI not calculated
     * @param view  view
     */
    public void showCalculateBMIToast(View view) {
        Toast toast = Toast.makeText(this, "Need to calculate BMI first", Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Error message: when info is missing
     * @param view
     */
    public void showMissingInfoToast(View view) {
        Toast toast = Toast.makeText(this, "Fill in all the information", Toast.LENGTH_SHORT);
        toast.show();
    }

}
