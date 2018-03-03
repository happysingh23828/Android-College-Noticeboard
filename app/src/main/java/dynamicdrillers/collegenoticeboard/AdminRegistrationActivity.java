package dynamicdrillers.collegenoticeboard;

import android.app.DatePickerDialog;
import android.app.Dialog;
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

import java.util.Calendar;

public class AdminRegistrationActivity extends AppCompatActivity {

    private TextInputLayout Name,Email,Passwors,MobaileNo;
    private Button AdminNext;

    private String Url = Constants.WEB_API_URL+"AdminRegistration.php";

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
        Gender = findViewById(R.id.Gender);
        Date = findViewById(R.id.Date);

        Intent intent = getIntent();

        if(intent.getStringExtra("status").equals("prev")){
            final String NamePre = intent.getStringExtra("Name");
            final String EmailPre =intent.getStringExtra("Email");
            final String PasswordPre =intent.getStringExtra("Password");
            final String MobaleNoPre =intent.getStringExtra("MobaileNo");
            final String DatePre =intent.getStringExtra("Date");
            final String GenderPre =intent.getStringExtra("Gender");

            Name.getEditText().setText(NamePre);
            Email.getEditText().setText(EmailPre);
            Passwors.getEditText().setText(PasswordPre);
            MobaileNo.getEditText().setText(MobaleNoPre);
            Date.setText(DatePre);

            if(GenderPre.equals("Male")){
                Gender.check(R.id.Male);
                Gender_s = "Male";
            }
            else{
                Gender.check(R.id.Female);
                Gender_s = "Female";
            }

        }



        AdminNext = findViewById(R.id.AdminNext);
        AdminNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminRegistrationActivity.this,AdminRegCollegeActivity.class);
                intent.putExtra("status","next");
                intent.putExtra("Name",Name.getEditText().getText().toString());
                intent.putExtra("Email",Email.getEditText().getText().toString());
                intent.putExtra("Password",Passwors.getEditText().getText().toString());
                intent.putExtra("MobaileNo",MobaileNo.getEditText().getText().toString());
                intent.putExtra("Date",Date_s);
                intent.putExtra("Gender",Gender_s);
                startActivity(intent);
            }
        });




        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);



        Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId=Gender.getCheckedRadioButtonId();
                RadioButton radioSexButton=(RadioButton)findViewById(selectedId);
                Gender_s = radioSexButton.getText().toString();
                Toast.makeText(AdminRegistrationActivity.this,radioSexButton.getText(),Toast.LENGTH_SHORT).show();
            }
        });



    }




    private void showDate(int year, int month, int day) {
        Date_s =  new StringBuilder().append(year).append("-").append(month).append("-").append(day).toString();
        Date.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));

    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);

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
