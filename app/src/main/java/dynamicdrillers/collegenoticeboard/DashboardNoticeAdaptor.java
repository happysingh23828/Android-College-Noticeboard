package dynamicdrillers.collegenoticeboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Happy-Singh on 2/24/2018.
 */

public class DashboardNoticeAdaptor extends RecyclerView.Adapter<DashboardNoticeAdaptor.DashboardNoticeViewHolder> {
    private  Context context;
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
    public void onBindViewHolder(final DashboardNoticeViewHolder holder, final int position) {
        final SharedpreferenceHelper sharedpreferenceHelper  = SharedpreferenceHelper.getInstance(holder.itemView.getContext());
        String checkTypeFlag =  sharedpreferenceHelper.getType();

        if(position==3)
        {
            if(checkTypeFlag.equals("student"))
            {
                holder.cardViewright.setVisibility(View.GONE);
            }
            else
            {
                holder.cardViewleft.setVisibility(View.GONE);
                holder.cardViewright.setVisibility(View.GONE);
            }
        }

        holder.lefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ShowNoticeByType.class);
                if(position==3)
                {

                    intent.putExtra("tgemail",sharedpreferenceHelper.getTgEmail());
                }
                else {
                    intent.putExtra("tgemail","notg");
                }
                intent.putExtra("NoticeCollegeCode","0536");
                intent.putExtra("NoticeType",noticename1[position]);

                v.getContext().startActivity(intent);
            }
        });

        holder.righticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ShowNoticeByType.class);
                intent.putExtra("tgemail","notg");
                intent.putExtra("NoticeCollegeCode","0536");
                intent.putExtra("NoticeType",noticename2[position]);
                v.getContext().startActivity(intent);

            }
        });

        holder.imageView1.setImageResource(noticeicons1[position]);
        holder.textView1.setText(noticename1[position]+" Notice");
        holder.imageView2.setImageResource(noticeicons2[position]);
        holder.textView2.setText(noticename2[position]+" Notice");

    }

    @Override
    public int getItemCount() {
        return noticeicons1.length;
    }

    public  class  DashboardNoticeViewHolder extends  RecyclerView.ViewHolder
    {
        CircleImageView imageView1,imageView2;
        CardView cardViewleft,cardViewright;
        TextView textView1,textView2;
        LinearLayout lefticon,righticon;

        public DashboardNoticeViewHolder(View itemView) {
            super(itemView);
            imageView1 = (CircleImageView)itemView.findViewById(R.id.noticeicon1);
            textView1 = (TextView)itemView.findViewById(R.id.noticename1);
            imageView2 = (CircleImageView)itemView.findViewById(R.id.noticeicon2);
            textView2 = (TextView)itemView.findViewById(R.id.noticename2);
            lefticon = (LinearLayout)itemView.findViewById(R.id.left_notice_icon_root_layout);
            righticon = (LinearLayout)itemView.findViewById(R.id.right_notice_icon_root_layout);
            cardViewleft = (CardView)itemView.findViewById(R.id.leftcardview);
            cardViewright = (CardView)itemView.findViewById(R.id.rightcardview);
        }


    }
}
