package dynamicdrillers.collegenoticeboard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.squareup.picasso.Picasso;

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

    SharedpreferenceHelper sharedPreferenceHelper = SharedpreferenceHelper.getInstance(getBaseContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dashboard);

        ProfileIcon = (ImageView) findViewById(R.id.profileIcon);
        ProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacultyDashboard.this, ProfileActivity.class));

            }
        });


        button = findViewById(R.id.a);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacultyDashboard.this, StudentRegistrationActivity.class));
            }
        });

        button = findViewById(R.id.b);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacultyDashboard.this, StudentProfileActivity.class));
            }
        });


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


            menu.findItem(R.id.Faculties).setVisible(false);
            menu.findItem(R.id.dept_notices).setVisible(false);
            menu.findItem(R.id.Students).setVisible(false);
            menu.findItem(R.id.your_notices).setVisible(false);
        } else {
            Picasso.with(getBaseContext()).load(Constants.HOD_PROFILE_STORAGE_URL + sharedPreferenceHelper.getHodProfileName())
                    .into(NavigationProfileImage);
            NavigationText2.setText("Email :" + sharedPreferenceHelper.getEmail());
            NavigationText3.setText("dept :" + sharedPreferenceHelper.getDept());

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
                        startActivity(new Intent(getApplicationContext(),EditProfile.class));
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

                    case R.id.HeadOfDept:
                        drawerLayout.closeDrawer(Gravity.START);
                        startActivity(new Intent(FacultyDashboard.this, HodList.class));
                        break;

                    case R.id.your_notices:
                        drawerLayout.closeDrawer(Gravity.START);
                        startActivity(new Intent(getApplicationContext(),YourNotices.class));
                        break;

                    case R.id.dept_notices:
                        drawerLayout.closeDrawer(Gravity.START);
                        startActivity(new Intent(getApplicationContext(),YourNotices.class));
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

        String noticenames1[] = {"College", "Scholarship", "Events", "Tg"};
        String noticenames2[] = {"Accounts", "Tnp", "Dept", "Tg"};
        int noticeicons1[] = {R.drawable.collegenotice, R.drawable.schlorship, R.drawable.events, R.drawable.tg};
        int noticeicons2[] = {R.drawable.viewnotice, R.drawable.tnp, R.drawable.dept, R.drawable.tg};
        recyclerView = (RecyclerView) findViewById(R.id.notice_recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DashboardNoticeAdaptor(noticenames1, noticenames2, noticeicons1, noticeicons2));


    }
}
