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

public class StudentListAdaptor extends RecyclerView.Adapter<StudentListAdaptor.StudentListViewHolder> {

    List<Student> studentList;

    public StudentListAdaptor(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public StudentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.singlehodshow,parent,false);
        return new StudentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentListViewHolder holder, int position) {

        Student student = studentList.get(position);

        holder.studentName.setText(student.getName());
        holder.studentSem.setText("Sem : "+student.getSem());
        Picasso.with(holder.itemView.getContext()).load(Constants.STUDENT_PROFILE_STORAGE_URL+"Student"+student.getEmail()+".png")
                .into(holder.studentimage);

        holder.editstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Edit Profile Clicked",Toast.LENGTH_LONG).show();
            }
        });

        holder.deleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Delete Profile Clicked",Toast.LENGTH_LONG).show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public  class StudentListViewHolder extends RecyclerView.ViewHolder{

        CircleImageView studentimage;
        TextView studentName,studentSem;
        Button deleteStudent,editstudent;


        public StudentListViewHolder(View itemView) {
            super(itemView);

            studentimage = (CircleImageView)itemView.findViewById(R.id.hodphoto);
            studentName = (TextView)itemView.findViewById(R.id.hodname);
            studentSem = (TextView)itemView.findViewById(R.id.hoddept);
            editstudent = (Button)itemView.findViewById(R.id.hodeditprofile);
            deleteStudent = (Button)itemView.findViewById(R.id.hoddeleteprofile);
        }
    }
}
