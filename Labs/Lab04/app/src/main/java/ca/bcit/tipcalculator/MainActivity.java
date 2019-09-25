package ca.bcit.tipcalculator;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onConstraintClick(View v) {
        Intent i = new Intent(this, ConstraintActivity.class);
        startActivity(i);
    }

    public void onLinearClick(View v) {
        Intent i = new Intent(this, LinearActivity.class);
        startActivity(i);
    }

    public void onRelativeClick(View v) {
        Intent i = new Intent(this, RelativeActivity.class);
        startActivity(i);
    }

    public void onGridClick(View v) {
        Intent i = new Intent(this, GridActivity.class);
        startActivity(i);
    }

    public void onTableClick(View v) {
        Intent i = new Intent(this, TableActivity.class);
        startActivity(i);
    }


}
