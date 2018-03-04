package dynamicdrillers.collegenoticeboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    Button BtnLogin,BtnReg;
    Spinner SpnLoginType;
    TextInputLayout TxtLoginUsername,TxtLoginPassword;
    String URL_LOGIN="";
    String Type[] = {"Student","Admin","Hod","Faculty"};
    SpotsDialog alertDialog;
    String SelectedType="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TxtLoginUsername = findViewById(R.id.txt_login_username);
        TxtLoginPassword = findViewById(R.id.txt_login_password);
        BtnLogin = findViewById(R.id.btn_login);
        BtnReg = findViewById(R.id.btn_Registration);

        alertDialog  = new SpotsDialog(this);
        alertDialog.create();



        SpnLoginType = findViewById(R.id.spn_login_type);
        SpnLoginType.setAdapter(new ArrayAdapter<String>(this,R.layout.login_type_layout,R.id.txt_type,Type));
        SpnLoginType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = adapterView.getSelectedItemPosition();
                LinearLayout linearLayout = (LinearLayout) SpnLoginType.getSelectedView();
                TextView textView = linearLayout.findViewById(R.id.txt_type);
                textView.setTextColor(getResources().getColor(R.color.spn));
                if(textView.getText().equals("Admin")){
                    SelectedType = "Admin";
                    URL_LOGIN = Constants.WEB_API_URL+"AdminLogin.php";
                }
                else if(textView.getText().equals("Hod")){
                    SelectedType = "Hod";
                    URL_LOGIN = Constants.WEB_API_URL+"HodLogin.php";
                }
                else if(textView.getText().equals("Student")){
                    SelectedType = "Student";
                    URL_LOGIN = Constants.WEB_API_URL+"StudentLogin.php";
                }
                else{
                    SelectedType = "Faculty";
                    URL_LOGIN = Constants.WEB_API_URL+"FacultyLogin.php";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(validate())
                    {
                        userLogin();
                        alertDialog.show();
                    }



            }
        });




        BtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),AdminRegistrationActivity.class);
                i.putExtra("status","");
                startActivity(i);
            }
        });
    }
    private void userLogin() {

        final String email = TxtLoginUsername.getEditText().getText().toString();
        final String passowrd = TxtLoginPassword.getEditText().getText().toString();


        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();

                try {
                    JSONObject user_detail = new JSONObject(response);
                    if(!user_detail.getBoolean("error"))
                    {
                        alertDialog.dismiss();
                        Toast.makeText(getBaseContext(),user_detail.getString("message"),Toast.LENGTH_SHORT).show();
                        SharedpreferenceHelper sharedPreferenceHelper = SharedpreferenceHelper.getInstance(LoginActivity.this);
                        sharedPreferenceHelper.userlogin(user_detail.getString("name"),user_detail.getString("email")
                                ,user_detail.getString("collegecode")
                                ,user_detail.getString("mobileno")
                                ,user_detail.getString("dob")
                                ,user_detail.getString("gender")
                                ,user_detail.getString("type")
                                );

                        if(SelectedType.equals("Student"))
                        sharedPreferenceHelper.studentUser(user_detail.getString("studentprofile"),
                                user_detail.getString("dept"),
                                user_detail.getString("sem"),
                                user_detail.getString("tgemail"),
                                user_detail.getString("enrollment"));

                        else if(SelectedType.equals("Hod")){
                            sharedPreferenceHelper.hodUser(user_detail.getString("personphoto"),
                                    user_detail.getString("dept")
                                   );
                        }

                        else if(SelectedType.equals("Faculty"))
                            sharedPreferenceHelper.otherUser(user_detail.getString("role")
                            ,user_detail.getString("personprofile"),user_detail.getString("dept")
                            ,user_detail.getInt("tgflag")
                            ,user_detail.getString("tgsem"));

                        else
                            sharedPreferenceHelper.adminUser(user_detail.getString("profilephoto"),
                                    user_detail.getString("collegelogo"),
                                    user_detail.getString("collegename"),
                                    user_detail.getString("collegecity"),
                                    user_detail.getString("collegestate"));


                        startActivity(new Intent(getApplicationContext(),FacultyDashboard.class));
                        finish();

                    }
                    else
                    {
                        alertDialog.dismiss();
                        Toast.makeText(getBaseContext(),user_detail.getString("message"),Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    alertDialog.dismiss();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alertDialog.dismiss();

                Toast.makeText(getBaseContext(),"Some Network Issues",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Email",email);
                params.put("Password",passowrd);
                return  params;
            }
        };


      MySingleton mySingleton = MySingleton.getInstance(this);
      mySingleton.addToRequestQueue(stringRequest);

    }

    private boolean validate() {

        boolean status = true;

        Validation validation = new Validation();

        if(!validation.emailValidation(TxtLoginUsername))
            status = false;

        if(!validation.passwordValidation(TxtLoginPassword))
            status = false;

        return status;
    }



}
