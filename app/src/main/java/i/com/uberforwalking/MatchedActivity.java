package i.com.uberforwalking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MatchedActivity extends AppCompatActivity {

    Button cancel, accept;
    TextView t;
    TextView partnerName;
    TextView locLat;
    TextView locLong;
    TextView destLat;
    TextView destLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String response = intent.getStringExtra("RESPONSE");
        setContentView(R.layout.activity_matched);

        partnerName = (TextView) findViewById(R.id.textView2);
        locLat = (TextView) findViewById(R.id.editTextt);
        locLong = (TextView) findViewById(R.id.editText3);
        destLat = (TextView) findViewById(R.id.editText4);
        destLong = (TextView) findViewById(R.id.editText5);

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                restartHome(v);
            }
        });
        String [] a = response.split(";");
        partnerName.setText(a[0]);
        locLat.setText(a[1]);
        locLong.setText(a[2]);
        destLat.setText(a[3]);
        destLong.setText(a[4]);
        accept = (Button) findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                acceptPartner(v);
            }
        });
    }

    public void restartHome(View view)
    {
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }

    public void acceptPartner(View view)
    {
        Intent intent = new Intent(this, GoingHomeActivity.class);
        startActivity(intent);
    }
}
