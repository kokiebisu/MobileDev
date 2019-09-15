package ca.bcit.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ReceiveMessageActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "msg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);

        Intent intent = getIntent();
        String messageText = intent.getStringExtra((EXTRA_MESSAGE));
        TextView messageview = findViewById(R.id.message);
        messageview.setText(messageText);
    }

    public void onBackClick(View v) {
        finish();
    }
}
