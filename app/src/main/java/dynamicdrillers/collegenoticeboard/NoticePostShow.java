package dynamicdrillers.collegenoticeboard;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class NoticePostShow extends AppCompatActivity {


    android.support.v7.widget.Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView Notice_image;
    TextView Title,Desc,Time,WriterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_post_show);

        Notice_image = (ImageView)findViewById(R.id.noticeimage);
        Title = (TextView)findViewById(R.id.post_title);
        Desc = (TextView)findViewById(R.id.post_desc);
        Time = (TextView)findViewById(R.id.post_time);
        WriterName = (TextView)findViewById(R.id.post_author_name);

        String NoticeTitle = getIntent().getStringExtra("NoticeTitle");
        String NoticeDesc = getIntent().getStringExtra("NoticeDesc");
        String NoticeImage = getIntent().getStringExtra("NoticeImage");
        String NoticeTime = getIntent().getStringExtra("NoticeTime");
        String AuthorName = getIntent().getStringExtra("AuthorName");

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar1);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.CollapsingToolbarLayout1);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitle("");

        Title.setText(NoticeTitle);
        Desc.setText(NoticeDesc);
        Time.setText(NoticeTime);
        WriterName.setText(AuthorName);
        Picasso.with(getApplicationContext()).load(Constants.COLLEGE_NOTICE_STORAGE_URL+"Title"+NoticeTitle+".png").into(Notice_image);






    }
}
