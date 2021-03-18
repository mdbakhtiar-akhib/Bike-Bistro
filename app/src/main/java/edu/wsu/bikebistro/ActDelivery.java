//==========================================================================
//
// Application: Bike-bistro
// Activity:    Activity Delivery
// Course:      CSC 4330
// Homework:    2
// Author:      MD Bakhtiar R Akhib
// Date:        03/03/2021
// Description: This is an android app for Bike Bistro that lets user to show the menu and let them
//                 choose from it and also select the delivery options.
//===========================================================================

package edu.wsu.bikebistro;

//Importing packages
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

//---------------------------------
// class ActDelivery
//---------------------------------
public class ActDelivery extends AppCompatActivity{
    //-------------------------------------------
    // Constants and variables
    //-------------------------------------------
    private EditText txtViewName;
    private EditText txtViewSubTotal;
    private EditText txtViewAddress;
    private EditText txtViewPhone;
    private EditText txtViewTax;
    private EditText txtVIewDistance;
    private EditText txtViewDeliveryCharge;
    private EditText txtViewTip;
    private EditText txtViewGrandTotal;
    private EditText txtViewDeliveryTime;
    private SeekBar sbDistance;
    double sub = 0.0;
    double GrandTotal = 0.0;
    double DeliveryCharge = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laydelivery);

        //Getting the ids by value and storing them into objects
        txtViewSubTotal= findViewById(R.id.txtViewSubTotal);
        txtViewName = findViewById(R.id.txtViewName);
        txtViewAddress = findViewById(R.id.txtViewAddress);
        txtViewPhone = findViewById(R.id.txtViewPhone);
        txtViewTax = findViewById(R.id.txtViewTax);
        txtVIewDistance = findViewById(R.id.txtViewDistance);
        txtViewDeliveryCharge = findViewById(R.id.txtViewDeliveryCharge);
        txtViewTip = findViewById(R.id.txtViewTip);
        txtViewGrandTotal = findViewById(R.id.txtViewGrandTotal);
        txtViewDeliveryTime = findViewById(R.id.txtViewDeliveryTime);
        sbDistance = findViewById(R.id.sbDistance);

        //----------------------------------------------------
        // Getting the total from the Menu page
        //----------------------------------------------------
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            sub = extras.getDouble("doubleTotal");
            if (sub != 0.0){
                txtViewSubTotal.setText("$" + (String.format("%.2f",sub)));
            }
        }

        //----------------------------------------------------
        // Calculating the tax and setting the grand total
        //----------------------------------------------------
        double tax = (sub * 6) / 100;
        GrandTotal = GrandTotal + tax + sub;

        txtViewTax.setText("$" + String.format("%.2f", tax));
        txtViewGrandTotal.setText("$" + String.format("%.2f", GrandTotal));

        //----------------------------------------------------
        // Seekbar for selectng the distance with max 21
        //----------------------------------------------------
        sbDistance.setMax(21);
        sbDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(
                    SeekBar seekBar, int progress, boolean fromUser)
            {
                //Setting the value of miles
                txtVIewDistance.setText(String.valueOf(progress) + " miles");
                //Calculating and setting the Estimated delivery time
                Double ETA = Double.parseDouble(String.valueOf(progress)) * 2;
                txtViewDeliveryTime.setText(ETA + " mins");
                if(Double.parseDouble(String.valueOf(progress)) <= 10){
                    DeliveryCharge = 3.0;
                    GrandTotal = GrandTotal + DeliveryCharge;
                    txtViewDeliveryCharge.setText("$" + DeliveryCharge);
                    txtViewGrandTotal.setText("$" + String.format("%.2f", GrandTotal));
                }
                else{
                    DeliveryCharge = 5.0;
                    GrandTotal = GrandTotal + DeliveryCharge;
                    txtViewDeliveryCharge.setText("$" + DeliveryCharge);
                    txtViewGrandTotal.setText("$" + String.format("%.2f", GrandTotal));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {}
        });

        //Setting the grand total after getting user input for tip
        GrandTotal = GrandTotal + Double.parseDouble(txtViewTip.getText().toString());
        txtViewGrandTotal.setText("$" + String.format("%.2f", GrandTotal));

    }

    //---------------------------------------------------
    // On Clicking reset
    //---------------------------------------------------
    public void onDeliveryResetClicked(View v){
        //Setting dialog box to ask if the user wants to reset or no
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Are you sure you want to reset all the fields?");
        builder.setMessage("If you do, you would have to fill everything again");

        //IF the user decides to reset this will be executed
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            //onClick function resets every field to previous
            public void onClick(DialogInterface dialog, int which) {
                txtViewAddress.setText("");
                txtViewDeliveryTime.setText("");
                txtVIewDistance.setText("");
                txtViewGrandTotal.setText("");
                txtViewName.setText("");
                txtViewPhone.setText("");
                txtViewSubTotal.setText("");
                txtViewTax.setText("");
                txtViewTip.setText("");
                txtViewDeliveryCharge.setText("");
                sub = 0.0;
                GrandTotal = 0.0;
                DeliveryCharge = 0.0;

                //setting all the spinners to default
                sbDistance.setProgress(0);

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

    //---------------------------------------------------
    // On Clicking submit
    //---------------------------------------------------
    public void onDeliverySubmitClicked(View v) {
        //Output a single dialog box to show the user and order details
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Order details");

        builder.setMessage("Your order details:" +
                "\n Customer Name: " + txtViewName.getText().toString() +
                "\n Address: " + txtViewAddress.getText().toString() +
                "\n Phone: " + txtViewPhone.getText().toString() +
                "\n Sub Total: " + txtViewSubTotal.getText().toString() +
                "\n Tax: " + txtViewTax.getText().toString() +
                "\n Distance: " + txtVIewDistance.getText().toString() +
                "\n Delivery Charge: " + txtViewDeliveryCharge.getText().toString() +
                "\n Tip: " + txtViewTip.getText().toString() +
                "\n Total: " + txtViewGrandTotal.getText().toString() +
                "\n ETA: " + txtViewDeliveryTime.getText().toString());
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
