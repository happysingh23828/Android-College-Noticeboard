package dynamicdrillers.collegenoticeboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class AddNotice extends AppCompatActivity {
    private int PICK_IMAGE_REQUEST_NOTICE = 1;
    TextInputLayout textInputLayout;
    TextInputLayout title,desc;
    CheckBox uploadflag;
    ImageView uploaded_image;
    Button chooseImage,sendnotice;
    Bitmap bitmap;
    SharedpreferenceHelper sharedpreferenceHelper = SharedpreferenceHelper.getInstance(this);
    String persontype = sharedpreferenceHelper.getType();
    String noticetype;
    String Role = sharedpreferenceHelper.getRole();
    Toolbar toolbar;
    TextView notice_name;
    SpotsDialog spotsDialog;
    String tgnoticeflag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        tgnoticeflag = getIntent().getStringExtra("istgnotice");
        String noticetypeflag = getIntent().getStringExtra("noticetype");

        spotsDialog = new SpotsDialog(this);

        title = (TextInputLayout)findViewById(R.id.NoticeTitle);
        desc = (TextInputLayout)findViewById(R.id.NoticeDesc);
        uploadflag = (CheckBox)findViewById(R.id.checknoticecheckbox);
        uploaded_image = (ImageView)findViewById(R.id.choosedimage);
        chooseImage = (Button)findViewById(R.id.imagechoosenotice);
        sendnotice = (Button)findViewById(R.id.sendnoticebtn);


        if(persontype.equals("hod") && noticetypeflag.equals("dept"))
        {
            noticetype = "dept";
        }
        else if(persontype.equals("hod") && noticetypeflag.equals("events"))
        {
            noticetype = "events";
        }
        else if(persontype.equals("other") && noticetypeflag.equals("dept"))
        {
            noticetype = "dept";
        }
        else if(persontype.equals("other") && noticetypeflag.equals("events"))
        {
            noticetype = "events";
        }
        else if(persontype.equals("other") && sharedpreferenceHelper.getTgflag()==1 && tgnoticeflag.equals("yes"))
        {
            noticetype="tg";
        }
        else if(persontype.equals("admin"))
        {
            noticetype="college";
        }
        else
        {
            noticetype = Role;
        }

        notice_name = (TextView)findViewById(R.id.notice_name);
        notice_name.setText("Send "+ noticetype.toUpperCase() + " Notice");
        toolbar = (Toolbar)findViewById(R.id.noticeaddtoolbar);
        setSupportActionBar(toolbar);


        uploadflag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked())
                {
                   chooseImage.setVisibility(View.VISIBLE);
                   uploaded_image.setVisibility(View.VISIBLE);


                }
                else
                {
                    chooseImage.setVisibility(View.GONE);
                    uploaded_image.setVisibility(View.GONE);

                }
            }
        });

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooser(PICK_IMAGE_REQUEST_NOTICE);
            }
        });


        sendnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    SendNotice();
                    spotsDialog.show();
                }

            }
        });



    }



    private boolean validate() {

        boolean status = true;

        Validation validation = new Validation();

        if(!validation.titleValidation(title))
            status = false;


        if(!validation.disValidation(desc))
            status = false;

        return status;
    }



    private void SendNotice() {

        final ProgressDialog progressDialog = new ProgressDialog(AddNotice.this);

        String WEB_URL;

        if(noticetype.equals("dept"))
        {
            WEB_URL = Constants.WEB_API_URL+"FacultyCreateDeptNotice.php";
        }
        else if(noticetype.equals("tg"))
        {
            WEB_URL = Constants.WEB_API_URL+"FacultyCreateTgNotice.php";
        }
        else
        {
            WEB_URL = Constants.WEB_API_URL+"FacultyCreateNotice.php";
        }

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, WEB_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                spotsDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if(tgnoticeflag.equals("yes"))
                    {
                        sendNotification("TG",sharedpreferenceHelper.getEmail(),sharedpreferenceHelper.getCollegeCode(),sharedpreferenceHelper.getName());

                    }
                    else if(noticetype.equals("dept"))
                    {
                        sendNotification("Dept",sharedpreferenceHelper.getDept(),sharedpreferenceHelper.getCollegeCode(),sharedpreferenceHelper.getName());
                    }
                    else
                    {
                        if(sharedpreferenceHelper.getRole()==null)
                        {
                            sendNotification(noticetype.toUpperCase(),sharedpreferenceHelper.getDept(),sharedpreferenceHelper.getCollegeCode(),sharedpreferenceHelper.getName());
                        }
                        else
                        {
                            sendNotification(noticetype.toUpperCase(),sharedpreferenceHelper.getRole().toUpperCase(),sharedpreferenceHelper.getCollegeCode(),sharedpreferenceHelper.getName());

                        }

                    }

                    Toast.makeText(getBaseContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),FacultyDashboard.class));
                    finish();

                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(),"Oops.. Please Try Again",Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spotsDialog.dismiss();
                Toast.makeText(getBaseContext(),"Some Network Issues",Toast.LENGTH_LONG).show();


            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();

                DateFormat dateFormat = new SimpleDateFormat("yyyy/dd/MM HH:mm:ss");
                Date date = new Date();
                String Current_time = dateFormat.format(date);


                param.put("CollegeCode",sharedpreferenceHelper.getCollegeCode());
                param.put("AuthorEmail",sharedpreferenceHelper.getEmail());
                param.put("AuthorName",sharedpreferenceHelper.getName());
                param.put("Time",Current_time);
                param.put("Title",title.getEditText().getText().toString());
                param.put("String",desc.getEditText().getText().toString());


                if(uploadflag.isChecked() && bitmap!=null)
                {
                    String Base64image = getStringImage(bitmap);
                    param.put("Image",Base64image);
                }
                else
                  param.put("Image","noimage");

                if(noticetype.equals("dept"))
                {
                    param.put("Dept",sharedpreferenceHelper.getDept());
                }
                else if(noticetype.equals("tg"))
                {
                    param.put("Dept",sharedpreferenceHelper.getDept());
                    param.put("Sem",sharedpreferenceHelper.getTgSem());
                }
                else
                {
                    param.put("Type",noticetype);
                }

                        return param;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);



    }

    private void sendNotification(final String noticetype, final String data, final String collegeCode, final String name) {
         StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.WEB_API_URL + "SendNotificationByOneSignal.php",
                 new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {

                     }
                 }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {

             }
         }){
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
               Map<String,String> map = new HashMap<>();
               map.put("CollegeCode",collegeCode);
               map.put("Type",noticetype);
               map.put("Data",data);
               map.put("Name",name);

               return map;
             }
         };

         MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_NOTICE && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                uploaded_image.setVisibility(View.VISIBLE);
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                uploaded_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void showFileChooser(int i) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), i);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


}
