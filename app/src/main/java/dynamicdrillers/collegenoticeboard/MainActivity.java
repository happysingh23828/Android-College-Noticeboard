package dynamicdrillers.collegenoticeboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button,BtnMainLogin;
    Button img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedpreferenceHelper sharedPreferenceHelper = SharedpreferenceHelper.getInstance(this);
        if(!sharedPreferenceHelper.isLogIn()){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        button = findViewById(R.id.Register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AdminRegistrationActivity.class);
                intent.putExtra("status","next");
                startActivity(intent);
            }
        });
        BtnMainLogin = findViewById(R.id.btn_main_login);
        BtnMainLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        img = findViewById(R.id.image);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Main2Activity.class));

            }
        });
    }
}
