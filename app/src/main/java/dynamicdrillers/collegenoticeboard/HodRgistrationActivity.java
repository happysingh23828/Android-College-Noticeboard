package dynamicdrillers.collegenoticeboard;

import android.app.Dialog;
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

public class HodRgistrationActivity extends AppCompatActivity {

    TextInputLayout TxtInputlayloutName,TxtInputlayloutEmail,TxtInputlayloutPassword;
    RadioGroup Gender;
    RadioButton RadioMale,RadioFemale;
    Button BtnRegister;
    Toolbar toolbar;
    LinearLayout LayoutDept;

    String Url=Constants.WEB_API_URL+"HodRegistration.php",Gender_s="Male";
    TextView toolbarheading;
    Spinner SpnRole,SpnDept;
    String Role_s="hod",Dept_s,Url1=Constants.WEB_API_URL+"FacultyRegistration.php";
    String Type[] = {"Accounts","Hod","Scholarship","TNP"};
    String Dept[] = {"Cse","Me","Ec","Add new"};
    String s[];
    SpotsDialog spotsDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_rgistration);

        spotsDialog = new SpotsDialog(this);
        TxtInputlayloutName = findViewById(R.id.reg_hod_name);
        TxtInputlayloutEmail = findViewById(R.id.reg_hod_email);
        TxtInputlayloutPassword = findViewById(R.id.reg_hod_password);
        LayoutDept = findViewById(R.id.layout_dept);


        Gender = findViewById(R.id.reg_hod_gender);
        RadioMale = findViewById(R.id.reg_hod_male);
        RadioFemale = findViewById(R.id.reg_hod_female);

        SpnRole = findViewById(R.id.reg_hod_role);
        SpnDept = findViewById(R.id.reg_hod_dept);
        BtnRegister = findViewById(R.id.reg_hod_register);

        toolbarheading = (TextView)findViewById(R.id.notice_name);
        toolbarheading.setText("Staff Registration");
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
                    }
        });

        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    upload();
                    spotsDialog.show();
                }
            }
        });

        SpnRole.setAdapter(new ArrayAdapter<String>(this,R.layout.login_type_layout,R.id.txt_type,Type));
        SpnRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout linearLayout = (LinearLayout) SpnRole.getSelectedView();
                TextView textView = linearLayout.findViewById(R.id.txt_type);
                textView.setTextColor(getResources().getColor(R.color.spn));
                Role_s = textView.getText().toString().toLowerCase();
                if(Role_s.equals("hod"))
                {
                     LayoutDept.setVisibility(View.VISIBLE);
                }
                else
                {
                     LayoutDept.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.login_type_layout,R.id.txt_type,Dept);
        SpnDept.setAdapter(adapter);
        SpnDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout linearLayout = (LinearLayout) SpnDept.getSelectedView();
                TextView textView = linearLayout.findViewById(R.id.txt_type);
                textView.setTextColor(getResources().getColor(R.color.white));

                Dept_s = textView.getText().toString().toLowerCase();

                if(Dept_s.equals("add new"))
                {
                    Toast.makeText(HodRgistrationActivity.this,Dept_s,Toast.LENGTH_LONG).show();

                    final Dialog dialog = new Dialog(HodRgistrationActivity.this);
                    dialog.setContentView(R.layout.custom_edittext_dialog_layout);

                    TextView text = (TextView) dialog.findViewById(R.id.dialog_title);
                    text.setText("Add new");
                    final TextInputLayout textInputLayout = dialog.findViewById(R.id.dialog_edittext);



                    Button dialogButton = (Button) dialog.findViewById(R.id.dialog_btn);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            s = new  String[Dept.length+1];
                            for(int i = 0 ; i < Dept.length-1 ; i++){
                                s[i] = Dept[i];
                            }

                            s[Dept.length-1] =  textInputLayout.getEditText().getText().toString();
                            s[Dept.length] = "Add new";
                            Dept = s;
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(HodRgistrationActivity.this,R.layout.login_type_layout,R.id.txt_type,Dept);
                            SpnDept.setAdapter(adapter);
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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



        return status;
    }


    private void upload() {
        if(!Role_s.equals("hod"))
            Url=Url1;

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
                                Toast.makeText(HodRgistrationActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

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
                        spotsDialog.dismiss();

                        //Showing toast
                        Toast.makeText(HodRgistrationActivity.this, "Some Network Issues", Toast.LENGTH_LONG).show();
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


                map.put("Email",TxtInputlayloutEmail.getEditText().getText().toString().toLowerCase());
                map.put("Password",TxtInputlayloutPassword.getEditText().getText().toString());
                map.put("Name",TxtInputlayloutName.getEditText().getText().toString());

                if(Role_s.equals("hod"))
                    map.put("Dept",Dept_s);
                else
                    map.put("Dept","");

                map.put("Gender",Gender_s);
                map.put("PersonPhoto","https://cdn.pixabay.com/photo/2015/03/04/22/35/head-659652_960_720.png");
                map.put("MobileNo","9999999");
                map.put("Dob","2018-1-1");
                map.put("CollegeCode",CollegeCode);

                if(!Role_s.equals("hod"))
                {
                    map.put("Role",Role_s);
                    map.put("TgFlag","false");
                    map.put("TgSem","false");

                }
                return  map;

                //returning parameters

            }
        };




        //Adding request to the queue

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

}
