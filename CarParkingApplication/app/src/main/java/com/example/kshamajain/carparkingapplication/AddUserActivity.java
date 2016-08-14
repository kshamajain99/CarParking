package com.example.kshamajain.carparkingapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class AddUserActivity extends Activity implements OnClickListener {

    EditText c_name,c_carno,c_contact,c_email;
    //String name, carno, contact, email;
    Button bpost;
    CarOwner carowner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        Log.d("msg1", "working");

        c_name = (EditText)findViewById(R.id.coid);
        //name = c_name.getText().toString();
        /*Log.d("NAME1:",c_name.getText().toString());
        String xyz = "KSHAMA";
        Log.d("XYZ",xyz);*/
        c_carno = (EditText)findViewById(R.id.cnid);
        //carno = c_carno.getText().toString();
        //Log.d("CARNO",carno);
        c_contact = (EditText)findViewById(R.id.phid);
        //contact = c_contact.getText().toString();
        c_email = (EditText)findViewById(R.id.eid);
        //email = c_email.getText().toString();
        bpost = (Button)findViewById(R.id.btnPost);

        bpost.setOnClickListener(this);
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
            jsonObject.accumulate("name", carowner.getName());
            jsonObject.accumulate("carno", carowner.getCarno());
            jsonObject.accumulate("contact", carowner.getContact());
            jsonObject.accumulate("emailid",carowner.getEmailid());

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
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    public void onClick(View view) {

        switch(view.getId()){
            case R.id.btnPost:
                Log.d("NAME",c_name.getText().toString());
                Log.d("CARNO:",c_name.getText().toString());
                Log.d("Contact:",c_contact.getText().toString());
                Log.d("Email:",c_email.getText().toString());
                Log.d("OnClick", "Working");
                if(!validate())
                    Toast.makeText(this, "Enter Complete data!", Toast.LENGTH_LONG).show();
                else
                    new HttpAsyncTask().execute("http://10.20.62.85:8080/CarParking2/AddServlet");
                // call AsynTask to perform network operation on separate thread

                Intent i  = getIntent();
                finish();
                startActivity(i);
                break;
        }

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            carowner = new CarOwner();
            carowner.setName(c_name.getText().toString());
            Log.d("NAME2:",c_name.getText().toString());
            carowner.setCarno(c_carno.getText().toString());
            carowner.setContact(c_contact.getText().toString());
            carowner.setEmailid(c_email.getText().toString());

            return POST(urls[0],carowner);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validate(){
        String MobilePattern = "[7-9]{1}[0-9]{9}";

        if(c_carno.getText().toString().trim().equals(""))
            return false;
        else if(c_carno.getText().toString().trim().equals(""))
            return false;
        else if(c_contact.getText().toString().trim().equals(""))
            return false;
        else if(c_email.getText().toString().trim().equals(""))
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
        getMenuInflater().inflate(R.menu.menu_add_user, menu);
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
