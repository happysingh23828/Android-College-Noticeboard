package dynamicdrillers.collegenoticeboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class StudentRegistrationActivity extends AppCompatActivity {

    TextInputLayout TxtInputlayloutName,TxtInputlayloutEmail,TxtInputlayloutPassword,TxtInputlayloutEnrollment;
    RadioGroup Gender;
    RadioButton RadioMale,RadioFemale;
    Button BtnRegister;
    Spinner SpnSem;
    String Url=Constants.WEB_API_URL+"FacultyStudentRegistration.php",Gender_s="",Sem_s;
    String Type[] = {"1","2","3","4","5","6","7","8"};
    Toolbar toolbar;
    TextView toolbarheading;
    SpotsDialog spotsDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        spotsDialog  = new SpotsDialog(this);
        TxtInputlayloutName = findViewById(R.id.reg_student_name);
        TxtInputlayloutEmail = findViewById(R.id.reg_student_email);
        TxtInputlayloutPassword = findViewById(R.id.reg_student_password);
        TxtInputlayloutEnrollment = findViewById(R.id.reg_stdent_enrollment);

        Gender = findViewById(R.id.reg_student_gender);
        RadioMale = findViewById(R.id.reg_student_male);
        RadioFemale = findViewById(R.id.reg_student_female);

        SpnSem = findViewById(R.id.reg_student_sem);

        BtnRegister = findViewById(R.id.reg_student_register);

        toolbarheading = (TextView)findViewById(R.id.notice_name);
        toolbar = (Toolbar)findViewById(R.id.studentregistrationtoolbar);
        setSupportActionBar(toolbar);
        toolbarheading.setText("Student Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);


        Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId=Gender.getCheckedRadioButtonId();
                RadioButton radioSexButton=(RadioButton)findViewById(selectedId);
                Gender_s = radioSexButton.getText().toString();
              }
        });


        SpnSem.setAdapter(new ArrayAdapter<String>(this,R.layout.login_type_layout,R.id.txt_type,Type));
        SpnSem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout linearLayout = (LinearLayout) SpnSem.getSelectedView();
                TextView textView = linearLayout.findViewById(R.id.txt_type);
                textView.setTextColor(getResources().getColor(R.color.spn));
                Sem_s = textView.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    upload();
                    spotsDialog.show();
                }

            }
        });

    }

    private boolean validate() {

        boolean status = true;

        Validation validation = new Validation();

        if(!validation.nameValidation(TxtInputlayloutName))
            status = false;


        if(!validation.emailValidation(TxtInputlayloutEmail))
            status = false;

        if(!validation.passwordValidation(TxtInputlayloutPassword))
            status = false;

        if(!validation.deptValidation(TxtInputlayloutEnrollment))
            status = false;


        return status;
    }

    private void upload() {
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        spotsDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if(!jsonObject.getBoolean("error"))
                            {
                                Toast.makeText(StudentRegistrationActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(StudentRegistrationActivity.this,FacultyDashboard.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(StudentRegistrationActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        spotsDialog.dismiss();

                        //Showing toast
                        Toast.makeText(StudentRegistrationActivity.this, "Some Network Issues", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {





                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                Map<String,String> map = new HashMap<>();

                SharedPreferences sharedPreference = getSharedPreferences(SharedpreferenceHelper.SharedprefenceName, Context.MODE_PRIVATE);
                String CollegeCode = sharedPreference.getString("collegecode", null);
                String dept = sharedPreference.getString("dept", null);
                String tgemail = sharedPreference.getString("email", null);


                map.put("Email",TxtInputlayloutEmail.getEditText().getText().toString().toLowerCase());
                map.put("Password",TxtInputlayloutPassword.getEditText().getText().toString());
                map.put("Name",TxtInputlayloutName.getEditText().getText().toString());
                map.put("Gender",Gender_s);
                map.put("Sem",Sem_s);
                map.put("Dept",dept);
                map.put("TgEmail",tgemail);
                map.put("Enrollment",TxtInputlayloutEnrollment.getEditText().getText().toString());
                map.put("StudentPhoto","https://cdn.pixabay.com/photo/2015/03/04/22/35/head-659652_960_720.png");
                map.put("MobileNo","9999999999");
                map.put("Dob","2018-1-1");
                map.put("CollegeCode",CollegeCode);

                return  map;

                //returning parameters

            }
        };




        //Adding request to the queue

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

}
