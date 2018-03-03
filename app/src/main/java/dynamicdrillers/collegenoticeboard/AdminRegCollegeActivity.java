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
    Button Next,Prev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reg_college);
        Intent intent = getIntent();
        final String Name = intent.getStringExtra("Name");
        final String Email =intent.getStringExtra("Email");
        final String Password =intent.getStringExtra("Password");
        final String MobaleNo =intent.getStringExtra("MobaileNo");
        final String Date =intent.getStringExtra("Date");
        final String Gender =intent.getStringExtra("Gender");



        Toast.makeText(this,Name+"\n"+Email+"\n"+Password+"\n"+MobaleNo+"\n"+Date+"\n"+Gender,Toast.LENGTH_LONG).show();

        CollegeName = findViewById(R.id.CollegeName);
        CollegeCode = findViewById(R.id.CollegeCode);
        CollegeState = findViewById(R.id.CollegeState);
        CollegeCity = findViewById(R.id.CollegeCity);

        if(intent.getStringExtra("status").equals("prev")){
            CollegeCity.getEditText().setText(intent.getStringExtra("CollegeCity"));
            CollegeState.getEditText().setText(intent.getStringExtra("CollegeState"));
            CollegeCode.getEditText().setText(intent.getStringExtra("CollegeCode"));
            CollegeName.getEditText().setText(intent.getStringExtra("CollegeName"));

        }

        Next = findViewById(R.id.AdminCollegeNext);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate()) {
                    Intent intent1 = new Intent(AdminRegCollegeActivity.this, AdminRegImgActivity.class);
                    intent1.putExtra("Name", Name);
                    intent1.putExtra("Email", Email);
                    intent1.putExtra("Password", Password);
                    intent1.putExtra("MobaileNo", MobaleNo);
                    intent1.putExtra("Date", Date);
                    intent1.putExtra("Gender", Gender);
                    intent1.putExtra("CollegeName", CollegeName.getEditText().getText().toString());
                    intent1.putExtra("CollegeCode", CollegeCode.getEditText().getText().toString());
                    intent1.putExtra("CollegeState", CollegeState.getEditText().getText().toString());
                    intent1.putExtra("CollegeCity", CollegeCity.getEditText().getText().toString());
                    startActivity(intent1);
                }

            }
        });

        Prev = findViewById(R.id.btn_admin_college_prev);
        Prev.setOnClickListener(new View.OnClickListener() {
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

    public boolean validate(){
        boolean status = true;

        Validation validation = new Validation();

        if(!validation.collegeNameValidation(CollegeName))
            status = false;


        if(!validation.collegeCodeValidation(CollegeCode))
            status = false;

        if(!validation.collegeCityValidation(CollegeCity))
            status = false;

        if(!validation.collegeStateValidation(CollegeState))
            status = false;


        return status;
    }

}
