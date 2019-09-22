package ca.bcit.tipcalculator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class GridActivity extends AppCompatActivity
        implements TextView.OnEditorActionListener, View.OnClickListener {


    // define variables for the widgets
    EditText billAmountEditText;
    TextView percentTextView;
    Button percentUpButton;
    Button percentDownButton;
    TextView tipTextView;
    TextView totalTextView;

    // define the SharedPreferences object
    SharedPreferences savedValues;

    // define instance variables that should be saved
    String billAmountString = "";
    float tipPercent = .15f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);


        // get references to the widgets
        billAmountEditText = findViewById(R.id.billAmountEditText);
        percentTextView = findViewById(R.id.percentTextView);
        percentUpButton = findViewById(R.id.btnPercentUp);
        percentDownButton = findViewById(R.id.btnPercentDown);
        tipTextView = findViewById(R.id.textviewTip);
        totalTextView = findViewById(R.id.textviewTotal);

        // set the listeners
        billAmountEditText.setOnEditorActionListener(this);
        percentUpButton.setOnClickListener(this);
        percentDownButton.setOnClickListener(this);

        // get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

    }

    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        billAmountString = savedValues.getString("billAmountString", "");
        tipPercent = savedValues.getFloat("tipPercent", 0.15f);

        // set the bill amount on its widget
        billAmountEditText.setText(billAmountString);

        // calculate and display
        calculateAndDisplay();
    }

    public void calculateAndDisplay() {

        // get the bill amount
        billAmountString = billAmountEditText.getText().toString();
        float billAmount;
        try {
            billAmount = Float.parseFloat(billAmountString);
        }
        catch (NumberFormatException e) {
            billAmount = 0;
        }

        // calculate tip and total
        float tipAmount = billAmount * tipPercent;
        float totalAmount = billAmount + tipAmount;

        // display the other results with formatting
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tipTextView.setText(currency.format(tipAmount));
        totalTextView.setText(currency.format(totalAmount));

        NumberFormat percent = NumberFormat.getPercentInstance();
        percentTextView.setText(percent.format(tipPercent));
    }

    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            calculateAndDisplay();
        }
        return false;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPercentDown:
                tipPercent = tipPercent - .01f;
                calculateAndDisplay();
                break;
            case R.id.btnPercentUp:
                tipPercent = tipPercent + .01f;
                calculateAndDisplay();
                break;
        }
    }

}