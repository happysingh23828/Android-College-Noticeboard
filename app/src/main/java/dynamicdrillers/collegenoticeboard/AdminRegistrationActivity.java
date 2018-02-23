package dynamicdrillers.collegenoticeboard;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AdminRegistrationActivity extends AppCompatActivity {

    private TextInputLayout Name,Email,Passwors,MobaileNo;
    private Button AdminNext;
    private ProgressDialog progressDialog;
    private String Url = "http://192.168.1.8/Web-API-College-Noticeboard/WebServicesApi/AdminRegistration.php";

    private DatePicker datePicker;
    private Calendar calendar;

    private int year, month, day;
    private TextView Date;

    private RadioGroup Gender;

    private String Date_s,Gender_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        Passwors = findViewById(R.id.Password);
        MobaileNo = findViewById(R.id.MobaileNo);

        AdminNext = findViewById(R.id.AdminNext);
        AdminNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminRegistrationActivity.this,AdminRegCollegeActivity.class);
                intent.putExtra("Name",Name.getEditText().getText().toString());
                intent.putExtra("Email",Email.getEditText().getText().toString());
                intent.putExtra("Password",Passwors.getEditText().getText().toString());
                intent.putExtra("MobaileNo",MobaileNo.getEditText().getText().toString());
                intent.putExtra("Date",Date_s);
                intent.putExtra("Gender",Gender_s);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);


        Date = findViewById(R.id.Date);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);


        Gender = findViewById(R.id.Gender);
        Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId=Gender.getCheckedRadioButtonId();
                RadioButton radioSexButton=(RadioButton)findViewById(selectedId);
                Gender_s = radioSexButton.getText().toString();
                Toast.makeText(AdminRegistrationActivity.this,radioSexButton.getText(),Toast.LENGTH_SHORT).show();
            }
        });


        //  Register = findViewById(R.id.Rgister);
//        Register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                   registerAdmin();
//            }
//        });
    }

    public void registerAdmin(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(AdminRegistrationActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminRegistrationActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<>();
                map.put("Email",Email.getEditText().getText().toString());
                map.put("Password",Email.getEditText().getText().toString());
                map.put("CollegeCode",Email.getEditText().getText().toString());
                map.put("Name",Email.getEditText().getText().toString());
                map.put("MobileNo",Email.getEditText().getText().toString());
                map.put("Dob",Email.getEditText().getText().toString());
                map.put("Gender",Email.getEditText().getText().toString());
                map.put("CollegeName",Email.getEditText().getText().toString());
                map.put("CollegeCity",Email.getEditText().getText().toString());
                map.put("CollegeState",Email.getEditText().getText().toString());
                map.put("CollegeLogo",Email.getEditText().getText().toString());
                map.put("AdminPhoto",Email.getEditText().getText().toString());

                return  map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showDate(int year, int month, int day) {
        Date_s =  new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year).toString();
        Date.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));

    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };
}
