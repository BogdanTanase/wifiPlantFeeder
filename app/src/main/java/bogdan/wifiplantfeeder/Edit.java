package bogdan.wifiplantfeeder;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class Edit extends AppCompatActivity {
    private String name;
    private String addedOn;
    private int repeat;
    private int quantity;
    Button saveButton;
    Button cancelButton;
    EditText repeatInput;
    EditText quantityInput;
    TextView nameView;
    TextView dateView;
    Switch defaultSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        repeatInput=(EditText)findViewById(R.id.repeatInput);
        quantityInput=(EditText)findViewById(R.id.quantityInput);
        nameView=(TextView)findViewById(R.id.nameTextView);
        dateView=(TextView)findViewById(R.id.addedDateTextView);
        defaultSwitch = (Switch)findViewById(R.id.toDeafult);
        saveButton=(Button)findViewById(R.id.saveChanges);
        cancelButton=(Button)findViewById(R.id.cancelChanges);
        defaultSwitch=(Switch) findViewById(R.id.toDeafult);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        // Create URL
                        URL githubEndpoint = null;
                        URL httpbinEndpoint = null;
                        try {
                            httpbinEndpoint = new URL("http://192.168.10.163:5000/on");
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        HttpsURLConnection myConnection = null;
                        try {
                            myConnection = (HttpsURLConnection) httpbinEndpoint.openConnection();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            myConnection.setRequestMethod("POST");
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (myConnection.getResponseCode() == 200) {
                                Snackbar successMessage= Snackbar.make(view, "saved",3);
                            } else {
                                Snackbar faillMessage= Snackbar.make(view, "failled",3);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            };
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
                    repeatInput.setText("10");
                    repeatInput.setEnabled(false);
                    quantityInput.setText("10");
                    quantityInput.setEnabled(false);
                }
                else{
                    repeatInput.setEnabled(true);
                    repeatInput.setText("");
                    quantityInput.setEnabled(true);
                    quantityInput.setText("");
                }

            }
        });
    }
}
