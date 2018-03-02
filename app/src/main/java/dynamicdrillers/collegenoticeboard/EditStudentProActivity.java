package dynamicdrillers.collegenoticeboard;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditStudentProActivity extends AppCompatActivity {

    TextInputLayout TxtName,TxtPassword,TxtMobaileNo,TxtEnrollment;
    Spinner SpnSem;
    private DatePicker datePicker;
    private String Date_s,Gender_s;
    private Calendar calendar;
    private int year, month, day;
    private TextView Date;
    public static final String SharedprefenceName = "USER_DATA";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_pro);

        TxtName = findViewById(R.id.edt_std_pro_name);
        TxtPassword = findViewById(R.id.edt_std_pro_password);
        TxtMobaileNo = findViewById(R.id.edt_std_pro_mobaileno);
        TxtEnrollment = findViewById(R.id.edt_std_pro_enrollment);

        Date = findViewById(R.id.edt_std_pro_dob);


    }

    private void showDate(int year, int month, int day) {
        Date_s =  new StringBuilder().append(year).append("-").append(month).append("-").append(day).toString();
        Date.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));

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

    private void setValues()
    {
        SharedPreferences sharedPreference = getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
        TxtName.getEditText().setText(sharedPreference.getString("name",null));
        TxtEnrollment.getEditText().setText(sharedPreference.getString("enrollment",null));
        TxtMobaileNo.getEditText().setText(sharedPreference.getString("mobaileno",null));
        Date.setText(sharedPreference.getString("dob",null));
    }
}
