package ca.bcit.lecture01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnToggle(View view) {
        findViewById(R.id.btnToggle);
        if (toggle) {
tv.setText(tv.getText().toString().toLowerCase());
        } else {
    tv.setText(tv.getText().toString().toUpperCase());
        }

        toggle  = !toggle;
    }
}
