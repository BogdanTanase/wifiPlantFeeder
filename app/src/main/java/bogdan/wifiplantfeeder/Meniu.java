package bogdan.wifiplantfeeder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Meniu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meniu);
    }
    public void goToEdit(View view){
        Intent intent = new Intent(this, Edit.class);
        startActivity(intent);
    }
}
