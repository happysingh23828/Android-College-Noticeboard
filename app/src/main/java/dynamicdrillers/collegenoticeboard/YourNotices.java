package dynamicdrillers.collegenoticeboard;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
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

import dmax.dialog.SpotsDialog;

import static dynamicdrillers.collegenoticeboard.ShowNoticeByType.GET_COLLEGE_NOTICE_URL;
import static dynamicdrillers.collegenoticeboard.ShowNoticeByType.GET_TG_NOTICE_URL;

public class YourNotices extends AppCompatActivity {

    String WEB_URL = Constants.WEB_API_URL+"YourSentNotices.php";
    SharedpreferenceHelper sharedpreferenceHelper = SharedpreferenceHelper.getInstance(this);
    Toolbar toolbar;
    ImageView addnoticeicon;
    RecyclerView recyclerView;
    List<Notice> noticelist = new ArrayList<Notice>();
    YourNoticesAdaptor yourNoticesAdaptor;
    String Notice_Type = "";
    TextView notice_list_heading;
    SpotsDialog spotsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_notices);


        spotsDialog  = new SpotsDialog(this);
        final String tgnoticeflag = getIntent().getStringExtra("istgnotice");
        final String noticeflagtype = getIntent().getStringExtra("noticetype");

        notice_list_heading = (TextView)findViewById(R.id.adddpersontoolbarheading);
        addnoticeicon = (ImageView)findViewById(R.id.addpersonicon);
        toolbar = (Toolbar)findViewById(R.id.sentnoticelisttoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addnoticeicon.setImageDrawable(getDrawable(R.drawable.ic_add_notice));
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        addnoticeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AddNotice.class);
                i.putExtra("istgnotice",tgnoticeflag);
                i.putExtra("noticetype",noticeflagtype);
                startActivity(i);
            }
        });

        // Checking Which person Calling Which Notices List
        spotsDialog.show();
        if(tgnoticeflag.equals("yes"))
        {
            Notice_Type = "tg";
            showTgNotice();
        }
        else if(sharedpreferenceHelper.getType().equals("hod") && noticeflagtype.equals("dept"))
        {
            Notice_Type = "dept";
            WEB_URL = Constants.WEB_API_URL+"YourSentDeptNotices.php";
            showDeptNotice();
        }
        else if(sharedpreferenceHelper.getType().equals("hod") && noticeflagtype.equals("events"))
        {
            Notice_Type = "events";
            showCollegeNotice();
        }
        else if(sharedpreferenceHelper.getType().equals("admin") && noticeflagtype.equals("college"))
        {
            Notice_Type = "college";
            showCollegeNotice();
        }
        else if(sharedpreferenceHelper.getType().equals("other") && noticeflagtype.equals("dept"))
        {
            Notice_Type = "dept";
            WEB_URL = Constants.WEB_API_URL+"YourSentDeptNotices.php";
            showDeptNotice();
        }
        else if(sharedpreferenceHelper.getType().equals("other") && noticeflagtype.equals("events"))
        {
            Notice_Type = "events";
            showCollegeNotice();
        }
        else
        {
            Notice_Type = sharedpreferenceHelper.getRole();
            showCollegeNotice();
        }

        notice_list_heading.setText("Your Sent "+Notice_Type.toUpperCase()+" Notices ");
        recyclerView = (RecyclerView)findViewById(R.id.yoursentnoticerecyclerview);
        yourNoticesAdaptor = new YourNoticesAdaptor(noticelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(yourNoticesAdaptor);



    }

    private void showDeptNotice() {

        StringRequest stringRequest =  new StringRequest(StringRequest.Method.POST, WEB_URL, new Response.Listener<String>() {
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
                                ,singleNotice.getString("authoremail"),Notice_Type);
                        noticelist.add(notice);

                    }
                    spotsDialog.dismiss();
                } catch (JSONException e) {
                    spotsDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"There Is No Dept Notice..",Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spotsDialog.dismiss();

                Toast.makeText(getApplicationContext(),"Some Network Issues",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("Email",sharedpreferenceHelper.getEmail());
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
                                ,singleNotice.getString("authoremail"),Notice_Type);
                        noticelist.add(notice);
                spotsDialog.dismiss();
                    }
                } catch (JSONException e) {
                    spotsDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"There Is No Tg Notice..",Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spotsDialog.dismiss();

                Toast.makeText(getApplicationContext(),"Some Network Issues",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("TgEmail",sharedpreferenceHelper.getEmail());
                return param;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }

    private void showCollegeNotice() {
        StringRequest stringRequest =  new StringRequest(StringRequest.Method.POST,WEB_URL, new Response.Listener<String>() {
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
                                ,singleNotice.getString("authoremail"),Notice_Type);

                        noticelist.add(notice);

                    }
                    spotsDialog.dismiss();
                } catch (JSONException e) {
                    spotsDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"There Is No College Notice..",Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spotsDialog.dismiss();

                Toast.makeText(getApplicationContext(),"Some Network Issues",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("Email",sharedpreferenceHelper.getEmail());
                param.put("Type",Notice_Type);

                return param;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}
