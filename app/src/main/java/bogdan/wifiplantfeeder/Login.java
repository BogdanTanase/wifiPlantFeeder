package bogdan.wifiplantfeeder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void logginMethod(View view){
        Intent intent = new Intent(this, Meniu.class);
        EditText usernameInput = (EditText) findViewById(R.id.usernameInput);
        EditText passwordInput = (EditText) findViewById(R.id.passwordInput);
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        if ( username.equals("a") && password.equals("a")){
            startActivity(intent);
        }
        else{
            ((EditText) findViewById(R.id.usernameInput)).setError("Wrong username");
            ((EditText) findViewById(R.id.passwordInput)).setError("Wrong password");
        }
    }
}
