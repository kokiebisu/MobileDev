package ca.bcit.okiebisu_abrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String smallThumbnail = intent.getStringExtra("smallThumbnail");
        String author = intent.getStringExtra("author");
        String publisher = intent.getStringExtra("publisher");
        String publishedDate = intent.getStringExtra("publishedDate");
        String identifier = intent.getStringExtra("identifier");
//        String description = intent.getStringExtra("description");

        Log.i("title", title);


    }
}
