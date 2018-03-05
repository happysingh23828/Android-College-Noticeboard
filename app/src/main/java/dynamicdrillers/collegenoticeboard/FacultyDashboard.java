package dynamicdrillers.collegenoticeboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class FacultyDashboard extends AppCompatActivity {

    Button button;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navigationicon;
    RecyclerView recyclerView;
    Menu menu;
    ImageView ProfileIcon, NavigationProfileImage, CollegeLogo;
    TextView NavigationText1, NavigationText2, NavigationText3, CollegeName, CollegeAddress;
    SpotsDialog spotsDialog;



    SharedpreferenceHelper sharedPreferenceHelper = SharedpreferenceHelper.getInstance(getBaseContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dashboard);

        String SharedprefenceName = "USER_DATA";
        SharedPreferences sharedPreference = FacultyDashboard.this.getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);



        ProfileIcon = (ImageView) findViewById(R.id.profileIcon);
        ProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacultyDashboard.this, StudentProfileActivity.class));

            }
        });



        spotsDialog = new SpotsDialog(this);

        //Setting college Details In Main toolbar
        CollegeLogo = (ImageView) findViewById(R.id.toolbar_college_logo);
        CollegeName = (TextView) findViewById(R.id.toolbar_college_name);
        CollegeAddress = (TextView) findViewById(R.id.toolbar_college_address);

        Picasso.with(getBaseContext()).load(Constants.COLLEGE_LOGO_STORAGE_URL + sharedPreferenceHelper.getCollegeLogoName()).into(CollegeLogo);
        CollegeAddress.setText(sharedPreferenceHelper.getCollegeAddress());
        CollegeName.setText(sharedPreferenceHelper.getCollegeAddress());


        //Setting Toolbar
        toolbar = (Toolbar) findViewById(R.id.facultyMainToolbar);
        setSupportActionBar(toolbar);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerfacultyroot);
        navigationicon = (ImageView) findViewById(R.id.navigationicon);

        // When Navigation Icon Clicks
        navigationicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });


        navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        View Header = navigationView.getHeaderView(0);
        NavigationProfileImage = (ImageView) Header.findViewById(R.id.navigation_profile_photo);
        NavigationText1 = (TextView) Header.findViewById(R.id.navitext1);
        NavigationText2 = (TextView) Header.findViewById(R.id.navitext2);
        NavigationText3 = (TextView) Header.findViewById(R.id.navitext3);

        NavigationText1.setText(sharedPreferenceHelper.getName());
        menu = navigationView.getMenu();

        if (sharedPreferenceHelper.getType().equals("other")) {
            Picasso.with(getBaseContext()).load(Constants.PERSON_PROFILE_STORAGE_URL + sharedPreferenceHelper.getPersonProfileName())
                    .into(NavigationProfileImage);

            NavigationText2.setText("DEPT : " + sharedPreferenceHelper.getDept());
            NavigationText3.setText("ROLE : " + sharedPreferenceHelper.getRole());

            if(!sharedPreferenceHelper.getRole().equals("Faculty"))
            {
                menu.findItem(R.id.dept_notices).setVisible(false);
                menu.findItem(R.id.notfacultynotices).setTitle(sharedPreferenceHelper.getRole().toUpperCase()+" Notices");
                menu.findItem(R.id.notfacultynotices).setVisible(true);

            }
            menu.findItem(R.id.college_notices).setVisible(false);
            menu.findItem(R.id.HeadOfDept).setVisible(false);
            menu.findItem(R.id.Faculties).setVisible(false);
            if (sharedPreferenceHelper.getTgflag() == 0) {
                menu.findItem(R.id.Students).setVisible(false);
                menu.findItem(R.id.your_notices).setVisible(false);
            }

        } else if (sharedPreferenceHelper.getType().equals("student")) {

            Picasso.with(getBaseContext()).load(Constants.STUDENT_PROFILE_STORAGE_URL + sharedPreferenceHelper.getStudentProfileName())
                    .into(NavigationProfileImage);
            NavigationText2.setText("DEPT : " + sharedPreferenceHelper.getDept());
            NavigationText3.setText("SEM : " + sharedPreferenceHelper.getSem());

            menu.findItem(R.id.college_notices).setVisible(false);
            menu.findItem(R.id.events_notice).setVisible(false);
            menu.findItem(R.id.dept_notices).setVisible(false);
            menu.findItem(R.id.HeadOfDept).setVisible(false);
            menu.findItem(R.id.Students).setVisible(false);
            menu.findItem(R.id.Faculties).setVisible(false);
            menu.findItem(R.id.your_notices).setVisible(false);
        } else if (sharedPreferenceHelper.getType().equals("admin")) {

            Picasso.with(getBaseContext()).load(Constants.ADMIN_PROFILE_STORAGE_URL + sharedPreferenceHelper.getAdminProfileName())
                    .into(NavigationProfileImage);

            NavigationText2.setText("Email :" + sharedPreferenceHelper.getEmail());
            NavigationText3.setText("CollegeCode :" + sharedPreferenceHelper.getCollegeCode());

            menu.findItem(R.id.events_notice).setVisible(false);
            menu.findItem(R.id.dept_notices).setVisible(false);
            menu.findItem(R.id.Students).setVisible(false);
            menu.findItem(R.id.your_notices).setVisible(false);
        } else {

            Picasso.with(getBaseContext()).load(Constants.HOD_PROFILE_STORAGE_URL + sharedPreferenceHelper.getHodProfileName())
                    .into(NavigationProfileImage);
            NavigationText2.setText("Email :" + sharedPreferenceHelper.getEmail());
            NavigationText3.setText("dept :" + sharedPreferenceHelper.getDept());

            menu.findItem(R.id.college_notices).setVisible(false);
            menu.findItem(R.id.HeadOfDept).setVisible(false);
            menu.findItem(R.id.Students).setVisible(false);
            menu.findItem(R.id.your_notices).setVisible(false);


        }


        //checking Which Navigationitem Selected
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.editProfile:
                        drawerLayout.closeDrawer(Gravity.START);
                        startActivity(new Intent(getApplicationContext(),StudentProfileActivity.class));
                        break;

                    case R.id.ProfileLogout:

                        sharedPreferenceHelper.logout();
                        Toast.makeText(getBaseContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.Students:
                        drawerLayout.closeDrawer(Gravity.START);
                        startActivity(new Intent(getApplicationContext(),StudentList.class));
                        break;

                    case R.id.college_notices:
                        drawerLayout.closeDrawer(Gravity.START);
                        Intent l = new Intent(getApplicationContext(),YourNotices.class);
                        l.putExtra("istgnotice","no");
                        l.putExtra("noticetype","college");
                        startActivity(l);
                        break;


                    case R.id.HeadOfDept:
                        drawerLayout.closeDrawer(Gravity.START);
                        Intent n = new Intent(getApplicationContext(),HodList.class);
                        startActivity(n);
                        break;

                    case R.id.your_notices:
                        drawerLayout.closeDrawer(Gravity.START);
                        Intent i = new Intent(getApplicationContext(),YourNotices.class);
                        i.putExtra("istgnotice","yes");
                        i.putExtra("noticetype","no");
                        startActivity(i);
                        break;

                    case R.id.notfacultynotices:
                        drawerLayout.closeDrawer(Gravity.START);
                        Intent p = new Intent(getApplicationContext(),YourNotices.class);
                        p.putExtra("istgnotice","no");
                        p.putExtra("noticetype",sharedPreferenceHelper.getRole());
                        startActivity(p); break;

                    case R.id.dept_notices:
                        drawerLayout.closeDrawer(Gravity.START);
                        Intent j = new Intent(getApplicationContext(),YourNotices.class);
                        j.putExtra("istgnotice","no");
                        j.putExtra("noticetype","dept");
                        startActivity(j);
                        break;

                    case R.id.events_notice:
                        drawerLayout.closeDrawer(Gravity.START);
                        Intent k = new Intent(getApplicationContext(),YourNotices.class);
                        k.putExtra("istgnotice","no");
                        k.putExtra("noticetype","events");
                        startActivity(k);
                        break;

                    case R.id.Faculties:
                        drawerLayout.closeDrawer(Gravity.START);
                        startActivity(new Intent(FacultyDashboard.this, FacultyList.class));
                        break;


                    case R.id.feedback:
                        drawerLayout.closeDrawer(Gravity.START);
                        startActivity(new Intent(getApplicationContext(),FeedBack.class));
                        break;

                    case R.id.aboutus:
                        drawerLayout.closeDrawer(Gravity.START);
                        startActivity(new Intent(getApplicationContext(),AboutApp.class));
                        break;

                    case R.id.sharethisapp:
                        drawerLayout.closeDrawer(Gravity.START);
                        Toast.makeText(getBaseContext(), "Share Clicked", Toast.LENGTH_SHORT).show();
                        break;


                }

                return true;
            }
        });

        String noticenames1[] = {"college", "scholarship", "events", "tg"};
        String noticenames2[] = {"accounts", "tnp", "dept", "tg"};
        int noticeicons1[] = {R.drawable.collegenotice, R.drawable.schlorship, R.drawable.events, R.drawable.tg};
        int noticeicons2[] = {R.drawable.viewnotice, R.drawable.tnp, R.drawable.dept, R.drawable.tg};
        recyclerView = (RecyclerView) findViewById(R.id.notice_recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DashboardNoticeAdaptor(noticenames1, noticenames2, noticeicons1, noticeicons2));

        sendTokenToserver();

    }
    public void sendTokenToserver() {

        final SharedpreferenceHelper sharedpreferenceHelper = SharedpreferenceHelper.getInstance(this);
        final String token = FirebaseInstanceId.getInstance().getToken();

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.WEB_API_URL + "SendToken.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("Email",sharedpreferenceHelper.getEmail());
                param.put("Type",sharedpreferenceHelper.getType());
                param.put("Token",token);
                return param;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    @Override
    protected void onResume() {
        super.onResume();



        SharedpreferenceHelper sharedpreferenceHelper = SharedpreferenceHelper.getInstance(this);
        if (sharedpreferenceHelper.getType().equals("other")) {

            Picasso.with(getBaseContext()).invalidate(Constants.PERSON_PROFILE_STORAGE_URL + sharedpreferenceHelper.getPersonProfileName());
            Picasso.with(getBaseContext()).load(Constants.PERSON_PROFILE_STORAGE_URL + sharedpreferenceHelper.getPersonProfileName()).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(NavigationProfileImage);


        }
        else if(sharedpreferenceHelper.getType().equals("student")) {

            Picasso.with(getBaseContext()).invalidate(Constants.STUDENT_PROFILE_STORAGE_URL + sharedpreferenceHelper.getPersonProfileName());
            Picasso.with(getBaseContext()).load(Constants.STUDENT_PROFILE_STORAGE_URL + sharedpreferenceHelper.getPersonProfileName()).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(NavigationProfileImage);

        }
        else if (sharedpreferenceHelper.getType().equals("admin")) {
            Picasso.with(getBaseContext()).invalidate(Constants.ADMIN_PROFILE_STORAGE_URL + sharedpreferenceHelper.getPersonProfileName());
            Picasso.with(getBaseContext()).load(Constants.ADMIN_PROFILE_STORAGE_URL + sharedpreferenceHelper.getPersonProfileName()).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(NavigationProfileImage);



        }
        else if (sharedpreferenceHelper.getType().equals("hod")){

            Picasso.with(getBaseContext()).invalidate(Constants.HOD_PROFILE_STORAGE_URL + sharedpreferenceHelper.getPersonProfileName());
            Picasso.with(getBaseContext()).load(Constants.HOD_PROFILE_STORAGE_URL + sharedpreferenceHelper.getPersonProfileName()).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(NavigationProfileImage);



        }

    }

}
