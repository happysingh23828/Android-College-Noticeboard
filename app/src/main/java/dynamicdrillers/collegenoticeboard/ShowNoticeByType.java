package dynamicdrillers.collegenoticeboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowNoticeByType extends AppCompatActivity {

    public  static  final String GET_NOTICE_URL="http://192.168.1.8/Web-API-College-Noticeboard/WebServicesApi/StudentGetCollegeNotice.php";
    android.support.v7.widget.Toolbar toolbar;
    String Notice_type;
    String Notice_College_Code;
    TextView noticeTitle;
    RecyclerView recyclerView;
    List<Notice> noticelist = new ArrayList<Notice>();
    NoticeShowAdaptor noticeShowAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notice_by_type);


        //getting Notice Type And College Code From Another Activity
        Notice_type = getIntent().getStringExtra("NoticeType");
        Notice_College_Code = getIntent().getStringExtra("NoticeCollegeCode");


        // Setting Toolbar......
        noticeTitle = (TextView)findViewById(R.id.notice_name);
        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.single_notice_toolbar);
        setSupportActionBar(toolbar);
        noticeTitle.setText(Notice_type + " Notices");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);


        showNotice();

        recyclerView = (RecyclerView)findViewById(R.id.noticelist_recylerview);
        noticeShowAdaptor = new NoticeShowAdaptor(noticelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(noticeShowAdaptor);


    }

    private void showNotice() {
         RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest =  new StringRequest(StringRequest.Method.POST, GET_NOTICE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray NoticeArrray = new JSONArray(response);
                    int JsonArrayLength = NoticeArrray.length();
                    for (int i  =0;i<NoticeArrray.length();i++)
                    {
                        JSONObject singleNotice =NoticeArrray.getJSONObject(i);
                        Notice notice = new Notice(singleNotice.getString("contentid")
                                ,singleNotice.getString("authorname")
                                ,singleNotice.getString("title")
                                ,singleNotice.getString("string")
                                ,singleNotice.getString("time")
                                ,singleNotice.getString("image")
                                ,singleNotice.getString("authoremail"));

                        noticelist.add(notice);

                         }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"There Is No Notice..",Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("CollegeCode",Notice_College_Code);
                param.put("Type",Notice_type);

                return param;
            }
        };

        requestQueue.add(stringRequest);

    }
}
