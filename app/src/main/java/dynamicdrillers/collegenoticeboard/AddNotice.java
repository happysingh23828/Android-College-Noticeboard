package dynamicdrillers.collegenoticeboard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
    String noticetype = getIntent().getStringExtra("noticetype");
    String Role = sharedpreferenceHelper.getRole();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        title = (TextInputLayout)findViewById(R.id.NoticeTitle);
        desc = (TextInputLayout)findViewById(R.id.NoticeDesc);
        uploadflag = (CheckBox)findViewById(R.id.checknoticecheckbox);
        uploaded_image = (ImageView)findViewById(R.id.choosedimage);
        chooseImage = (Button)findViewById(R.id.imagechoosenotice);
        sendnotice = (Button)findViewById(R.id.sendnoticebtn);

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

                SendNotice();
            }
        });



    }

    private void SendNotice() {






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
