package ca.bcit.okiebisu_abrary;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    // URL to get contacts JSON
    private static String SERVICE_URL = "https://www.googleapis.com/books/v1/volumes?q=harry+potter";
    private ArrayList<Book> bookList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookList = new ArrayList<Book>();
        lv = findViewById(R.id.bookList);
        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = null;

            // Making a request to url and getting response
            jsonStr = sh.makeServiceCall(SERVICE_URL);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray bookJsonArray = jsonObj.getJSONArray("items");

                    // looping through All Contacts
                    for (int i = 0; i < bookJsonArray.length(); i++) {
                        JSONObject item = bookJsonArray.getJSONObject(i);
                        JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                        String title = volumeInfo.getString("title");

//                        String firstName = c.getString("firstName");
//                        String lastName = c.getString("lastName");
//                        String occupation = c.getString("occupation");
//                        String gender = c.getString("gender");
//                        String pictureUrl = c.getString("pictureUrl");

                        // tmp hash map for single contact
                        Book book = new Book();

                        // adding each child node to HashMap key => value
                        book.setTitle(title);
//                        book.sete(firstName);
//                        book.setLastName(lastName);
//                        book.setOccupation(occupation);
//                        book.setGender(gender);
//                        book.setPictureUrl(pictureUrl);

                        // adding contact to contact list
                        bookList.add(book);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            //Toon[] toonArray = toonList.toArray(new Toon[toonList.size()]);

            BookAdapter adapter = new BookAdapter(MainActivity.this, bookList);

            // Attach the adapter to a ListView
            lv.setAdapter(adapter);
        }
    }

}