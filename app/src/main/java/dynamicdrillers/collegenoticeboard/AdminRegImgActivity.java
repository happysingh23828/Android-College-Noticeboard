package dynamicdrillers.collegenoticeboard;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class AdminRegImgActivity extends AppCompatActivity {

    Button AdminImgBtn,CollegeLogoBtn,RegisterAdmin,BtnPrev;
    ImageView CollegeLogo,AdminImg;
    private int PICK_IMAGE_REQUEST_ADMIN = 1;
    private int PICK_IMAGE_REQUEST_LOGO = 2;
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private Bitmap bitmapAdmin,bitmapLogo;
    private String Url = Constants.WEB_API_URL+"AdminRegistration.php";

    String Name="";
    String Email="";
    String Password="";
    String MobaleNo="";
    String Date="";
    String Gender="";
    String CollegeName="";
    String CollegeCode="";
    String CollegeState="";
    String CollegeCity="";
    SpotsDialog alertDialog;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reg_img);

        final Intent intent = getIntent();
        Name = intent.getStringExtra("Name");
        Email =intent.getStringExtra("Email");
        Password =intent.getStringExtra("Password");
        MobaleNo =intent.getStringExtra("MobaileNo");
        Date =intent.getStringExtra("Date");
        Gender =intent.getStringExtra("Gender");
        CollegeName =intent.getStringExtra("CollegeName");
        CollegeCode =intent.getStringExtra("CollegeCode");
        CollegeState =intent.getStringExtra("CollegeState");
        CollegeCity =intent.getStringExtra("CollegeCity");

        AdminImgBtn = findViewById(R.id.AdminImgBtn);
        CollegeLogoBtn = findViewById(R.id.CollegeLogoBtn);
        RegisterAdmin = findViewById(R.id.RegisterAdmin);

        AdminImg = findViewById(R.id.AdminImg);
        CollegeLogo = findViewById(R.id.CollegeLogo);

        BtnPrev = findViewById(R.id.btn_reg_img_prev);
        alertDialog  = new SpotsDialog(this);
        alertDialog.create();
        AdminImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser(PICK_IMAGE_REQUEST_ADMIN);
            }
        });

         CollegeLogoBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 showFileChooser(PICK_IMAGE_REQUEST_LOGO);
             }
         });

         RegisterAdmin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (bitmapAdmin == null||bitmapLogo==null) {
                     Toast.makeText(AdminRegImgActivity.this, "Please Upload Image", Toast.LENGTH_SHORT).show();
                 } else {


                     upload();
                     alertDialog.show();

                 }
             }
         });

         BtnPrev.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent1 = new Intent(AdminRegImgActivity.this,AdminRegCollegeActivity.class);
                 intent1.putExtra("status","prev");
                 intent1.putExtra("Name",Name);
                 intent1.putExtra("Email",Email);
                 intent1.putExtra("Password",Password);
                 intent1.putExtra("MobaileNo",MobaleNo);
                 intent1.putExtra("Date",Date);
                 intent1.putExtra("Gender",Gender);
                 intent1.putExtra("CollegeName",CollegeName);
                 intent1.putExtra("CollegeCode",CollegeCode);
                 intent1.putExtra("CollegeState",CollegeState);
                 intent1.putExtra("CollegeCity",CollegeCity);
                 startActivity(intent1);
             }
         });

    }

    private void showFileChooser(int i) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_ADMIN && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                AdminImg.setVisibility(View.VISIBLE);
                bitmapAdmin = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                AdminImg.setImageBitmap(bitmapAdmin);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST_LOGO && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                CollegeLogo.setVisibility(View.VISIBLE);
                bitmapLogo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                CollegeLogo.setImageBitmap(bitmapLogo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private void upload() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        alertDialog.dismiss();

                        try {
                            JSONObject user_detail = new JSONObject(s);
                            if(user_detail.getBoolean("error"))
                            {

                                Toast.makeText(AdminRegImgActivity.this, user_detail.getString("message"), Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                SharedpreferenceHelper sharedPreferenceHelper = SharedpreferenceHelper.getInstance(AdminRegImgActivity.this);
                                sharedPreferenceHelper.userlogin(user_detail.getString("name"),user_detail.getString("email")
                                        ,user_detail.getString("collegecode")
                                        ,user_detail.getString("mobileno")
                                        ,user_detail.getString("dob")
                                        ,user_detail.getString("gender")
                                        ,user_detail.getString("type")
                                );

                                sharedPreferenceHelper.adminUser(user_detail.getString("profilephoto"),
                                        user_detail.getString("collegelogo"),
                                        user_detail.getString("collegename"),
                                        user_detail.getString("collegecity"),
                                        user_detail.getString("collegestate"));

                                Toast.makeText(AdminRegImgActivity.this, "You Registered Succeccfuly", Toast.LENGTH_LONG).show();


                                Intent intent = new Intent(AdminRegImgActivity.this,FacultyDashboard.class);
                                startActivity(intent);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                       alertDialog.dismiss();

                        //Showing toast

                        }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String imageAdmin = getStringImage(bitmapAdmin);
                String imageLogo = getStringImage(bitmapLogo);



                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                Map<String,String> map = new HashMap<>();

                map.put("Email",Email.toLowerCase());
                map.put("Password",Password);
                map.put("CollegeCode",CollegeCode);
                map.put("Name",Name);
                map.put("MobileNo",MobaleNo);
                map.put("Dob",Date);
                map.put("Gender",Gender);
                map.put("CollegeName",CollegeName);
                map.put("CollegeCity",CollegeCity);
                map.put("CollegeState",CollegeState);
                map.put("CollegeLogo",imageLogo);
                map.put("AdminPhoto",imageAdmin);

                return  map;

                //returning parameters

            }
        };




        //Adding request to the queue

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
