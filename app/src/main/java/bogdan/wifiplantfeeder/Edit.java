package bogdan.wifiplantfeeder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

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
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    repeatInput.setVerticalScrollbarPosition(10);
                    repeatInput.setEnabled(false);
                    quantityInput.setVerticalScrollbarPosition(10);
                    quantityInput.setEnabled(false);
                    try {
                        getWeather();
                        //TODO make logic for setting the 2
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        quantityInput.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                quantityOutput.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    public void sendInfo() {
        AsyncTask.execute(new Runnable() {
            public void run() {
                // Create URL
                URL rbpEndpoint = null;
                try {
                    rbpEndpoint = new URL("http://192.168.10.163:5000/switch");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                // Create connection
                HttpsURLConnection rbpConnect =
                        null;
                try {
                    rbpConnect = (HttpsURLConnection) rbpEndpoint.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    rbpConnect.setRequestMethod("POST");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }

                rbpConnect.setRequestProperty("quantity", String.valueOf(quantityInput.getProgress()));
                rbpConnect.setRequestProperty("repetition", String.valueOf(repeatInput.getProgress()));

                try {
                    rbpConnect.getResponseCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
