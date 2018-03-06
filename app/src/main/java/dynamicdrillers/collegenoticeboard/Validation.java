package dynamicdrillers.collegenoticeboard;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mayank on 03-03-2018.
 */

public class Validation {

    public boolean emailValidation(TextInputLayout textInputLayout) {

        if (!textInputLayout.getEditText().getText().toString().isEmpty()) {
            Pattern pattern;
            Matcher matcher;
            pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(textInputLayout.getEditText().getText().toString());
            if (!matcher.matches()) {
                textInputLayout.setError("Enter Valid Email Address");
                return false;
            } else {
                textInputLayout.setErrorEnabled(false);
                return true;
            }
        } else {
            textInputLayout.setError("Enter Email Address");
            return false;
        }
    }




    public boolean mobailenoValidation(TextInputLayout textInputLayout) {

        if (!textInputLayout.getEditText().getText().toString().isEmpty()) {
            Pattern pattern;
            Matcher matcher;
            pattern = Pattern.compile("^(([+]|[0]{2})([\\d]{1,3})([\\s-]{0,1}))?([\\d]{10})$");
            matcher = pattern.matcher(textInputLayout.getEditText().getText().toString());
            if (!matcher.matches()) {
                textInputLayout.setError("Enter Valid Mobile No");
                return false;
            } else {
                textInputLayout.setErrorEnabled(false);
                return true;
            }
        }
        else {
            textInputLayout.setError("Enter Mobile No");
            return false;
        }

    }


    public boolean passwordValidation(TextInputLayout textInputLayout){
        if (textInputLayout.getEditText().getText().toString().isEmpty()) {
            textInputLayout.setError("Enter Password");
            return false;
        }
        else{
            textInputLayout.setErrorEnabled(false);
            return true;
        }

    }

    public boolean nameValidation(TextInputLayout textInputLayout){
        if (textInputLayout.getEditText().getText().toString().isEmpty()) {
            textInputLayout.setError("Enter Name");
            return false;
        }
        else{
            textInputLayout.setErrorEnabled(false);
            return true;
        }

    }

    public boolean collegeCodeValidation(TextInputLayout textInputLayout){
        if (textInputLayout.getEditText().getText().toString().isEmpty()) {
            textInputLayout.setError("Enter College Code");
            return false;
        }
        else{
            textInputLayout.setErrorEnabled(false);
            return true;
        }

    }

    public boolean collegeCityValidation(TextInputLayout textInputLayout){
        if (textInputLayout.getEditText().getText().toString().isEmpty()) {
            textInputLayout.setError("Enter College City");
            return false;
        }
        else{
            textInputLayout.setErrorEnabled(false);
            return true;
        }

    }
    public boolean collegeStateValidation(TextInputLayout textInputLayout){
        if (textInputLayout.getEditText().getText().toString().isEmpty()) {
            textInputLayout.setError("Enter College State");
            return false;
        }
        else{
            textInputLayout.setErrorEnabled(false);
            return true;
        }

    }
    public boolean collegeNameValidation(TextInputLayout textInputLayout){
        if (textInputLayout.getEditText().getText().toString().isEmpty()) {
            textInputLayout.setError("Enter College Name");
            return false;
        }
        else{
            textInputLayout.setErrorEnabled(false);
            return true;
        }

    }

    public boolean deptValidation(TextInputLayout textInputLayout){
        if (textInputLayout.getEditText().getText().toString().isEmpty()) {
            textInputLayout.setError("Enter Department");
            return false;
        }
        else{
            textInputLayout.setErrorEnabled(false);
            return true;
        }

    }

    public boolean enrollmentValidation(TextInputLayout textInputLayout){
        if (textInputLayout.getEditText().getText().toString().isEmpty()) {
            textInputLayout.setError("Enter Enrollment No");
            return false;
        }
        else{
            textInputLayout.setErrorEnabled(false);
            return true;
        }

    }

    public boolean titleValidation(TextInputLayout textInputLayout){
        if (textInputLayout.getEditText().getText().toString().isEmpty()) {
            textInputLayout.setError("Enter Title");
            return false;
        }
        else{
            textInputLayout.setErrorEnabled(false);
            return true;
        }

    }
    public boolean disValidation(EditText textInputLayout){
        if (textInputLayout.getText().toString().isEmpty()) {
            textInputLayout.setError("Enter Discription");
            return false;
        }
        else{
            return true;
        }

    }

}