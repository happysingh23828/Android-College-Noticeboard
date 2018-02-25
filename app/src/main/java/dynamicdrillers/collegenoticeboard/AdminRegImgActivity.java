package dynamicdrillers.collegenoticeboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class AdminRegImgActivity extends AppCompatActivity {

    Button AdminImgBtn,CollegeLogoBtn,RegisterAdmin,BtnAdminRegImgPrev;
    ImageView CollegeLogo,AdminImg;
    private int PICK_IMAGE_REQUEST_ADMIN = 1;
    private int PICK_IMAGE_REQUEST_LOGO = 2;
    private Bitmap bitmapAdmin,bitmapLogo;
    private String Url = "http://192.168.1.8/Web-API-College-Noticeboard/WebServicesApi/AdminRegistration.php";

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reg_img);

        Intent intent = getIntent();
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

        Toast.makeText(this,Name+"\n"+Email+"\n"+Password+"\n"+MobaleNo+"\n"+Date+"\n"+Gender,Toast.LENGTH_LONG).show();


        AdminImgBtn = findViewById(R.id.AdminImgBtn);
        CollegeLogoBtn = findViewById(R.id.CollegeLogoBtn);
        RegisterAdmin = findViewById(R.id.RegisterAdmin);

        AdminImg = findViewById(R.id.AdminImg);
        CollegeLogo = findViewById(R.id.CollegeLogo);

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

                 }
             }
         });
        BtnAdminRegImgPrev = findViewById(R.id.btn_reg_img_prev);
        BtnAdminRegImgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminRegImgActivity.this,"c ndsm c",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AdminRegImgActivity.this,AdminRegCollegeActivity.class);

                intent.putExtra("status","prev");
                intent.putExtra("Email",Email);
                intent.putExtra("Password",Name);
                intent.putExtra("CollegeCode",CollegeCode);
                intent.putExtra("Name",Name);
                intent.putExtra("MobileNo",MobaleNo);
                intent.putExtra("Dob",Date);
                intent.putExtra("Gender",Gender);
                intent.putExtra("CollegeName",CollegeName);
                intent.putExtra("CollegeCity",CollegeCity);
                intent.putExtra("CollegeState",CollegeState);

                startActivity(intent);
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
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(AdminRegImgActivity.this, s, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(AdminRegImgActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
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

                map.put("Email",Email);
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
