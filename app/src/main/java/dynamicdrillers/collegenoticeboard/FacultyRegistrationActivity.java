package dynamicdrillers.collegenoticeboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class FacultyRegistrationActivity extends AppCompatActivity {

    TextInputLayout TxtInputlayloutName,TxtInputlayloutEmail,TxtInputlayloutPassword,TxtInputlayloutRole,TxtInputlayloutDept;
    Spinner SpnSem;
    RadioGroup Gender;
    RadioButton RadioMale,RadioFemale;
    CheckBox Tg;
    Button BtnRegister;
    String Type[] = {"1","2","3","4","5","6","7","8"};
    String Tg_s="0",Url="http://192.168.56.1/Web-API-College-Noticeboard/WebServicesApi/FacultyRegistration.php",Gender_s="",TgSem_s="false";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_registration);

        TxtInputlayloutName = findViewById(R.id.reg_faculty_name);
        TxtInputlayloutEmail = findViewById(R.id.reg_faculty_email);
        TxtInputlayloutPassword = findViewById(R.id.reg_faculty_password);
        TxtInputlayloutRole = findViewById(R.id.reg_faculty_role);
        TxtInputlayloutDept = findViewById(R.id.reg_faculty_department);

        Gender = findViewById(R.id.reg_faculty_gender);
        RadioMale = findViewById(R.id.reg_faculty_male);
        RadioFemale = findViewById(R.id.reg_faculty_female);

        Tg = findViewById(R.id.reg_faculty_tg);
        SpnSem = findViewById(R.id.reg_faculty_sem);

        BtnRegister = findViewById(R.id.reg_faculty_register);

        Tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    SpnSem.setEnabled(true);
                    SpnSem.setClickable(true);
                    Tg_s="1";
                }
                else{
                    SpnSem.setEnabled(false);
                    SpnSem.setClickable(false);
                    Tg_s="0";
                    TgSem_s = "false";
                }
            }
        });

        SpnSem.setEnabled(false);
        SpnSem.setClickable(false);
        SpnSem.setAdapter(new ArrayAdapter<String>(this,R.layout.login_type_layout,R.id.txt_type,Type));
        SpnSem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout linearLayout = (LinearLayout) SpnSem.getSelectedView();
                TextView textView = linearLayout.findViewById(R.id.txt_type);
                textView.setTextColor(getResources().getColor(R.color.spn));
                TgSem_s = textView.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId=Gender.getCheckedRadioButtonId();
                RadioButton radioSexButton=(RadioButton)findViewById(selectedId);
                Gender_s = radioSexButton.getText().toString();
                Toast.makeText(FacultyRegistrationActivity.this,radioSexButton.getText(),Toast.LENGTH_SHORT).show();
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
                            if(jsonObject.getBoolean("error"))
                            {
                                Intent intent = new Intent(FacultyRegistrationActivity.this,FacultyDashboard.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(FacultyRegistrationActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(FacultyRegistrationActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
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
                map.put("Role",TxtInputlayloutRole.getEditText().getText().toString());
                map.put("Dept",TxtInputlayloutDept.getEditText().getText().toString());
                map.put("Gender",Gender_s);
                map.put("TgFlag",Tg_s);
                map.put("TgSem",TgSem_s);
                map.put("PersonPhoto","https://cdn.pixabay.com/photo/2015/03/04/22/35/head-659652_960_720.png");
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

