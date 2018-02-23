package dynamicdrillers.collegenoticeboard;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class FacultyDashboard extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navigationicon;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dashboard);

    //Setting Toolbar
    toolbar = (Toolbar)findViewById(R.id.facultyMainToolbar);
    setSupportActionBar(toolbar);



    drawerLayout = (DrawerLayout)findViewById(R.id.drawerfacultyroot);
    navigationicon = (ImageView)findViewById(R.id.navigationicon);

    // When Navigation Icon Clicks
        navigationicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

     //checking Which Navigationitem Selected
        navigationView = (NavigationView)findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.editProfile:
                        Toast.makeText(getBaseContext(),"Editprofile Clicked",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.ProfileLogout:
                        Toast.makeText(getBaseContext(),"Logout Clicked",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.feedback:
                        Toast.makeText(getBaseContext(),"Feedback Clicked",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.aboutus:
                        Toast.makeText(getBaseContext(),"Aboutus Clicked",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.sharethisapp:
                        Toast.makeText(getBaseContext(),"Share Clicked",Toast.LENGTH_SHORT).show();
                        break;



                }

                return true;
            }
        });


        recyclerView = (RecyclerView)findViewById(R.id.notice_recylerview);





    }
}
