package dynamicdrillers.collegenoticeboard;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mayank on 24-02-2018.
 */

public class SharedpreferenceHelper {
    private static Context mCtx;
    private static SharedpreferenceHelper mInstance;
    public static final String SharedprefenceName = "USER_DATA";


    private SharedpreferenceHelper(Context context) {
        mCtx = context;
    }

    public static synchronized SharedpreferenceHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedpreferenceHelper(context);
        }
        return mInstance;
    }

    public boolean isLogIn() {
        SharedPreferences sharedPreference = mCtx.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);

        if (sharedPreference.getString("email", null) != null) {
            return true;
        } else
            return false;
    }

    public boolean logout() {
        SharedPreferences sharedPreference = mCtx.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.clear();
        editor.apply();
        return true;

    }

    public boolean userlogin(String Email,  String CollegeCode, String Name,String Type) {
        SharedPreferences sharedPreference = mCtx.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString("collegecode", CollegeCode);
        editor.putString("email", Email);
        editor.putString("name",Name);
        editor.putString("type",Type);

        editor.apply();
        return true;
    }


    public String getName() {
        SharedPreferences sharedPreference = mCtx.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);

        return sharedPreference.getString("name", null);

    }

    public String getEmail() {
        SharedPreferences sharedPreference = mCtx.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);

        return sharedPreference.getString("email", null);

    }

    public String getCollegeCode()
    {
        SharedPreferences sharedPreference =mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);

        return sharedPreference.getString("collegecode",null);


    }

    public String getType()
    {
        SharedPreferences sharedPreference =mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);

        return sharedPreference.getString("type",null);


    }

}
