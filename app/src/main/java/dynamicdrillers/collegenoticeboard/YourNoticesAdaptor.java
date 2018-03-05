package dynamicdrillers.collegenoticeboard;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
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
 * Created by Happy-Singh on 3/1/2018.
 */

public class YourNoticesAdaptor extends RecyclerView.Adapter<YourNoticesAdaptor.YourNoticesViewHolder>  {


    List<Notice> noticelist;
    Context context;
    SpotsDialog spotsDialog;

    public YourNoticesAdaptor(List<Notice> noticelist) {
        this.noticelist = noticelist;
    }


    @Override
    public YourNoticesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_notice_show,parent,false);
        return new YourNoticesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final YourNoticesViewHolder holder, final int position) {
        spotsDialog  = new SpotsDialog(holder.itemView.getContext());
        final SharedpreferenceHelper sharedpreferenceHelper = SharedpreferenceHelper.getInstance(holder.itemView.getContext());
        final Notice notice = noticelist.get(position);

        holder.AuthorName.setText(notice.getNoticeAuthor().toUpperCase());
        holder.NoticeTime.setText(notice.getTime());

        //Constants.PERSON_PROFILE_STORAGE_URL+"Person"+notice.getNoticeAuthorImage()+".png")
        Picasso.with(holder.itemView.getContext()).load("http://192.168.56.1/Web-API-College-Noticeboard/Storage/PersonProfiles/Personhappy123@gmail.com.png").into(holder.Author_Profile);

        if(notice.getNoticeTitle().length()>=50)
        {
            holder.NoticeTitle.setText(notice.getNoticeTitle().subSequence(0,50)+"...");
        }
        else{
            holder.NoticeTitle.setText(notice.getNoticeTitle());
        }

        if(notice.getNoticeDesc().length()>=70)
        {
            holder.NoticeDesc.setText(notice.getNoticeDesc().subSequence(0,70)+"...");
        }
        else{
            holder.NoticeDesc.setText(notice.getNoticeDesc());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String WEB_URL;

                if(notice.Notice_Type.equals("tg"))
                {
                    WEB_URL = Constants.WEB_API_URL+"FacultyDeleteTgNotice.php";
                }
                else if(notice.getNotice_Type().equals("dept"))
                {
                    WEB_URL = Constants.WEB_API_URL+"FacultyDeleteDeptNotice.php";
                }
                else
                    WEB_URL = Constants.WEB_API_URL+"FacultyDeleteCollegeNotice.php";


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


                        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, WEB_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        spotsDialog.dismiss();

                                        try {

                                            JSONObject jsonObject = new JSONObject(response);

                                            Toast.makeText(holder.itemView.getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                            noticelist.remove(position);
                                            YourNoticesAdaptor.this.notifyItemRemoved(position);
                                            YourNoticesAdaptor.this.notifyItemRangeChanged(position, noticelist.size());
                                            dialog.dismiss();

                                        } catch (JSONException e) {
                                            Toast.makeText(holder.itemView.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                spotsDialog.dismiss();
                                Toast.makeText(holder.itemView.getContext(), "Some Network Issues", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }
                        }) {

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> param = new HashMap<>();
                                param.put("Id", notice.getNoticeId());
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
        return noticelist.size();
    }

    public  class  YourNoticesViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView Author_Profile;
        TextView AuthorName,NoticeTitle,NoticeDesc,NoticeTime;

        public YourNoticesViewHolder(View itemView) {
            super(itemView);
            Author_Profile = (CircleImageView)itemView.findViewById(R.id.authorimage);
            AuthorName = (TextView)itemView.findViewById(R.id.authorname);
            NoticeTitle = (TextView)itemView.findViewById(R.id.noticetitle);
            NoticeDesc = (TextView)itemView.findViewById(R.id.noticedescription);
            NoticeTime = (TextView)itemView.findViewById(R.id.noticetime);
        }
    }


    private void dialogBulder(String Text, final String Type,String Head) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_edittext_dialog_layout);
        dialog.setTitle("Update...");

        TextView text = (TextView) dialog.findViewById(R.id.dialog_title);
        text.setText(Head);

        final TextInputLayout textInputLayout = dialog.findViewById(R.id.dialog_edittext);
        textInputLayout.getEditText().setText(Text);

        Button cencel = (Button) dialog.findViewById(R.id.dialog_btn_cencel);
        // if button is clicked, close the custom dialog
        cencel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button delete = (Button) dialog.findViewById(R.id.dialog_btn_delete);
        // if button is clicked, close the custom dialog
        cencel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
