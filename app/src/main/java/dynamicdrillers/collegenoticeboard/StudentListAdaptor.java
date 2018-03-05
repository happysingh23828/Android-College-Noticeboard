package dynamicdrillers.collegenoticeboard;

import android.app.Dialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

/**
 * Created by Happy-Singh on 2/28/2018.
 */

public class StudentListAdaptor extends RecyclerView.Adapter<StudentListAdaptor.StudentListViewHolder> {

    List<Student> studentList;
    SpotsDialog spotsDialog;

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
    public void onBindViewHolder(final StudentListViewHolder holder, final int position) {

        spotsDialog = new SpotsDialog(holder.itemView.getContext());
        final Student student = studentList.get(position);

        holder.studentName.setText(student.getName());
        holder.studentSem.setText("Sem : "+student.getSem());
        Picasso.with(holder.itemView.getContext()).load(Constants.STUDENT_PROFILE_STORAGE_URL+"Student"+student.getEmail()+".png")
                .into(holder.studentimage);

        holder.editstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.changepassworddialog);
                dialog.setTitle("Do you want to delete ?");



                final TextInputEditText newpassword = (TextInputEditText)dialog.findViewById(R.id.newpassword);
                final TextInputEditText confirmpassword = (TextInputEditText)dialog.findViewById(R.id.confirmpassword);


                Button cencel = (Button) dialog.findViewById(R.id.dialog_btn_cencel);
                // if button is clicked, close the custom dialog
                cencel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });



                Button change = (Button) dialog.findViewById(R.id.dialog_btn_chnge);
                // if button is clicked, close the custom dialog
                change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spotsDialog.show();

                        if(newpassword.getText().length()>1)
                        {
                            if(newpassword.getText().toString().equals(confirmpassword.getText().toString()))
                            {
                                StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.WEB_API_URL + "ChangePassword.php",
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                spotsDialog.dismiss();

                                                try {

                                                    JSONObject jsonObject = new JSONObject(response);

                                                    Toast.makeText(holder.itemView.getContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                                     dialog.dismiss();

                                                } catch (JSONException e) {
                                                    dialog.dismiss();
                                                    e.printStackTrace();
                                                }

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        spotsDialog.dismiss();
                                        dialog.dismiss();
                                        Toast.makeText(holder.itemView.getContext(),"Some Network Issues",Toast.LENGTH_SHORT).show();


                                    }
                                }){

                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String,String> param = new HashMap<>();
                                        param.put("Email",student.getEmail());
                                        param.put("NewPassword",confirmpassword.getText().toString());
                                        param.put("PersonType","student");
                                        return param;
                                    }
                                };

                                MySingleton.getInstance(holder.itemView.getContext()).addToRequestQueue(stringRequest);
                            }
                            else
                            {
                                spotsDialog.dismiss();
                                Toast.makeText(holder.itemView.getContext(),"Password Does Not Matched",Toast.LENGTH_SHORT).show();

                            }
                        }
                        else
                        {
                            dialog.dismiss();
                            spotsDialog.dismiss();
                            Toast.makeText(holder.itemView.getContext(),"Enter Password",Toast.LENGTH_SHORT).show();

                        }

                    }
                });
                dialog.show();


            }
        });



        holder.deleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.custom_delete_dealog_layout);
                dialog.setTitle("Do you want to delete ?");





                Button cencel = (Button) dialog.findViewById(R.id.dialog_btn_cencel);
                // if button is clicked, close the custom dialog
                cencel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });



                Button delete = (Button) dialog.findViewById(R.id.dialog_btn_delete);
                // if button is clicked, close the custom dialog
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spotsDialog.show();

                        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.WEB_API_URL + "FacultyDeleteStudent.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        spotsDialog.dismiss();

                                        try {

                                            JSONObject jsonObject = new JSONObject(response);

                                            Toast.makeText(holder.itemView.getContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                            studentList.remove(position);
                                            StudentListAdaptor.this.notifyItemRemoved(position);
                                            StudentListAdaptor.this.notifyItemRangeChanged(position,studentList.size());
                                            dialog.dismiss();

                                        } catch (JSONException e) {
                                            dialog.dismiss();
                                            e.printStackTrace();
                                        }


                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                spotsDialog.dismiss();
                                Toast.makeText(holder.itemView.getContext(),"Some Network Issuses",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }
                        }){

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> param = new HashMap<>();
                                param.put("Email",student.getEmail());
                                return param;
                            }
                        };

                        MySingleton.getInstance(holder.itemView.getContext()).addToRequestQueue(stringRequest);

                    }
                });
                dialog.show();





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
