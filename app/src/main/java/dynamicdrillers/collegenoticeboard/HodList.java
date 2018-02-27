package dynamicdrillers.collegenoticeboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HodList extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarheading;
    ImageView toolbaraddhodicon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_list);
        toolbarheading = (TextView)findViewById(R.id.adddpersontoolbarheading);
        toolbaraddhodicon = (ImageView)findViewById(R.id.addpersonicon);
        toolbar = (Toolbar)findViewById(R.id.hodlisttoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarheading.setText("HOD's List");
        toolbaraddhodicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HodRgistrationActivity.class));
            }
        });


    }
}
