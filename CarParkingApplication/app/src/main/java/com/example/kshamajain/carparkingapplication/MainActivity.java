package com.example.kshamajain.carparkingapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends Activity implements OnClickListener {

    ImageButton bAdd, bSub, parkAvail;
    TextView tv1;
    View v;
    //Integer counter;
    //CarAvailability caraval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //v = findViewById()

        tv1 = (TextView)findViewById(R.id.availnew);

        new HttpAsyncTask().execute("http://10.20.62.85:8080/CarParking2/ParkingAvailServlet");

        ImageButton b1 =(ImageButton)findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, AddUserActivity.class);
                startActivity(i);
            }
        });

        ImageButton b2 =(ImageButton)findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,SearchUserActivity.class);
                startActivity(i);
            }
        });

       /* parkAvail = (ImageButton)findViewById(R.id.button3);
        parkAvail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this,AvailabilityActivity.class);
                startActivity(i);

            }
        });*/

        bAdd = (ImageButton)findViewById(R.id.addcar);
        bAdd.setOnClickListener(this);
        bSub = (ImageButton)findViewById(R.id.delcar);
        bSub.setOnClickListener(this);
    }

    public static String POST(String url){
        InputStream inputStream = null;
        String result = "";

        try {

            Log.d("POST1","working");

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

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

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.addcar:

                //Toast.makeText(this, "Clicked!", Toast.LENGTH_LONG).show();
                // call AsynTask to perform network operation on separate thread
                Log.d("OnClick", "Working");
                try{
                     new HttpAsyncTask().execute("http://10.20.62.85:8080/CarParking2/AddCarServlet");

                    /*if(s.equals("10"))
                        Toast.makeText(this,"No more car can be added!!!",Toast.LENGTH_LONG).show();*/
                }catch(Exception e){

                }
                /*Intent i  = getIntent();
                finish();
                startActivity(i);*/
                ///new HttpAsyncTask().onPostExecute();
                break;

            case  R.id.delcar:

                //Toast.makeText(this, "Clicked!", Toast.LENGTH_LONG).show();
                // call AsynTask to perform network operation on separate thread
                Log.d("OnClick", "Working");
                try{
                    new HttpAsyncTask().execute("http://10.20.62.85:8080/CarParking2/RemoveCarServlet");
                }catch(Exception e){

                }
               /* Intent i1  = getIntent();
                finish();
                startActivity(i1);*/
                break;

        }

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return POST(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        //@Override
        protected void onPostExecute(String result) {

            if(result.equals("0")){
                tv1.setText("NO PARKING SPACE AVAILABLE!!!");
                tv1.setTextColor(Color.RED);
            }else{
                tv1.setText(result);
                tv1.setTextColor(Color.BLUE);
            }
        }
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
