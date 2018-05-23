package i.com.uberforwalking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GoingHomeActivity extends AppCompatActivity {
    Button home;
    Intent reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_going_home);
        home = (Button) findViewById(R.id.button4);
        reset = new Intent(this, SignIn.class);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(reset);
            }
        });
    }

}
