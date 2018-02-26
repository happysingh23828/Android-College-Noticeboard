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

    public boolean userlogin(String Name,String Email,  String CollegeCode
            ,String MobaileNo,String Dob,String Gender,String Type) {

        SharedPreferences sharedPreference = mCtx.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();

        editor.putString("name",Name);
        editor.putString("collegecode", CollegeCode);
        editor.putString("email", Email);
        editor.putString("mobaileno",MobaileNo);
        editor.putString("gender",Gender);
        editor.putString("dob",Dob);
        editor.putString("type",Type);

        editor.apply();
        return true;
    }

    public boolean adminUser(String profilephoto,String collegelogo,  String collegename
            ,String collegecity,String collegestate) {

        SharedPreferences sharedPreference = mCtx.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();

        editor.putString("profilephoto",profilephoto);
        editor.putString("collegelogo",collegelogo);
        editor.putString("collegename",collegename);
        editor.putString("collegecity",collegecity);
        editor.putString("collegestate",collegestate);
        editor.apply();

        return true;
    }

    public boolean studentUser(String StudentProfile,String Dept,  String Sem
            ,String TgEmail,String Enrollment) {

        SharedPreferences sharedPreference = mCtx.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();

        editor.putString("studentprofile",StudentProfile);
        editor.putString("dept",Dept);
        editor.putString("sem",Sem);
        editor.putString("tgemail",TgEmail);
        editor.putString("enrollment",Enrollment);
        editor.apply();

        return true;
    }

    public boolean otherUser(String Role,String PersonProfile,String Dept,int TgFlag,String TgSem) {

        SharedPreferences sharedPreference = mCtx.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString("dept",Dept);
        editor.putString("role",Role);
        editor.putString("PersonProfile",PersonProfile);
        editor.putInt("tgflag",TgFlag);
        editor.putString("tgsem",TgSem);


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

    public  String getRole()
    {
        SharedPreferences sharedPreference =mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);
        return sharedPreference.getString("role",null);

    }

    public String getDept()
    {
        SharedPreferences sharedPreference =mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);
        return sharedPreference.getString("dept",null);

    }

    public  int getTgflag()
    {
        SharedPreferences sharedPreference =mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);
        return sharedPreference.getInt("tgflag",0);

    }

    public  String getTgSem()
    {
        SharedPreferences sharedPreference =mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);
        return sharedPreference.getString("tgsem",null);

    }

    public  String getSem()
    {
        SharedPreferences sharedPreference =mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);
        return sharedPreference.getString("sem",null);

    }

    public  String getPersonProfileName()
    {
        SharedPreferences sharedPreference =mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);
        return sharedPreference.getString("PersonProfile",null);
    }

    public  String getStudentProfileName()
    {
        SharedPreferences sharedPreference =mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);
        return sharedPreference.getString("studentprofile",null);
    }

    public  String getAdminProfileName()
    {
        SharedPreferences sharedPreference =mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);
        return sharedPreference.getString("profilephoto",null);
    }

    public  String getCollegeLogoName()
    {
        SharedPreferences sharedPreference =mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);
        String collegelogoname = "Logo"+sharedPreference.getString("collegecode",null)+".png";
        return collegelogoname;
    }

    public  String getCollegeAddress()
    {
        SharedPreferences sharedPreference =mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);
        return sharedPreference.getString("collegecode",null);
    }

}
