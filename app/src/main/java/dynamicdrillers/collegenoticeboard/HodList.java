package dynamicdrillers.collegenoticeboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class HodList extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarheading;
    ImageView toolbaraddhodicon;
    SharedpreferenceHelper sharedpreferenceHelper = SharedpreferenceHelper.getInstance(this);
    String AdminCollegeCode = sharedpreferenceHelper.getCollegeCode();
    RecyclerView recyclerView;
    List<Hod> hodlist = new ArrayList<Hod>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_list);

        recyclerView = (RecyclerView)findViewById(R.id.hodlistrecyclerview);

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



        getHodlist();
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(new HodListAdaptor(hodlist));



    }

    private void getHodlist() {

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.WEB_API_URL + "AdminGetHodList.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject Hod_details  = jsonArray.getJSONObject(i);

                        Hod hod = new Hod(Hod_details.getString("name"),Hod_details.getString("email")
                                      ,Hod_details.getString("mobileno"),Hod_details.getString("dob")
                                      ,Hod_details.getString("gender"),Hod_details.getString("personphoto")
                                      ,Hod_details.getString("dept"));

                        hodlist.add(hod);
                    }



                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(),"There Is No HOD",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param  = new HashMap<>();
                param.put("CollegeCode",AdminCollegeCode);
                return  param;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
