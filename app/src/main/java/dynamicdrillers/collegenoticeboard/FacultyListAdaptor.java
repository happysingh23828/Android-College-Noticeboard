package dynamicdrillers.collegenoticeboard;

import android.annotation.SuppressLint;
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

public class FacultyListAdaptor extends RecyclerView.Adapter<FacultyListAdaptor.FacultyListViewHolder> {


    public FacultyListAdaptor(List<Faculty> facultyList) {

        this.facultyList = facultyList;

    }

    List<Faculty> facultyList;
    SpotsDialog spotsDialog;


    @Override
    public FacultyListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.singlehodshow, parent, false);
        return new FacultyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FacultyListViewHolder holder, final int position) {

        spotsDialog = new SpotsDialog(holder.itemView.getContext());
        SharedpreferenceHelper sharedpreferenceHelper = SharedpreferenceHelper.getInstance(holder.itemView.getContext());
        final Faculty faculty = facultyList.get(position);

        holder.facultyName.setText(faculty.getName());
        if (sharedpreferenceHelper.getType().equals("admin"))
            holder.facultyEmail.setText("Role : "+faculty.getRole().toUpperCase());
        else
            holder.facultyEmail.setText(faculty.getEmail());

        Picasso.with(holder.itemView.getContext()).load(Constants.PERSON_PROFILE_STORAGE_URL + "Person" + faculty.getEmail() + ".png")
                .into(holder.facultyimage);


        holder.deleteFaculty.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
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

                        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.WEB_API_URL + "HodDeleteFaculty.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        spotsDialog.dismiss();
                                        dialog.dismiss();
                                        try {

                                            JSONObject jsonObject = new JSONObject(response);

                                            Toast.makeText(holder.itemView.getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                            facultyList.remove(position);
                                            FacultyListAdaptor.this.notifyItemRemoved(position);
                                            FacultyListAdaptor.this.notifyItemRangeChanged(position, facultyList.size());
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
                                Toast.makeText(holder.itemView.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }
                        }) {

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> param = new HashMap<>();
                                param.put("Email", faculty.getEmail());
                                return param;
                            }
                        };

                        MySingleton.getInstance(holder.itemView.getContext()).addToRequestQueue(stringRequest);

                    }
                });
                dialog.show();


            }
        });


        holder.editFaculty.setOnClickListener(new View.OnClickListener() {
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



                Button delete = (Button) dialog.findViewById(R.id.dialog_btn_chnge);
                // if button is clicked, close the custom dialog
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spotsDialog.show();

                        if (newpassword.getText().length() > 1) {
                            if (newpassword.getText().toString().equals(confirmpassword.getText().toString())) {
                                StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.WEB_API_URL + "ChangePassword.php",
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                dialog.dismiss();
                                                spotsDialog.dismiss();
                                                try {

                                                    JSONObject jsonObject = new JSONObject(response);

                                                    Toast.makeText(holder.itemView.getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();


                                                } catch (JSONException e) {

                                                    e.printStackTrace();
                                                }

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        spotsDialog.dismiss();
                                        Toast.makeText(holder.itemView.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();

                                    }
                                }) {

                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> param = new HashMap<>();
                                        param.put("Email", faculty.getEmail());
                                        param.put("NewPassword", confirmpassword.getText().toString());
                                        param.put("PersonType", "other");
                                        return param;
                                    }
                                };

                                MySingleton.getInstance(holder.itemView.getContext()).addToRequestQueue(stringRequest);
                            } else {
                                dialog.dismiss();
                                spotsDialog.dismiss();
                                Toast.makeText(holder.itemView.getContext(), "Password Does Not Matched", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            dialog.dismiss();
                            spotsDialog.dismiss();
                            Toast.makeText(holder.itemView.getContext(), "Enter Password", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
                dialog.show();



            }
        });


    }

    @Override
    public int getItemCount() {
        return facultyList.size();
    }

    public class FacultyListViewHolder extends RecyclerView.ViewHolder {

        CircleImageView facultyimage;
        TextView facultyName, facultyEmail;
        Button editFaculty, deleteFaculty;


        public FacultyListViewHolder(View itemView) {
            super(itemView);

            facultyimage = (CircleImageView) itemView.findViewById(R.id.hodphoto);
            facultyName = (TextView) itemView.findViewById(R.id.hodname);
            facultyEmail = (TextView) itemView.findViewById(R.id.hoddept);
            editFaculty = (Button) itemView.findViewById(R.id.hodeditprofile);
            deleteFaculty = (Button) itemView.findViewById(R.id.hoddeleteprofile);
        }
    }
}
