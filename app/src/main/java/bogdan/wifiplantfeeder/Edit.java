package bogdan.wifiplantfeeder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


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
        repeatInput = findViewById(R.id.repeatInput);
        quantityInput = findViewById(R.id.quantityInput);
        repeatOutput = findViewById(R.id.repetitionOutput);
        quantityOutput = findViewById(R.id.quantityOutput);
        nameView = (TextView) findViewById(R.id.nameTextView);
        defaultSwitch = (Switch) findViewById(R.id.toDeafult);
        saveButton = (Button) findViewById(R.id.saveChanges);
        cancelButton = (Button) findViewById(R.id.cancelChanges);
        defaultSwitch = (Switch) findViewById(R.id.toDeafult);
        RBPConnect = (TextView) findViewById(R.id.RBPConnection);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendInfo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    repeatInput.setVerticalScrollbarPosition(10);
                    repeatInput.setEnabled(false);
                    quantityInput.setVerticalScrollbarPosition(10);
                    quantityInput.setEnabled(false);
//                    try {
//                        getWeather();
//                        //TODO make logic for setting the 2
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                } else {
                    repeatInput.setEnabled(true);
                    repeatInput.setVerticalScrollbarPosition(0);
                    quantityInput.setEnabled(true);
                    quantityInput.setVerticalScrollbarPosition(0);
                }

            }
        });


        repeatInput.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                repeatOutput.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        quantityInput.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                quantityOutput.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    public void sendInfo() throws IOException {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");
        client.addHeader("Content-type", "application/json;charset=utf-8");
        client.addHeader("repetition", String.valueOf(repeatInput.getProgress()*1000));
        client.addHeader("quantity", String.valueOf(quantityInput.getProgress()*1000));

        client.put("http://192.168.10.163:5000/switch", new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("Success " + Arrays.toString(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Bad " +error);
            }
        });
    }

    public void getWeather() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://community-open-weather-map.p.rapidapi.com/weather?callback=test&id=2172797&units=%22metric%22%20or%20%22imperial%22&mode=xml%2C%20html&q=Bucharest%2Cro")
                .get()
                .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "4bdbd2e979mshbe85be9e7f23d87p1d4339jsn0b9a69e14110")
                .build();

        Response response = client.newCall(request).execute();
    }
}
