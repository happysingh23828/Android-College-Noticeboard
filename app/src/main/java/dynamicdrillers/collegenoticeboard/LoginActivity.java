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

public class LoginActivity extends AppCompatActivity {

    Button BtnLogin;
    Spinner SpnLoginType;
    TextInputLayout TxtLoginUsername,TxtLoginPassword;
    String URL_LOGIN="";
    String Type[] = {"Student","Admin","Other"};
    String SelectedType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TxtLoginUsername = findViewById(R.id.txt_login_username);
        TxtLoginPassword = findViewById(R.id.txt_login_password);
        BtnLogin = findViewById(R.id.btn_login);

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
                    URL_LOGIN = "http://192.168.56.1/Web-API-College-Noticeboard/WebServicesApi/AdminLogin.php";
                }
                else if(textView.getText().equals("Student")){
                    SelectedType = "Student";
                    URL_LOGIN = "http://192.168.56.1/Web-API-College-Noticeboard-master/WebServicesApi/StudentLogin.php";
                }
                else{
                    SelectedType = "Other";
                    URL_LOGIN = "http://192.168.56.1/Web-API-College-Noticeboard-master/WebServicesApi/FacultyLogin.php";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }
    private void userLogin() {

        final String email = TxtLoginUsername.getEditText().getText().toString();
        final String passowrd = TxtLoginPassword.getEditText().getText().toString();


        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject user_detail = new JSONObject(response);
                    if(!user_detail.getBoolean("error"))
                    {
                        SharedpreferenceHelper sharedPreferenceHelper = SharedpreferenceHelper.getInstance(LoginActivity.this);
                        sharedPreferenceHelper.userlogin(user_detail.getString("name"),user_detail.getString("email")
                                ,user_detail.getString("collegecode")
                                ,user_detail.getString("mobileno"),user_detail.getString("dob")
                                ,user_detail.getString("gender"),user_detail.getString("type"));

                        if(SelectedType.equals("Student"))
                        sharedPreferenceHelper.studentUser(user_detail.getString("studentprofile"),
                                user_detail.getString("dept"),
                                user_detail.getString("sem"),
                                user_detail.getString("tgemail"),
                                user_detail.getString("enrollment"));

                        else if(SelectedType.equals("Other"))
                            sharedPreferenceHelper.otherUser(user_detail.getString("role")
                            ,user_detail.getString("personprofile"));

                        else
                            sharedPreferenceHelper.studentUser(user_detail.getString("profilephoto"),
                                    user_detail.getString("collegelogo"),
                                    user_detail.getString("collegename"),
                                    user_detail.getString("collegecity"),
                                    user_detail.getString("collegestate"));


                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                        Toast.makeText(getBaseContext(),user_detail.getString("message"),Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(getBaseContext(),user_detail.getString("message"),Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
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
                Map<String,String> params = new HashMap<>();
                params.put("Email",email);
                params.put("Password",passowrd);
                return  params;
            }
        };


      MySingleton mySingleton = MySingleton.getInstance(this);
      mySingleton.addToRequestQueue(stringRequest);

    }
}
