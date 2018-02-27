package dynamicdrillers.collegenoticeboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentProfileActivity extends AppCompatActivity {

    TextView TxtName,TxtEmail,TxtMobaileNo,TxtGender,TxtDept;
    LinearLayout LinLayEnrollment,LinLayTgSem;
    public static final String SharedprefenceName = "USER_DATA";
    CircleImageView ProImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        TxtName = findViewById(R.id.stu_pro_txt_name);
        TxtEmail = findViewById(R.id.stu_pro_txt_email);
        TxtMobaileNo = findViewById(R.id.stu_pro_txt_mobaileno);
        TxtGender = findViewById(R.id.stu_pro_txt_gender);
        TxtDept = findViewById(R.id.stu_pro_txt_dept);

        SharedPreferences sharedPreference = getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);

        TxtName.setText(sharedPreference.getString("name",null));
        TxtEmail.setText(sharedPreference.getString("email",null));
        TxtMobaileNo.setText(sharedPreference.getString("mobaileno",null));
        TxtGender.setText(sharedPreference.getString("gender",null));
        TxtDept.setText(sharedPreference.getString("dept",null));

        ProImg = findViewById(R.id.pro_img);

        if(sharedPreference.getString("type",null).equals("student")){
            LinLayEnrollment = findViewById(R.id.pro_enrollment);
            LinLayEnrollment.setVisibility(View.VISIBLE);
            TextView Enrollment = LinLayEnrollment.findViewById(R.id.stu_pro_txt_enrollment);
            Enrollment.setText(sharedPreference.getString("enrollment",null));
        }

        if(sharedPreference.getString("type",null).equals("other")){
            LinLayTgSem = findViewById(R.id.pro_tg_sem);
            LinLayTgSem.setVisibility(View.VISIBLE);
            TextView TgSem = LinLayEnrollment.findViewById(R.id.stu_pro_txt_tg_sem);
            TgSem.setText(sharedPreference.getString("tgsem",null));
        }

    }
}
