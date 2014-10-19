package com.example.joel.webapitest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;



public class SearchForItem extends Activity {

    RequestQueue mRequestQueue;

    ArrayList<MediaObject> MediaObjects;
    MediaObjectArrayAdapter listadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerSearchType);
        ArrayAdapter<CharSequence> spinnerarray = ArrayAdapter.createFromResource(this, R.array.SearchTypes,android.R.layout.simple_spinner_item);
        spinnerarray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerarray);

        MediaObjects = new ArrayList<MediaObject>();
        listadapter = new MediaObjectArrayAdapter(this, 0, MediaObjects);
        ((ListView)findViewById(R.id.ResultsList)).setAdapter(listadapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_for_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void GetData(String data)
    {

        String url = null;
        try {
            Spinner spinner = (Spinner)findViewById(R.id.spinnerSearchType);
            String SearchType = (String)spinner.getSelectedItem();

            StringBuilder sb = new StringBuilder();
            sb.append("https://www.googleapis.com/books/v1/volumes?q=");

            if (SearchType.contentEquals("Any"))
                sb.append(java.net.URLEncoder.encode(data, "UTF-8"));

            if (SearchType.contentEquals("Title")) {
                sb.append("intitle:");
                sb.append(java.net.URLEncoder.encode(data, "UTF-8"));
            }

            if (SearchType.contentEquals("ISBN")) {
                sb.append("isbn:");
                sb.append(java.net.URLEncoder.encode(data, "UTF-8"));
            }
            url = sb.toString();

            Log.w("GetData", url);
        }
        catch (UnsupportedEncodingException ex)
        {
            Log.e("GetData", ex.getMessage());
            return;
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {

                try{
                    JSONArray jsonArray = new JSONArray(response.getString("items"));

                    for (int i= 0; i < jsonArray.length(); i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        JSONObject volumeObj = obj.getJSONObject("volumeInfo");

                        String Title = volumeObj.getString("title");
                        String Author = volumeObj.getJSONArray("authors").getString(0);

                        String Thumbnail = null;

                         try {
                             Thumbnail=volumeObj.getJSONObject("imageLinks").getString("smallThumbnail");
                         }
                         catch (Exception ex) {}
                        MediaObjects.add(new MediaObject(Title, Author, Thumbnail));
                        //Log.i("GetData", );
                        listadapter.notifyDataSetChanged();
                    }
                }
                catch (Exception e)
                {
                    Log.e("GetData", e.getMessage());
                }

                    //Log.i("GetData", response.toString());
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("GetData", error.getMessage());
            }
        });

        RequestQueueSingleton.getInstance(this).AddToRequestQueue(jsObjRequest);

        //mRequestQueue.add((jsObjRequest));
    }


        public void onSearchClick(View v) {
            EditText editText;

            editText =(EditText)findViewById(R.id.editText);

            MediaObjects.clear();
            listadapter.notifyDataSetChanged();

            // Set up the HTTP transport and JSON
            GetData(editText.getText().toString());
        }


    /*private class readItemsJsonTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }*/
}
