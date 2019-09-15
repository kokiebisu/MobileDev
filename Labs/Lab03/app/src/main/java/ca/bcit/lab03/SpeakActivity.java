package ca.bcit.lab03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class SpeakActivity extends AppCompatActivity {
    private TextToSpeech readingText;
    private EditText messageView;
    private String messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speak);

        readingText = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = readingText.setLanguage(Locale.CANADA);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

    }


    public void onSpeak(View view) {
        messageView = findViewById(R.id.editTextSpeak);
        messageText = messageView.getText().toString();
        readingText.speak(messageText, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    public void onGoBack(View view) {
        finish();
    }


}
