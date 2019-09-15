package ca.bcit.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateMessengerActivity extends AppCompatActivity {


    public void onSendMessage(View view) {
        EditText messageView = findViewById(R.id.message);
        String messageText = messageView.getText().toString();

        Intent intent = new Intent(this, ReceiveMessageActivity.class);
        intent.putExtra("msg", messageText);
        startActivity(intent);
    }

    public void onSendMessageToOtherApp(View v) {
        EditText messageview = findViewById(R.id.message);
        String messageText = messageview.getText().toString();

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, messageText);
        startActivity(i);
    }

    public void onSendMessageToToast(View v) {
        EditText messageView = findViewById(R.id.message);
        String messageText = messageView.getText().toString();

        Toast toast = Toast.makeText(this, messageText, Toast.LENGTH_SHORT);
        toast.show();
    }

}
