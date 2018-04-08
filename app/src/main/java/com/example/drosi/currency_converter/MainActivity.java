package com.example.drosi.currency_converter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    // Declare variables to handle the math and conversions
    double firstAmount, finalAmount, modifiedFirstAmount, modifier;
    final double US_EURO = 0.8139;
    final double US_PESO = 18.291513;
    final double US_GBP = 0.709735;
    final double US_YEN = 106.882606;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add in the action bar with the icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // Set up all the widgets
        final EditText inputCurrency = (EditText)findViewById(R.id.inputAmount);
        final Spinner inputCurrencySelector = (Spinner)findViewById(R.id.StartingCurrencySelector);
        final Spinner finalCurrencySelector = (Spinner)findViewById(R.id.EndingCurrencySelector);
        final Button btnCalculate = (Button)findViewById(R.id.btnConvert);
        final TextView txtFinal = (TextView)findViewById(R.id.txtFinalAmount);

        // Add the click listener for the calculate button
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // The user clicked the button, do the calculations here
                try {
                    firstAmount = Double.parseDouble(inputCurrency.getText().toString());
                } catch (Exception e) {
                    firstAmount = 0;
                    Toast.makeText(MainActivity.this, "No Value Entered Yet!", Toast.LENGTH_LONG).show();
                }

                // Set the starting amount
                switch(inputCurrencySelector.getSelectedItem().toString()) {
                    case "USD":
                        modifiedFirstAmount = firstAmount;
                        break;
                    case "Euro":
                        // The make the conversions easier, convert everything into USD first
                        modifiedFirstAmount = firstAmount / US_EURO;
                        break;
                    case "GBP":
                        modifiedFirstAmount = firstAmount / US_GBP;
                        break;
                    case "Peso (MXN)":
                        // Convert into USD before doing the final calculation
                        modifiedFirstAmount = firstAmount / US_PESO;
                        break;
                    case "YEN":
                        modifiedFirstAmount = firstAmount / US_YEN;
                        break;
                }
                DecimalFormat currency = new DecimalFormat("$###,###.##");
                // Set the modifier for the final amount
                switch(finalCurrencySelector.getSelectedItem().toString()) {
                    case "USD":
                        modifier = 1;
                        break;
                    case "Euro":
                        modifier = US_EURO;
                        currency.applyPattern("€###,###.##");
                        break;
                    case "GBP":
                        modifier = US_GBP;
                        currency.applyPattern("£###,###.##");
                        break;
                    case "Peso (MXN)":
                        modifier = US_PESO;
                        break;
                    case "YEN":
                        modifier = US_YEN;
                        currency.applyPattern("¥###,###.##");
                        break;
                }
                // Calculate the conversion factor
                finalAmount = modifiedFirstAmount * modifier;

                // Toast the user if the value is over $10,000
                if (finalAmount >= 10000) {
                    Toast.makeText(MainActivity.this, "That's alot of cheddar!", Toast.LENGTH_LONG).show();
                }

                txtFinal.setText(currency.format(finalAmount) + " " + finalCurrencySelector.getSelectedItem().toString());
            }
        });
    }
}
