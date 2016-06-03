package com.jassi.loktra;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> authorName;
    ArrayList<String> commitDate;
    ArrayList<String> commitMessage;
    ProgressDialog pd;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authorName = new ArrayList<String>();
        commitDate = new ArrayList<String>();
        commitMessage = new ArrayList<String>();

        recyclerView = (RecyclerView) findViewById(R.id.recylerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new GetJSON().execute();

    }


    private class GetJSON extends AsyncTask<String, String, String> {
        StringBuilder stringBuilder = new StringBuilder();

        @Override
        protected void onPreExecute() {

            // TODO Auto-generated method stub
            //  Toast.makeText(getApplicationContext(), "pre", 3000).show();

            super.onPreExecute();


            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("loading");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            try {
                URL url = new URL("https://api.github.com/repos/rails/rails/commits?sha=master");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    stringBuilder.append(str);

                }

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


            Log.w("Result", "" + result);


            try {
                JSONArray array = new JSONArray(result);
                Log.d("Result", "" + array.length());
                String length = "" + array.length();

                for (int count = 0; count < array.length(); count++) {
                    JSONObject object = array.getJSONObject(count);
                    JSONObject getCommit = object.getJSONObject("commit");
                    JSONObject getAuthor = getCommit.getJSONObject("author");
                    authorName.add(getAuthor.getString("name"));
                    commitDate.add(getAuthor.getString("date").substring(0, 10));
                    commitMessage.add(getCommit.getString("message"));

//                    Log.d("Result", "" + autherName);
//                    Log.d("Result", "" + commitDate);
//                    Log.d("Result", "" + commitMessage);


                }

                MyAdapter adapter = new MyAdapter(MainActivity.this, authorName, commitDate, commitMessage);
                recyclerView.setAdapter(adapter);
                pd.dismiss();
                //          Message.setText(message);

//               JSONObject object1=array.getJSONObject(0);
//                Log.d("Result",""+object1);

//
//                String Message=object1.getString("message");
//                Toast.makeText(MainActivity.this, ""+Message, Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}
