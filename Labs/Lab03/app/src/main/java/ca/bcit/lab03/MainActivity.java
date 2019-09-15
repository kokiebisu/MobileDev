package ca.bcit.lab03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onChangeBackground(View view) {
        RelativeLayout layout = findViewById(R.id.relativeLayoutMain);
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        layout.setBackgroundColor(color);
    }

    public void onSpeakActivity(View view) {
        Intent intent = new Intent(this, SpeakActivity.class);
        startActivity(intent);
    }

}
