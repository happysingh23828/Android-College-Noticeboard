package dynamicdrillers.collegenoticeboard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentProfileActivity extends AppCompatActivity {

    TextView TxtName,TxtEmail,TxtMobaileNo,TxtGender,TxtDob;
    ImageView EditEmail,EditName,EditMobaile,EditGender,EditDob;
    LinearLayout LinLayEnrollment,LinLayTgSem;
    public static final String SharedprefenceName = "USER_DATA";
    CircleImageView ProImg;
    Button UpdateImg,ChangePass;
    String Url = Constants.WEB_API_URL+"StudentUpdate.php";
    private int year, month, day;
    private TextView Date;
    private String Date_s;
    private Calendar calendar;
    SharedpreferenceHelper sharedpreferenceHelper = SharedpreferenceHelper.getInstance(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        TxtName = findViewById(R.id.stu_pro_txt_name);
        TxtEmail = findViewById(R.id.stu_pro_txt_email);
        TxtMobaileNo = findViewById(R.id.stu_pro_txt_mobaileno);
        TxtGender = findViewById(R.id.stu_pro_txt_gender);
        TxtDob = findViewById(R.id.stu_pro_txt_dob);

        EditName = findViewById(R.id.pro_edit_email);
        EditDob = findViewById(R.id.pro_edit_dob);
        EditEmail = findViewById(R.id.pro_edit_email);
        EditName = findViewById(R.id.pro_edit_name);
        EditGender = findViewById(R.id.pro_edit_gender);

        ProImg = (CircleImageView)findViewById(R.id.pro_img);

        if (sharedpreferenceHelper.getType().equals("other")) {
            Picasso.with(getBaseContext()).load(Constants.PERSON_PROFILE_STORAGE_URL + sharedpreferenceHelper.getPersonProfileName())
                    .into(ProImg);

        }
        else if(sharedpreferenceHelper.getType().equals("student")) {

            Picasso.with(getBaseContext()).load(Constants.STUDENT_PROFILE_STORAGE_URL + sharedpreferenceHelper.getStudentProfileName())
                    .into(ProImg);
        }
        else if (sharedpreferenceHelper.getType().equals("admin")) {

            Picasso.with(getBaseContext()).load(Constants.ADMIN_PROFILE_STORAGE_URL + sharedpreferenceHelper.getAdminProfileName())
                    .into(ProImg);
        }
        else {

            Picasso.with(getBaseContext()).load(Constants.HOD_PROFILE_STORAGE_URL + sharedpreferenceHelper.getHodProfileName())
                    .into(ProImg);
        }

        EditMobaile = findViewById(R.id.pro_edit_mobaileno);

        UpdateImg = findViewById(R.id.update_img);
        UpdateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentProfileActivity.this,UpdateProImgActivity.class));
            }
        });

        ChangePass = findViewById(R.id.pro_edi_pass);
        ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(StudentProfileActivity.this);

                View view1 =  View.inflate(StudentProfileActivity.this,R.layout.changepassworddialog,null);
                final TextInputEditText newpassword = (TextInputEditText)view1.findViewById(R.id.newpassword);
                final TextInputEditText confirmpassword = (TextInputEditText)view1.findViewById(R.id.confirmpassword);

                builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(newpassword.getText().length()>1)
                        {
                            if(newpassword.getText().toString().equals(confirmpassword.getText().toString()))
                            {
                                StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.WEB_API_URL + "ChangePassword.php",
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                try {

                                                    JSONObject jsonObject = new JSONObject(response);

                                                    Toast.makeText(StudentProfileActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();


                                                } catch (JSONException e) {

                                                    e.printStackTrace();
                                                }

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(StudentProfileActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();


                                    }
                                }){

                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String,String> param = new HashMap<>();
                                        SharedpreferenceHelper  sharedpreferenceHelper = SharedpreferenceHelper.getInstance(getApplicationContext());
                                        param.put("Email",sharedpreferenceHelper.getEmail());
                                        param.put("NewPassword",confirmpassword.getText().toString());
                                        param.put("PersonType",sharedpreferenceHelper.getType());
                                        return param;
                                    }
                                };

                                MySingleton.getInstance(StudentProfileActivity.this).addToRequestQueue(stringRequest);
                            }
                            else
                                Toast.makeText(StudentProfileActivity.this,"Password Doen Not Matched",Toast.LENGTH_SHORT).show();
                        }

                        else
                            Toast.makeText(StudentProfileActivity.this,"Enter Password",Toast.LENGTH_SHORT).show();

                    }
                });


                builder.setView(view1);
                builder.create();
                builder.show();
            }
        });







        final SharedPreferences sharedPreference = getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
        if(sharedPreference.getString("type",null).equals("other"))
        {
            Url = Constants.WEB_API_URL+"FacultyUpdate.php";
        }
        if(sharedPreference.getString("type",null).equals("admin"))
        {
            Url = Constants.WEB_API_URL+"AdminUpdate.php";
        }
        if(sharedPreference.getString("type",null).equals("hod"))
        {
            Url = Constants.WEB_API_URL+"HodUpdate.php";
        }

        TxtName.setText(sharedPreference.getString("name",null));
        TxtEmail.setText(sharedPreference.getString("email",null));
        TxtMobaileNo.setText(sharedPreference.getString("mobileno",null));
        TxtGender.setText(sharedPreference.getString("gender",null));


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        TxtDob.setText(sharedPreference.getString("dob",null));


        if(sharedPreference.getString("type",null).equals("student")){
            LinLayEnrollment = findViewById(R.id.pro_enrollment);
            LinLayEnrollment.setVisibility(View.VISIBLE);
            TextView Enrollment = LinLayEnrollment.findViewById(R.id.stu_pro_txt_enrollment);
            Enrollment.setText(sharedPreference.getString("enrollment",null));
        }

        if(sharedPreference.getString("type",null).equals("other")){
            LinLayTgSem = findViewById(R.id.pro_tg_sem);
            LinLayTgSem.setVisibility(View.VISIBLE);
            TextView TgSem = LinLayTgSem.findViewById(R.id.stu_pro_txt_tg_sem);
            TgSem.setText(sharedPreference.getString("tgsem",null));
        }




        EditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreference = StudentProfileActivity.this.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
                dialogBulder(sharedPreference.getString("email",null),"email","Enter Name...");

            }
        });

        EditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreference = StudentProfileActivity.this.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
                dialogBulder(sharedPreference.getString("name",null),"name","Enter Name...");

            }
        });

        EditMobaile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreference = StudentProfileActivity.this.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
                dialogBulder(sharedPreference.getString("mobileno",null),"mobileno","Enter mobileno...");
            }
        });


        EditGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreference = StudentProfileActivity.this.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
                dialogBulder(sharedPreference.getString("gender",null),"gender","Enter Gender...");
            }
        });

        EditDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TxtDob.setText(sharedPreference.getString("dob",null));
                showDialog(999);

            }
        });





    }

    String Gender_s = "Male";


    private void dialogBulder(String Text, final String Type,String Head){
        final Dialog dialog = new Dialog(StudentProfileActivity.this);



        SharedPreferences sharedPreference = StudentProfileActivity.this.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);

        if(Type.equals("gender")){

            dialog.setContentView(R.layout.custom_redio_dialog_layout);
            dialog.setTitle("Update...");

            final RadioGroup Gender = (RadioGroup) dialog.findViewById(R.id.gender);

            if(Gender_s.equals("Male")){
                Gender.check(R.id.male);
            }
            else{
                Gender.check(R.id.female);
            }

            Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {

                    Toast.makeText(StudentProfileActivity.this,i+"",Toast.LENGTH_LONG).show();
                     if(i==R.id.male){
                         Gender_s="Male";
                         Toast.makeText(StudentProfileActivity.this,Gender_s,Toast.LENGTH_LONG).show();
                     }
                     else
                     {
                         Gender_s="Female";
                         Toast.makeText(StudentProfileActivity.this,Gender_s,Toast.LENGTH_LONG).show();
                     }

                }
            });

            Button update = dialog.findViewById(R.id.update);
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    upload(Type,Gender_s);
                    dialog.dismiss();
                }
            });

        }
        else{
            dialog.setContentView(R.layout.custom_edittext_dialog_layout);
            dialog.setTitle("Update...");

            TextView text = (TextView) dialog.findViewById(R.id.dialog_title);
            text.setText(Head);

            final TextInputLayout textInputLayout = dialog.findViewById(R.id.dialog_edittext);
            textInputLayout.getEditText().setText(Text);

            Button dialogButton = (Button) dialog.findViewById(R.id.dialog_btn);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    upload(Type,textInputLayout.getEditText().getText().toString());
                    dialog.dismiss();
                }
            });

        }
        dialog.show();
    }




    private void upload(final String Type,final  String Data) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog


                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if(!jsonObject.getBoolean("error"))
                            {
                                SharedPreferences sharedPreference = StudentProfileActivity.this.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreference.edit();
                                editor.putString(Type,Data);
                                editor.apply();

                                Intent intent = new Intent(StudentProfileActivity.this,FacultyDashboard.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(StudentProfileActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

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


                        //Showing toast
                        Toast.makeText(StudentProfileActivity.this, "Some Netwok Issues", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                //Creating parameters


                //Adding parameters

                SharedPreferences sharedPreference = getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
                String email = sharedPreference.getString("email",null);
                Map<String, String> map = new HashMap<>();
                map.put("Email",email);
                map.put("Type",Type);
                map.put("Data",Data);


                return  map;

                //returning parameters

            }
        };




        //Adding request to the queue

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void showDate(int year, int month, int day) {

        Date_s =  new StringBuilder().append(year).append("-").append(month).append("-").append(day).toString();
        TxtDob.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
        upload("dob",TxtDob.getText().toString());
    }

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
