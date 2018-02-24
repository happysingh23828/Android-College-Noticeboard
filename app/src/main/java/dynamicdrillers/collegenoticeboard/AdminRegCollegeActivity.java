package dynamicdrillers.collegenoticeboard;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminRegCollegeActivity extends AppCompatActivity {

    TextInputLayout CollegeName,CollegeCode,CollegeState,CollegeCity;
    Button Next,BtnAdminCollegePrev;
    String Name,Email,Password,MobaleNo,Date,Gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reg_college);


        CollegeCode = findViewById(R.id.CollegeCode);
        CollegeCity = findViewById(R.id.CollegeCity);
        CollegeState = findViewById(R.id.CollegeState);
        CollegeName = findViewById(R.id.CollegeName);


        final Intent intent = getIntent();

        if(intent.getStringExtra("status").equals("next")){
            Name = intent.getStringExtra("Name");
            Email =intent.getStringExtra("Email");
            Password =intent.getStringExtra("Password");
            MobaleNo =intent.getStringExtra("MobaileNo");
            Date =intent.getStringExtra("Date");
            Gender =intent.getStringExtra("Gender");
        }
        else
        {
            Name = intent.getStringExtra("Name");
            Email =intent.getStringExtra("Email");
            Password =intent.getStringExtra("Password");
            MobaleNo =intent.getStringExtra("MobileNo");
            Date =intent.getStringExtra("Dob");
            Gender =intent.getStringExtra("Gender");
            CollegeName.getEditText().setText(intent.getStringExtra("CollegeName"));
            CollegeCity.getEditText().setText(intent.getStringExtra("CollegeCity"));
            CollegeState.getEditText().setText(intent.getStringExtra("CollegeState"));
            CollegeCode.getEditText().setText(intent.getStringExtra("CollegeCode"));
        }


        Toast.makeText(this,Name+"\n"+Email+"\n"+Password+"\n"+MobaleNo+"\n"+Date+"\n"+Gender,Toast.LENGTH_LONG).show();



        Next = findViewById(R.id.AdminCollegeNext);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(AdminRegCollegeActivity.this,AdminRegImgActivity.class);
                intent1.putExtra("Name",Name);
                intent1.putExtra("Email",Email);
                intent1.putExtra("Password",Password);
                intent1.putExtra("MobaileNo",MobaleNo);
                intent1.putExtra("Date",Date);
                intent1.putExtra("Gender",Gender);
                intent1.putExtra("CollegeName",CollegeName.getEditText().getText().toString());
                intent1.putExtra("CollegeCode",CollegeCode.getEditText().getText().toString());
                intent1.putExtra("CollegeState",CollegeState.getEditText().getText().toString());
                intent1.putExtra("CollegeCity",CollegeCity.getEditText().getText().toString());
                startActivity(intent1);

            }
        });

        BtnAdminCollegePrev = findViewById(R.id.btn_admin_college_prev);
        BtnAdminCollegePrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(AdminRegCollegeActivity.this,AdminRegistrationActivity.class);
                intent1.putExtra("status","prev");
                intent1.putExtra("Name",Name);
                intent1.putExtra("Email",Email);
                intent1.putExtra("Password",Password);
                intent1.putExtra("MobaileNo",MobaleNo);
                intent1.putExtra("Date",Date);
                intent1.putExtra("Gender",Gender);
                startActivity(intent1);
            }
        });

    }

}
