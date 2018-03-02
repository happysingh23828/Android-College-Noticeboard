package dynamicdrillers.collegenoticeboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

/**
 * Created by Happy-Singh on 2/28/2018.
 */

public class HodListAdaptor extends RecyclerView.Adapter<HodListAdaptor.HodlIstViewHolder>{

    List<Hod> hodlist;

    public HodListAdaptor(List<Hod> hodlist) {
        this.hodlist = hodlist;
    }

    @Override
    public HodlIstViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.singlehodshow,parent,false);
        return new HodlIstViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HodlIstViewHolder holder, int position) {

        final Hod hod = hodlist.get(position);

        holder.HodName.setText(hod.getName());
        holder.HodDept.setText("Dept : "+hod.getDept());
        Picasso.with(holder.itemView.getContext()).load(Constants.HOD_PROFILE_STORAGE_URL+"Hod"+hod.getEmail()+".png").into(holder.hodimage);

        holder.editHod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());

                View view =  View.inflate(holder.itemView.getContext(),R.layout.changepassworddialog,null);
                final TextInputEditText newpassword = (TextInputEditText)view.findViewById(R.id.newpassword);
                final TextInputEditText confirmpassword = (TextInputEditText)view.findViewById(R.id.confirmpassword);

                builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(newpassword.getText().length()>1)
                        {
                            if(newpassword.getText().toString().equals(confirmpassword.getText().toString()))
                            {
                                StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.WEB_API_URL + "ChangePassword.php",
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                try {

                                                    JSONObject jsonObject = new JSONObject(response);

                                                    Toast.makeText(holder.itemView.getContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();


                                                } catch (JSONException e) {

                                                    e.printStackTrace();
                                                }

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(holder.itemView.getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();


                                    }
                                }){

                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String,String> param = new HashMap<>();
                                        param.put("Email",hod.getEmail());
                                        param.put("NewPassword",confirmpassword.getText().toString());
                                        param.put("PersonType","hod");
                                        return param;
                                    }
                                };

                                MySingleton.getInstance(holder.itemView.getContext()).addToRequestQueue(stringRequest);
                            }
                            else
                                Toast.makeText(holder.itemView.getContext(),"Password Doen Not Matched",Toast.LENGTH_SHORT).show();
                        }

                        else
                            Toast.makeText(holder.itemView.getContext(),"Enter Password",Toast.LENGTH_SHORT).show();

                    }
                });


                builder.setView(view);
                builder.create();
                builder.show();
            }
        });



        holder.deleteHod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("All The Data Of User Will Be Lost?  ");

                alert.setPositiveButton("Delete Anyway", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.WEB_API_URL + "AdminDeleteHod.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {

                                            JSONObject jsonObject = new JSONObject(response);

                                            Toast.makeText(holder.itemView.getContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();

                                        } catch (JSONException e) {

                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(holder.itemView.getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();


                            }
                        }){

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> param = new HashMap<>();
                                param.put("Email",hod.getEmail());
                                return param;
                            }
                        };

                        MySingleton.getInstance(holder.itemView.getContext()).addToRequestQueue(stringRequest);

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                    }
                });

                alert.create();
                alert.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return hodlist.size();
    }

    public  class HodlIstViewHolder extends  RecyclerView.ViewHolder{

        CircleImageView hodimage;
        TextView HodName,HodDept;
        Button editHod,deleteHod;

        public HodlIstViewHolder(View itemView) {
            super(itemView);

            hodimage = (CircleImageView)itemView.findViewById(R.id.hodphoto);
            HodName = (TextView)itemView.findViewById(R.id.hodname);
            HodDept = (TextView)itemView.findViewById(R.id.hoddept);
            editHod = (Button)itemView.findViewById(R.id.hodeditprofile);
            deleteHod = (Button)itemView.findViewById(R.id.hoddeleteprofile);
        }


    }
}
