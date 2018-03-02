package dynamicdrillers.collegenoticeboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowNoticeByType extends AppCompatActivity {

    public  static  final String GET_COLLEGE_NOTICE_URL=Constants.WEB_API_URL+"StudentGetCollegeNotice.php";
    public  static  final String GET_TG_NOTICE_URL=Constants.WEB_API_URL+"StudentGetTgNotice.php";
    android.support.v7.widget.Toolbar toolbar;
    String Notice_type,Notice_Tg_Email;
    String Notice_College_Code;
    TextView noticeTitle;
    RecyclerView recyclerView;
    List<Notice> noticelist = new ArrayList<Notice>();
    NoticeShowAdaptor noticeShowAdaptor;
    SharedpreferenceHelper sharedpreferenceHelper = SharedpreferenceHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notice_by_type);


        //getting Notice Type And College Code From Another Activity
        Notice_Tg_Email = getIntent().getStringExtra("tgemail");
        Notice_type = getIntent().getStringExtra("NoticeType");
        Notice_College_Code = getIntent().getStringExtra("NoticeCollegeCode");
        // Setting Toolbar......
        noticeTitle = (TextView)findViewById(R.id.notice_name);
        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.single_notice_toolbar);
        setSupportActionBar(toolbar);
        noticeTitle.setText(Notice_type.toUpperCase() + " NOTICES");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);


        if(Notice_type.equals("dept"))
        {
            showDeptNotices();
        }
        else if(Notice_Tg_Email.equals("notg"))
        {
            showCollegeNotice();
        }
        else
        {
            showTgNotice();
        }


        recyclerView = (RecyclerView)findViewById(R.id.noticelist_recylerview);
        noticeShowAdaptor = new NoticeShowAdaptor(noticelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(noticeShowAdaptor);


    }

    private void showDeptNotices() {


        StringRequest stringRequest =  new StringRequest(StringRequest.Method.POST, Constants.WEB_API_URL+"StudentGetDeptNotice.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray NoticeArrray = new JSONArray(response);
                    int JsonArrayLength = NoticeArrray.length();
                    for (int i  =0;i<NoticeArrray.length();i++)
                    {
                        JSONObject singleNotice =NoticeArrray.getJSONObject(i);
                        Notice notice = new Notice(singleNotice.getString("id")
                                ,singleNotice.getString("authorname")
                                ,singleNotice.getString("title")
                                ,singleNotice.getString("string")
                                ,singleNotice.getString("time")
                                ,singleNotice.getString("image")
                                ,singleNotice.getString("authoremail"),"dept");
                        noticelist.add(notice);

                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

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
                param.put("CollegeCode",sharedpreferenceHelper.getCollegeCode());
                param.put("Dept",sharedpreferenceHelper.getDept());
                return param;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);



    }

    private void showTgNotice() {

        StringRequest stringRequest =  new StringRequest(StringRequest.Method.POST, GET_TG_NOTICE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

               try {
                    JSONArray NoticeArrray = new JSONArray(response);
                    int JsonArrayLength = NoticeArrray.length();
                    for (int i  =0;i<NoticeArrray.length();i++)
                    {
                        JSONObject singleNotice =NoticeArrray.getJSONObject(i);
                        Notice notice = new Notice(singleNotice.getString("id")
                                ,singleNotice.getString("authorname")
                                ,singleNotice.getString("title")
                                ,singleNotice.getString("string")
                                ,singleNotice.getString("time")
                                ,singleNotice.getString("image")
                                ,singleNotice.getString("authoremail"),"tg");
                        noticelist.add(notice);

                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

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
                param.put("TgEmail",Notice_Tg_Email);
                return param;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }

    private void showCollegeNotice() {
        StringRequest stringRequest =  new StringRequest(StringRequest.Method.POST,GET_COLLEGE_NOTICE_URL, new Response.Listener<String>() {
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
                                ,singleNotice.getString("authoremail"),Notice_type);

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

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}
