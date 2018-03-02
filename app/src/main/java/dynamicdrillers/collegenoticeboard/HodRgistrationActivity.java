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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class HodRgistrationActivity extends AppCompatActivity {

    TextInputLayout TxtInputlayloutName,TxtInputlayloutEmail,TxtInputlayloutPassword,TxtInputlayloutDept;
    RadioGroup Gender;
    RadioButton RadioMale,RadioFemale;
    Button BtnRegister;
    Toolbar toolbar;
    String Url=Constants.WEB_API_URL+"HodRegistration.php",Gender_s="";
    TextView toolbarheading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_rgistration);

        TxtInputlayloutName = findViewById(R.id.reg_hod_name);
        TxtInputlayloutEmail = findViewById(R.id.reg_hod_email);
        TxtInputlayloutPassword = findViewById(R.id.reg_hod_password);
        TxtInputlayloutDept = findViewById(R.id.reg_hod_department);

        Gender = findViewById(R.id.reg_hod_gender);
        RadioMale = findViewById(R.id.reg_hod_male);
        RadioFemale = findViewById(R.id.reg_hod_female);

        BtnRegister = findViewById(R.id.reg_hod_register);

        toolbarheading = (TextView)findViewById(R.id.notice_name);
        toolbarheading.setText("Hod Registration");
        toolbar = (Toolbar)findViewById(R.id.hod_registration_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId=Gender.getCheckedRadioButtonId();
                RadioButton radioSexButton=(RadioButton)findViewById(selectedId);
                Gender_s = radioSexButton.getText().toString();
                Toast.makeText(HodRgistrationActivity.this,radioSexButton.getText(),Toast.LENGTH_SHORT).show();
            }
        });

        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
    }

    private void upload() {
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if(!jsonObject.getBoolean("error"))
                            {
                                Intent intent = new Intent(HodRgistrationActivity.this,FacultyDashboard.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(HodRgistrationActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
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
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(HodRgistrationActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
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


                map.put("Email",TxtInputlayloutEmail.getEditText().getText().toString());
                map.put("Password",TxtInputlayloutPassword.getEditText().getText().toString());
                map.put("Name",TxtInputlayloutName.getEditText().getText().toString());
                map.put("Dept",TxtInputlayloutDept.getEditText().getText().toString());
                map.put("Gender",Gender_s);
                map.put("PersonPhoto","https://cdn.pixabay.com/photo/2015/03/04/22/35/head-659652_960_720.png");
                map.put("MobileNo","9999999");
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
