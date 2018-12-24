package bogdan.wifiplantfeeder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import java.net.URL;




public class Edit extends AppCompatActivity {
    private String name;
    private String addedOn;
    private int repeat;
    private int quantity;
    Button saveButton;
    Button cancelButton;
    SeekBar repeatInput;
    SeekBar quantityInput;
    TextView repeatOutput;
    TextView quantityOutput;
    TextView nameView;
    TextView dateView;
    Switch defaultSwitch;
    TextView RBPConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        repeatInput=findViewById(R.id.repeatInput);
        quantityInput= findViewById(R.id.quantityInput);
        repeatOutput=findViewById(R.id.repetitionOutput);
        quantityOutput=findViewById(R.id.quantityOutput);
        nameView=(TextView)findViewById(R.id.nameTextView);
        dateView=(TextView)findViewById(R.id.addedDateTextView);
        defaultSwitch = (Switch)findViewById(R.id.toDeafult);
        saveButton=(Button)findViewById(R.id.saveChanges);
        cancelButton=(Button)findViewById(R.id.cancelChanges);
        defaultSwitch=(Switch)findViewById(R.id.toDeafult);
        RBPConnect=(TextView)findViewById(R.id.RBPConnection);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sendInfo();
                Edit.super.onBackPressed();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit.super.onBackPressed();
            }
        });

        defaultSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    repeatInput.setVerticalScrollbarPosition(10);
                    repeatInput.setEnabled(false);
                    quantityInput.setVerticalScrollbarPosition(10);
                    quantityInput.setEnabled(false);
                }
                else{
                    repeatInput.setEnabled(true);
                    repeatInput.setVerticalScrollbarPosition(0);
                    quantityInput.setEnabled(true);
                    quantityInput.setVerticalScrollbarPosition(0);
                }

            }
        });

        repeatInput.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                repeatOutput.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        quantityInput.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                quantityOutput.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });
    }

    public boolean checkNetworkConnection(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isConnected = false;
        if (networkInfo != null && (isConnected = networkInfo.isConnected())) {
            RBPConnect.setText("Connected");
            RBPConnect.setBackgroundColor(0xFF7CCC26);
        } else {
            RBPConnect.setText("Not connected");
            RBPConnect.setBackgroundColor(0xFFFF0000);
        }

        return isConnected;
    }
    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    return HttpPost(urls[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

        }
    }

    private String HttpPost(String myUrl) throws IOException, JSONException {
        String result = "";

        URL url = new URL(myUrl);

        // 1. create HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        // 2. build JSON object
        JSONObject jsonObject = buidJsonObject();

        // 3. add JSON content to POST request body
        setPostRequestContent(conn, jsonObject);

        // 4. make POST request to the given URL
        conn.connect();

        // 5. return response message
        return conn.getResponseMessage()+"";

    }

    private JSONObject buidJsonObject() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("quantity", quantityInput.getProgress());
        jsonObject.accumulate("repetition", repeatInput.getProgress());

        return jsonObject;
    }

    private void setPostRequestContent(HttpURLConnection conn,
                                       JSONObject jsonObject) throws IOException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        Log.i(Edit.class.toString(), jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }


    private void sendInfo(){
        HTTPAsyncTask task = new HTTPAsyncTask();
        task.execute("http://192.168.10.163:5000/switch");
    }
}
