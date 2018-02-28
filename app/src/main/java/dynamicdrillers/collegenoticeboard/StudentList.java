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

public class StudentList extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarheading;
    ImageView toolbaraddhodicon;
    RecyclerView recyclerView;
    List<Student> studentList = new ArrayList<Student>();
    SharedpreferenceHelper sharedpreferenceHelper = SharedpreferenceHelper.getInstance(this);
    String FacultyEmail = sharedpreferenceHelper.getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        toolbarheading = (TextView)findViewById(R.id.adddpersontoolbarheading);
        toolbaraddhodicon = (ImageView)findViewById(R.id.addpersonicon);
        toolbar = (Toolbar)findViewById(R.id.studentlisttoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarheading.setText("Students's List");
        toolbaraddhodicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),StudentRegistrationActivity.class));
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.studentlistrecylerview);
        showStudentList();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new StudentListAdaptor(studentList));

    }

    private void showStudentList() {

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.WEB_API_URL + "FacultyGetStudentList.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject Student_details  = jsonArray.getJSONObject(i);
                        Student student = new Student(Student_details.getString("name"),Student_details.getString("email")
                        ,Student_details.getString("mobileno"),Student_details.getString("dob"),Student_details.getString("gender")
                        ,Student_details.getString("collegecode"),Student_details.getString("studentprofile"),Student_details.getString("dept")
                        ,Student_details.getString("sem"),Student_details.getString("tgemail"),Student_details.getString("enrollment"));



                        studentList.add(student);
                    }



                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(),"There Is No Student",Toast.LENGTH_LONG).show();
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
                param.put("TgEmail",FacultyEmail);

                return  param;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }
}
