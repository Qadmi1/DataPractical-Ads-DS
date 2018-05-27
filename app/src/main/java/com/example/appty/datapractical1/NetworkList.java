package com.example.appty.datapractical1;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by appty on 05/05/18.
 */

public class NetworkList extends ListActivity {

    ListView listView;
    final static String urlStr = "http://shawtesting.net/youtubenamegrab/getworkouts.php";
    static List<Workout> workoutList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new FetchItems().execute();

    }

    class FetchItems extends AsyncTask<Void, Void, Void> {

        HttpURLConnection connection;
        InputStream inputStream;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NetworkList.this);
            progressDialog.setMessage("Loading workout from resource");
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    cancel(true);
                    finish();
                }
            });
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL(urlStr);

                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("GET");
                inputStream = connection.getInputStream();
                readJsonStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (progressDialog != null)
            {
                progressDialog.dismiss();
            }
            try {
                if (inputStream != null)
                {
                    inputStream.close();
                    connection.disconnect();
                }
                else {
                    connection.disconnect();
                }
            }catch (IOException e)
            {

            }
            setAdapter();

        }
    }

    private void readJsonStream(InputStream inputStream) throws IOException {

        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

        try {
            readArray(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }

    }

    private void readArray(JsonReader reader) throws IOException {
        reader.beginArray();

        while (reader.hasNext()) {
            workoutList.add(readWorkout(reader));
        }
        reader.endArray();
    }

    private Workout readWorkout(JsonReader reader) throws IOException {
        String title = null;
        String sets = null;
        String reps = null;
        String desc = null;

        String name = null;

        reader.beginObject();

        while (reader.hasNext()) {

            name = reader.nextName();
            switch (name) {
                case "title":
                    title = reader.nextString();
                    break;
                case "sets":
                    sets = reader.nextString();
                    break;
                case "reps":
                    reps = reader.nextString();
                    break;
                case "description":
                    desc = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;

            }
        }
        reader.endObject();

        return new Workout(title, sets, reps, desc);
    }

    private void setAdapter(){
        listView = getListView();
        ArrayAdapter<Workout> adapter = new ArrayAdapter<Workout>(this, android.R.layout.simple_list_item_1, workoutList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(NetworkList.this, OnlineWorkout.class);
        intent.putExtra(OnlineWorkout.EXTRA_ID, workoutList.get((int) id));
        startActivity(intent);
    }
}
