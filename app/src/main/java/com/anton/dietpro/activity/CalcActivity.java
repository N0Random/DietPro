package com.anton.dietpro.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anton.dietpro.R;
import com.anton.dietpro.models.CalcCalories;

public class CalcActivity extends AppCompatActivity {

    private EditText editAge;
    private EditText editWeight;
    private EditText editHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editAge = ((EditText)findViewById(R.id.editText6));
        editWeight = ((EditText)findViewById(R.id.editText5));
        editHeight = ((EditText)findViewById(R.id.editText11));
        SharedPreferences shared = getPreferences(MODE_PRIVATE);
        Integer myAge = shared.getInt("myAge", 0);
        Float myWeight = shared.getFloat("myWeight", 0);
        Float myHeight = shared.getFloat("myHeight", 0);
        if (myAge > 0){
            editAge.setText(String.valueOf(myAge));
        }
        if (myWeight > 0){
            editWeight.setText(String.valueOf(myWeight));
        }
        if (myHeight > 0){
            editHeight.setText(String.valueOf(myHeight));
        }
        Toast.makeText(getApplicationContext(), "age = " + myAge + ", weight = "
        + myWeight + ", height = " + myHeight, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            saveData();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void actionCalc(View v)
    {
        saveData();
        String age = editAge.getText().toString();
        String weight = editWeight.getText().toString();
        String height = editHeight.getText().toString();
        if (age.isEmpty()){
            return;
        }
        if (weight.isEmpty()){
            return;
        }
        if (height.isEmpty()){
            return;
        }
        int x = Integer.parseInt(age);
        int y = Integer.parseInt(weight);
        int z = Integer.parseInt(height);
        CalcCalories calc = new CalcCalories(x,y,z);
        calc.setAge(x);
        calc.setWeight(y);
        calc.setHeight(z);
        double cal = calc.calcMifflin();
        TextView textResult = ((TextView)findViewById(R.id.textResult));
        textResult.setText(String.valueOf("Ваша дневная норма БЖУ: " + cal));

    }

    private void saveData(){
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        Integer myAge = Integer.valueOf(editAge.getText().toString());
        Float myWeight = Float.valueOf(editWeight.getText().toString());
        Float myHeight = Float.valueOf(editHeight.getText().toString());
        if (myAge != null) {
            ed.putInt("myAge", myAge);
        }
        if(myWeight != null) {
            ed.putFloat("myWeight", myWeight);
        }
        if(myHeight != null) {
            ed.putFloat("myHeight", myHeight);
        }
        ed.apply();
    }

    @Override
    protected void onPause() {
        saveData();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}