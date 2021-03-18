//==========================================================================
//
// Application: Bike-bistro
// Activity:    Activity Meal
// Course:      CSC 4330
// Homework:    2
// Author:      MD Bakhtiar R Akhib
// Date:        03/03/2021
// Description: This is an android app for Bike Bistro that lets user to show the menu and let them
//                 choose from it and also select the delivery options.
//===========================================================================
package edu.wsu.bikebistro;

//Importing packages
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;

//---------------------------------
// class ActMeal
//---------------------------------
public class ActMeal extends AppCompatActivity {

    //-------------------------------------------
    // Constants and variables
    //-------------------------------------------
    private String sharedEntrees;
    private String sharedDrinks;
    private String sharedDesserts;
    private String sharedTotal;

    private EditText txtViewEntrees;
    private EditText txtViewDrinks;
    private EditText txtViewDesserts;
    private EditText txtViewTotal;
    private Toolbar tbrMain;
    private Spinner spEntrees;
    private Spinner spDrinks;
    private Spinner spDesserts;
    ArrayList<String> alEntrees = new ArrayList<String>();
    ArrayList<String> alDrinks = new ArrayList<String>();
    ArrayList<String> alDesserts = new ArrayList<String>();
    public double Total = 0.00;

    //Setting the array for the menu
    public String[][] spMenu = {
            {"Entree", "Select Entree", "0.00"},
            {"Entree", "Garlic Shrimp", "17.99"},
            {"Entree", "Alfredo Chicken", "12.99"},
            {"Entree", "Stir Fry", "12.99"},
            {"Entree", "Lamb Chops", "20.99"},
            {"Entree", "Twin Lobster", "19.99"},
            {"Drink", "Select Drinks", "0.00"},
            {"Drink", "Pepsi", "1.99"},
            {"Drink", "Sprite", "1.99"},
            {"Drink", "Hot Chocolate", "2.99"},
            {"Drink", "Milk Shake", "3.99"},
            {"Drink", "Iced Tea", "2.99"},
            {"Dessert", "Select Dessert ", "0.00"},
            {"Dessert", "Cheesecake", "4.99"},
            {"Dessert", "Rice Pudding", "2.99"},
            {"Dessert", "Fruit Pie", "3.99"},
            {"Dessert", "Ice Cream", "1.99"},
            {"Dessert", "Bread Pudding", "3.99"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laymeal);

        //Getting the ids by value and storing them into objects
        spEntrees = findViewById(R.id.spEntrees);
        spDrinks = findViewById(R.id.spDrinks);
        spDesserts = findViewById(R.id.spDesserts);
        txtViewEntrees = findViewById(R.id.txtViewEntrees);
        txtViewDrinks = findViewById(R.id.txtViewDrinks);
        txtViewDesserts = findViewById(R.id.txtViewDesserts);
        txtViewTotal = findViewById(R.id.txtViewTotal);

        //Adding a custom toolbar along with image
        tbrMain = findViewById(R.id.tbrMain);
        setSupportActionBar(tbrMain);
        tbrMain.setNavigationIcon(R.mipmap.ic_launcher_menu);

        //Setting up the Entree Spinner
        alEntrees = new ArrayList<String>();
        for(int i = 0; i < spMenu.length; i++){
            if(spMenu[i][0] == "Entree"){
                alEntrees.add(spMenu[i][1] + ", $" + spMenu[i][2]);
                System.out.println(spMenu[i][1] + ", $" + spMenu[i][2]);
            }
        }

        // Define spinner adapter
        ArrayAdapter<String> spAdapterEntrees =
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_item,
                        alEntrees);
        spAdapterEntrees.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spEntrees.setAdapter(spAdapterEntrees);

        // Define spinner listener
        spEntrees.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                    AdapterView<?> adapterView, View view, int i, long l)
            {
                System.out.println(String.valueOf(spEntrees.getSelectedItem()));
                String[] Splitter = (String.valueOf(spEntrees.getSelectedItem())).split("[,$]");
                double price = Double.parseDouble(Splitter[2]);
                Total = Total + price;
                txtViewEntrees.setText("$" + String.valueOf(price));
                txtViewTotal.setText("$" + (String.format("%.2f",Total)));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        //Setting up the Drinks spinner
        alDrinks = new ArrayList<String>();
        for(int i = 0; i < spMenu.length; i++){
            if(spMenu[i][0] == "Drink"){
                alDrinks.add(spMenu[i][1] + ", $" + spMenu[i][2]);
            }
        }

        // Define spinner adapter
        ArrayAdapter<String> spAdapterDrinks =
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_item,
                        alDrinks);
        spAdapterDrinks.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spDrinks.setAdapter(spAdapterDrinks);

        // Define spinner listener
        spDrinks.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(
                        AdapterView<?> adapterView, View view, int i, long l)
                {
                    String[] Splitter = (String.valueOf(spDrinks.getSelectedItem())).split("[,$]");
                    double price = Double.parseDouble(Splitter[2]);
                    Total = Total + price;
                    txtViewDrinks.setText("$" + String.valueOf(price));
                    txtViewTotal.setText("$" + (String.format("%.2f",Total)));
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView)
                {

            }
        });

        //Setting up the Dessert spinner
        alDesserts = new ArrayList<String>();
        for(int i = 0; i < spMenu.length; i++){
            if(spMenu[i][0] == "Dessert"){
                alDesserts.add(spMenu[i][1] + ", $" + spMenu[i][2]);
            }
        }

        // Define spinner adapter
        ArrayAdapter<String> spAdapterDesserts =
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_item,
                        alDesserts);
        spAdapterDesserts.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spDesserts.setAdapter(spAdapterDesserts);

        // Define spinner listener
        spDesserts.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                    AdapterView<?> adapterView, View view, int i, long l)
            {
                String[] Splitter = (String.valueOf(spDesserts.getSelectedItem())).split("[,$]");
                double price = Double.parseDouble(Splitter[2]);
                Total = Total + price;
                txtViewDesserts.setText("$" + String.valueOf(price));
                txtViewTotal.setText("$" + (String.format("%.2f",Total)));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

    }

    //---------------------------------------------------
    // On Clicking submit
    //---------------------------------------------------
    public void onMealSubmitClicked(View v){
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = getPreferences(MODE_PRIVATE);
        editor = settings.edit();

        editor.putString(
                sharedEntrees, txtViewEntrees.getText().toString()
        );

        editor.putString(
                sharedDrinks, txtViewDrinks.getText().toString()
        );

        editor.putString(
                sharedDesserts, txtViewDesserts.getText().toString()
        );

        //Passing the Total value to the delivery page
        Intent intent = new Intent(getApplicationContext(), ActDelivery.class);
        intent.putExtra("doubleTotal", Total);
        startActivity(intent);
    }

    //---------------------------------------------------
    // On Clicking reset
    //---------------------------------------------------
    public void onMealResetClicked(View v){
        //Setting dialog box to ask if the user wants to reset or no
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Are you sure you want to reset all the fields?");
        builder.setMessage("If you do, you would have to fill everything again");

        //IF the user decides to reset this will be executed
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            //onClick function resets every field to previous
            public void onClick(DialogInterface dialog, int which) {
                txtViewDesserts.setText("$0.00");
                txtViewDrinks.setText("$0.00");
                txtViewEntrees.setText("$0.00");
                Total = 0.00;
                txtViewTotal.setText("$0.00");

                //setting all the spinners to default
                spEntrees.setSelection(0);
                spDesserts.setSelection(0);
                spDrinks.setSelection(0);


                //Toast message to show that reset was executed
                Toast.makeText(getApplicationContext(),"All the fields have been reset!", Toast.LENGTH_SHORT).show();
            }
        });

        //If the user selects no this will be executed
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Do nothing just show a toast message that nothing was changed
                Toast.makeText(getApplicationContext(),"Nothing changed!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}