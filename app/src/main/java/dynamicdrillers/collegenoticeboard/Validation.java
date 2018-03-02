package dynamicdrillers.collegenoticeboard;

import android.support.design.widget.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mayank on 03-03-2018.
 */

public class Validation {

    public void emailValidation(TextInputLayout textInputLayout) {

        if (!textInputLayout.getEditText().getText().toString().isEmpty()) {

                Pattern pattern;
                Matcher matcher;
                pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(textInputLayout.getEditText().getText().toString());
                if(!matcher.matches()){
                    textInputLayout.setError("Enter Valid Email Address");
                }

        }
        else{
            textInputLayout.setError("Enter Email Address");
        }
    }
}