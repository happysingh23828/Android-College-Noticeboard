package dynamicdrillers.collegenoticeboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Happy-Singh on 2/28/2018.
 */

public class FacultyListAdaptor extends RecyclerView.Adapter<FacultyListAdaptor.FacultyListViewHolder> {
    public FacultyListAdaptor(List<Faculty> facultyList) {
        this.facultyList = facultyList;
    }

    List<Faculty> facultyList;
    @Override
    public FacultyListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.singlehodshow,parent,false);
        return new FacultyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FacultyListViewHolder holder, int position) {

        Faculty faculty = facultyList.get(position);

        holder.facultyName.setText(faculty.getName());
        holder.facultyEmail.setText(faculty.getEmail());
        Picasso.with(holder.itemView.getContext()).load(Constants.PERSON_PROFILE_STORAGE_URL+"Person"+faculty.getEmail()+".png")
                .into(holder.facultyimage);

        holder.editFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Edit Profile Clicked",Toast.LENGTH_LONG).show();
            }
        });

        holder.deleteFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Delete Profile Clicked",Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return facultyList.size();
    }

    public  class FacultyListViewHolder extends  RecyclerView.ViewHolder{

        CircleImageView facultyimage;
        TextView facultyName,facultyEmail;
        Button editFaculty,deleteFaculty;


        public FacultyListViewHolder(View itemView) {
            super(itemView);

            facultyimage = (CircleImageView)itemView.findViewById(R.id.hodphoto);
            facultyName = (TextView)itemView.findViewById(R.id.hodname);
            facultyEmail = (TextView)itemView.findViewById(R.id.hoddept);
            editFaculty = (Button)itemView.findViewById(R.id.hodeditprofile);
            deleteFaculty = (Button)itemView.findViewById(R.id.hoddeleteprofile);
        }
    }
}
