package dynamicdrillers.collegenoticeboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Happy-Singh on 2/24/2018.
 */

public class NoticeShowAdaptor extends RecyclerView.Adapter<NoticeShowAdaptor.NoticeShowViewHolder> {
    List<Notice> noticelist;
    Context context;
    public static final  String AuthorImageURL="http://192.168.1.8/Web-API-College-Noticeboard/Storage/PersonProfiles/";


    public NoticeShowAdaptor(List<Notice> noticelist) {
        this.noticelist=noticelist;
    }

    @Override
    public NoticeShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_notice_show,parent,false);
        return new NoticeShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoticeShowViewHolder holder, int position) {

        final Notice notice = noticelist.get(position);

        holder.AuthorName.setText(notice.getNoticeAuthor().toUpperCase());
        holder.NoticeTime.setText(notice.getTime());
        Picasso.with(holder.itemView.getContext()).load(AuthorImageURL+"Person"+notice.getNoticeAuthorImage()+".png").into(holder.Author_Profile);

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
                Intent intent = new Intent(v.getContext(),NoticePostShow.class);
                intent.putExtra("NoticeTitle",notice.getNoticeTitle());
                intent.putExtra("NoticeDesc",notice.getNoticeDesc());
                intent.putExtra("NoticeImage",notice.getImage());
                intent.putExtra("NoticeTime",notice.getTime());
                intent.putExtra("AuthorName",notice.getNoticeAuthor());

                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return noticelist.size();
    }

    public  class  NoticeShowViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView Author_Profile;
        TextView AuthorName,NoticeTitle,NoticeDesc,NoticeTime;

        public NoticeShowViewHolder(View itemView) {
            super(itemView);

            Author_Profile = (CircleImageView)itemView.findViewById(R.id.authorimage);
            AuthorName = (TextView)itemView.findViewById(R.id.authorname);
            NoticeTitle = (TextView)itemView.findViewById(R.id.noticetitle);
            NoticeDesc = (TextView)itemView.findViewById(R.id.noticedescription);
            NoticeTime = (TextView)itemView.findViewById(R.id.noticetime);
        }
    }
}
