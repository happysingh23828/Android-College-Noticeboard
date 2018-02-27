package dynamicdrillers.collegenoticeboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class StudentProfileActivity extends AppCompatActivity {

    TextView TxtName,TxtEmail,TxtPassword,TxtMobaileNo,TxtGender,TxtDept;
    public static final String SharedprefenceName = "USER_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        TxtName = findViewById(R.id.stu_pro_txt_name);
        TxtEmail = findViewById(R.id.stu_pro_txt_email);
        TxtPassword = findViewById(R.id.stu_pro_txt_password);
        TxtMobaileNo = findViewById(R.id.stu_pro_txt_mobaileno);
        TxtGender = findViewById(R.id.stu_pro_txt_gender);
        TxtDept = findViewById(R.id.stu_pro_txt_dept);

        SharedPreferences sharedPreference = getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);

        TxtName.setText(sharedPreference.getString("name",null));
        TxtEmail.setText(sharedPreference.getString("email",null));
        TxtPassword.setText(sharedPreference.getString("password",null));
        TxtMobaileNo.setText(sharedPreference.getString("mobaileno",null));
        TxtGender.setText(sharedPreference.getString("gender",null));
        TxtDept.setText(sharedPreference.getString("dept",null));

    }
}
