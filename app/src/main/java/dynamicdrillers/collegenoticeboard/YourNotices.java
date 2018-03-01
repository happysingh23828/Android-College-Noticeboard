package dynamicdrillers.collegenoticeboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class YourNotices extends AppCompatActivity {

    Toolbar toolbar;
    ImageView addnoticeicon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_notices);

        addnoticeicon = (ImageView)findViewById(R.id.addpersonicon);
        toolbar = (Toolbar)findViewById(R.id.sentnoticelisttoolbar);
        addnoticeicon.setImageDrawable(getDrawable(R.drawable.ic_add_notice));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        addnoticeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddNotice.class));
            }
        });

    }
}
