package ca.bcit.okiebisu_abrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {
    TextView titleTextView;
    TextView publisherTextView;
    TextView publishedDateTextView;
    TextView authorTextView;
    TextView identifierTextView;
    ImageView smallThumbnailImageView;

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

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


        titleTextView = findViewById(R.id.titleTextView);
        publisherTextView = findViewById(R.id.publisherTextVIew);
        publishedDateTextView = findViewById(R.id.publishedDateTextView);
        authorTextView = findViewById(R.id.authorTextView);
        identifierTextView = findViewById(R.id.identifierTextView);
        smallThumbnailImageView = findViewById(R.id.smallThumbnailImageView);

        ImageDownloader task = new ImageDownloader();
        Bitmap myImage;
        try {
            myImage = task.execute(smallThumbnail).get();
            smallThumbnailImageView.setImageBitmap(myImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        titleTextView.setText(title);
        publisherTextView.setText(publisher);
        publishedDateTextView.setText(publishedDate);
        authorTextView.setText(author);
        identifierTextView.setText(identifier);




    }
}
