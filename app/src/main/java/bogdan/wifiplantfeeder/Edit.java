package bogdan.wifiplantfeeder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;


public class Edit extends AppCompatActivity {
    private String name;
    private String addedOn;
    private int repeat=10;
    private int quantity=10;
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
            public void onClick(View view) {
                //to add personal data base

                //to send info to rbp
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
                repeatInput.setText(repeat);
                repeatInput.setClickable(false);
                quantityInput.setText(quantity);
                quantityInput.setClickable(false);

            }
        });
    }
}
