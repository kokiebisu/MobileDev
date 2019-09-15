package ca.bcit.lab02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {

    String word;
    String reversedWord = "";

    EditText wordInput;

    Button submitButton;
    Button resetButton;

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordInput = findViewById(R.id.wordInput);

        submitButton = findViewById(R.id.submit);
        resetButton = (Button) findViewById(R.id.reset);

        result = (TextView) findViewById(R.id.result);

        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                result.setText(null);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                reversedWord = "";
                word = wordInput.getText().toString();
                if (word.length() == 0) {
                    reversedWord = "You must enter text";
                    result.setTextColor(Color.RED);
                    result.setText(reversedWord);
                } else {
                    for (int i = word.length() - 1; i >= 0; i--) {
                        reversedWord = reversedWord + word.charAt(i);
                    }
                    result.setTextColor(Color.BLACK);
                    result.setText(reversedWord);
                }
            }
        });
    }
}
