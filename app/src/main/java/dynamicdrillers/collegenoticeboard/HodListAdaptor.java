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

public class HodListAdaptor extends RecyclerView.Adapter<HodListAdaptor.HodlIstViewHolder> {

    List<Hod> hodlist;
    SpotsDialog spotsDialog;

    public HodListAdaptor(List<Hod> hodlist) {
        this.hodlist = hodlist;
    }

    @Override
    public HodlIstViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.singlehodshow, parent, false);
        return new HodlIstViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HodlIstViewHolder holder, final int position) {

        spotsDialog = new SpotsDialog(holder.itemView.getContext());
        final Hod hod = hodlist.get(position);

        holder.HodName.setText(hod.getName());
        holder.HodDept.setText("Dept : " + hod.getDept());
        Picasso.with(holder.itemView.getContext()).load(Constants.HOD_PROFILE_STORAGE_URL + "Hod" + hod.getEmail() + ".png").into(holder.hodimage);

        holder.editHod.setOnClickListener(new View.OnClickListener() {
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
                        if (newpassword.getText().length() > 1) {
                            if (newpassword.getText().toString().equals(confirmpassword.getText().toString())) {
                                StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.WEB_API_URL + "ChangePassword.php",
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {


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

                                        Toast.makeText(holder.itemView.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();


                                    }
                                }) {

                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> param = new HashMap<>();
                                        param.put("Email", hod.getEmail());
                                        param.put("NewPassword", confirmpassword.getText().toString());
                                        param.put("PersonType", "hod");
                                        return param;
                                    }
                                };

                                MySingleton.getInstance(holder.itemView.getContext()).addToRequestQueue(stringRequest);
                            } else {
                                spotsDialog.dismiss();
                                Toast.makeText(holder.itemView.getContext(), "Password Does Not Matched", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            spotsDialog.dismiss();
                            Toast.makeText(holder.itemView.getContext(), "Enter Password", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                dialog.show();

            }
        });


        holder.deleteHod.setOnClickListener(new View.OnClickListener() {
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
                        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.WEB_API_URL + "AdminDeleteHod.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {


                                        try {

                                            JSONObject jsonObject = new JSONObject(response);

                                            Toast.makeText(holder.itemView.getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                            hodlist.remove(position);
                                            HodListAdaptor.this.notifyItemRemoved(position);
                                            HodListAdaptor.this.notifyItemRangeChanged(position, hodlist.size());

                                            dialog.dismiss();

                                        } catch (JSONException e) {

                                            dialog.dismiss();
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(holder.itemView.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();


                            }
                        }) {

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> param = new HashMap<>();
                                param.put("Email", hod.getEmail());
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
        return hodlist.size();
    }

    public class HodlIstViewHolder extends RecyclerView.ViewHolder {

        CircleImageView hodimage;
        TextView HodName, HodDept;
        Button editHod, deleteHod;

        public HodlIstViewHolder(View itemView) {
            super(itemView);

            hodimage = (CircleImageView) itemView.findViewById(R.id.hodphoto);
            HodName = (TextView) itemView.findViewById(R.id.hodname);
            HodDept = (TextView) itemView.findViewById(R.id.hoddept);
            editHod = (Button) itemView.findViewById(R.id.hodeditprofile);
            deleteHod = (Button) itemView.findViewById(R.id.hoddeleteprofile);
        }


    }
}
