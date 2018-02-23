package dynamicdrillers.collegenoticeboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Happy-Singh on 2/24/2018.
 */

public class DashboardNoticeAdaptor extends RecyclerView.Adapter<DashboardNoticeAdaptor.DashboardNoticeViewHolder> {

    String[] noticename1,noticename2;
    int[] noticeicons1,noticeicons2;

    public DashboardNoticeAdaptor( String[] noticename1,String[] noticename2,int[] noticeicons1,int[] noticeicons2) {
        this.noticeicons1 = noticeicons1;
        this.noticename1 = noticename1;
        this.noticeicons2 = noticeicons2;
        this.noticename2 = noticename2;
    }

    @Override
    public DashboardNoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_noticeboard_icons,parent,false);
        return new DashboardNoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DashboardNoticeViewHolder holder, int position) {

        holder.imageView1.setImageResource(noticeicons1[position]);
        holder.textView1.setText(noticename1[position]);
        holder.imageView2.setImageResource(noticeicons2[position]);
        holder.textView2.setText(noticename2[position]);

    }

    @Override
    public int getItemCount() {
        return noticeicons1.length;
    }

    public  class  DashboardNoticeViewHolder extends  RecyclerView.ViewHolder
    {
        CircleImageView imageView1,imageView2;
        TextView textView1,textView2;

        public DashboardNoticeViewHolder(View itemView) {
            super(itemView);
            imageView1 = (CircleImageView)itemView.findViewById(R.id.noticeicon1);
            textView1 = (TextView)itemView.findViewById(R.id.noticename1);
            imageView2 = (CircleImageView)itemView.findViewById(R.id.noticeicon2);
            textView2 = (TextView)itemView.findViewById(R.id.noticename2);

        }


    }
}
