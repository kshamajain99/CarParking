package com.example.kshamajain.carparkingapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class SearchUserActivity extends Activity implements OnClickListener {

    EditText s_carno;
    static LinearLayout lv;
    static TextView tv1, tv2, tv3;
    Button bSearch;
    CarOwner carowner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        s_carno = (EditText)findViewById(R.id.s_carno);
        bSearch = (Button)findViewById(R.id.search);

        lv = (LinearLayout)findViewById(R.id.lv);
        tv1 = (TextView)findViewById(R.id.setName);
        tv2 = (TextView)findViewById(R.id.setContact);
        tv3 = (TextView)findViewById(R.id.setEmailid);

        bSearch.setOnClickListener(this);
    }

    public static String POST(String url, CarOwner carowner){
        InputStream inputStream = null;
        String result = "";

        try {

            Log.d("POST1","working");

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            //jsonObject.accumulate("name", carowner.getName());
            jsonObject.accumulate("carno", carowner.getCarno());
            //jsonObject.accumulate("contact", carowner.getContact());
            //jsonObject.accumulate("emailid",carowner.getEmailid());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            //JSONObject jsonresult = new JSONObject();
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null) {
                result = convertInputStreamToString(inputStream);
                Log.d("Result", result);
            }
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result

        // 2. initiate jackson mapper
       /* ObjectMapper mapper = new ObjectMapper();
        // 3. Convert received JSON to Article
        try {
            carowner = mapper.readValue(result, CarOwner.class);
            Log.d("Objectname",carowner.getName());
            lv.setVisibility(View.VISIBLE);
            tv1.setText(carowner.getName());
            tv2.setText(carowner.getContact());
            tv3.setText(carowner.getEmailid());

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return result;
    }

    public void onClick(View view) {

        switch(view.getId()){
            case R.id.search:

                Log.d("CARNO:",s_carno.getText().toString());
                if(!validate())
                    Toast.makeText(this, "Enter Complete data!", Toast.LENGTH_LONG).show();
                // call AsynTask to perform network operation on separate thread
                Log.d("OnClick", "Working");
                new HttpAsyncTask().execute("http://10.20.62.85:8080/CarParking2/SearchServlet");

                /*lv.setVisibility(View.VISIBLE);
                tv1.setText(carowner.getName());
                tv2.setText(carowner.getContact());
                tv3.setText(carowner.getEmailid());*/

                /*Intent i = getIntent();
                finish();
                startActivity(i);*/
                break;
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            carowner = new CarOwner();

            carowner.setCarno(s_carno.getText().toString());
            return POST(urls[0],carowner);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            ObjectMapper mapper = new ObjectMapper();
            // 3. Convert received JSON to Article
            try {
                carowner = mapper.readValue(result, CarOwner.class);
                Log.d("Objectname",carowner.getName());
                lv.setVisibility(View.VISIBLE);
                tv1.setText(carowner.getName());
                tv2.setText(carowner.getContact());
                tv3.setText(carowner.getEmailid());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validate(){
        if(s_carno.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
