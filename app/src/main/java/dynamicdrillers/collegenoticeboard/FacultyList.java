package dynamicdrillers.collegenoticeboard;

import android.content.Intent;
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

public class FacultyList extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarheading;
    ImageView toolbaraddhodicon;
    RecyclerView recyclerView;
    SpotsDialog spotsDialog;
    List<Faculty> facultyList = new ArrayList<Faculty>();
    SharedpreferenceHelper sharedpreferenceHelper = SharedpreferenceHelper.getInstance(this);
    String HODCollegecode=sharedpreferenceHelper.getCollegeCode();
    String HODdept=sharedpreferenceHelper.getDept();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_list);
        toolbarheading = (TextView)findViewById(R.id.adddpersontoolbarheading);
        toolbaraddhodicon = (ImageView)findViewById(R.id.addpersonicon);
        toolbar = (Toolbar)findViewById(R.id.faultyaddtoolbar);


        spotsDialog = new SpotsDialog(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarheading.setText("Faculty's List");
        if(sharedpreferenceHelper.getType().equals("admin"))
        {
            toolbaraddhodicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),HodRgistrationActivity.class));
                }
            });
        }
        else
        {
            toolbaraddhodicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),FacultyRegistrationActivity.class));
                }
            });
        }


        showFacultyList();
        spotsDialog.show();
        recyclerView =(RecyclerView)findViewById(R.id.facultylistrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }

    private void showFacultyList() {

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.WEB_API_URL + "HodGetFacultiesList.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject Faculty_details  = jsonArray.getJSONObject(i);

                        Faculty faculty = new Faculty(Faculty_details.getString("name"),Faculty_details.getString("email")
                                                    ,Faculty_details.getString("mobileno"),Faculty_details.getString("dob")
                                                    ,Faculty_details.getString("gender"),Faculty_details.getString("collegecode"),
                                                    Faculty_details.getString("personprofile"),Faculty_details.getString("tgflag")
                                                    ,Faculty_details.getString("tgsem"),Faculty_details.getString("dept")
                                                    ,Faculty_details.getString("role"));

                        facultyList.add(faculty);
                    }

                    FacultyListAdaptor facultyListAdaptor = new FacultyListAdaptor(facultyList);
                    recyclerView.setAdapter(facultyListAdaptor);
                    facultyListAdaptor.notifyDataSetChanged();

                    spotsDialog.dismiss();



                } catch (JSONException e) {
                    spotsDialog.dismiss();
                    Toast.makeText(getBaseContext(),"There Is No Faculty",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                spotsDialog.dismiss();
                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param  = new HashMap<>();
                param.put("CollegeCode",HODCollegecode);
                if(sharedpreferenceHelper.getType().equals("admin")){
                    param.put("Dept","admin");
                }
                else {
                    param.put("Dept", HODdept);
                }
                return  param;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
