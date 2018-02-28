package dynamicdrillers.collegenoticeboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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
    public void onBindViewHolder(HodlIstViewHolder holder, int position) {

        Hod hod = hodlist.get(position);

        holder.HodName.setText(hod.getName());
        holder.HodDept.setText("Dept : "+hod.getDept());
        Picasso.with(holder.itemView.getContext()).load(Constants.HOD_PROFILE_STORAGE_URL+"Hod"+hod.getEmail()+".png").into(holder.hodimage);

        holder.editHod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(),"Edit Profile Clicked",Toast.LENGTH_LONG).show();
            }
        });

        holder.deleteHod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(),"Delete Profile Clicked",Toast.LENGTH_LONG).show();
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
