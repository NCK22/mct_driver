package com.example.joelwasserman.androidbletutorial.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joelwasserman.androidbletutorial.Activity.MainActivity;
import com.example.joelwasserman.androidbletutorial.Activity.StudDetailActivity;
import com.example.joelwasserman.androidbletutorial.Pojo.ChildPojoStudProf;
import com.example.joelwasserman.androidbletutorial.R;

import java.util.ArrayList;

//import com.squareup.picasso.Picasso;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ItemRowHolder> {

    private ArrayList<ChildPojoStudProf> dataList;
    private Context mContext;
    //MyApplication MyApp;

    public StudentAdapter(Context context, ArrayList<ChildPojoStudProf> dataList) {
        this.dataList = dataList;
        this.mContext = context;
       // MyApp=MyApplication.getInstance();
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_student, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemRowHolder holder, final int position) {
        final ChildPojoStudProf singleItem = dataList.get(position);
        holder.textName.setText(singleItem.getChildName());

        if(MainActivity.list_macId.contains(singleItem.getMacId()))
            holder.imgStatus.setImageResource(R.drawable.ic_present);
      /*  holder.textDescrip.setText(singleItem.getCompDescr());
        holder.textIType.setText(singleItem.getCompIType());
        holder.textLoc.setText(singleItem.getCompLoc());
        holder.textWeb.setText(singleItem.getCompWebEmail());*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent=new Intent(mContext,StudDetailActivity.class);
                intent.putExtra("child_id",singleItem.getChild_id());
                mContext.startActivity(intent);*/
            }
        });

        holder.imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("clicked","adapter");
                Intent intent=new Intent(mContext,StudDetailActivity.class);
                intent.putExtra("child_id",singleItem.getChild_id());
                mContext.startActivity(intent);
            }
        });
       /* if (!singleItem.getUserCity().isEmpty()) {
            holder.textCity.setText(singleItem.getUserCity());
        } else {
            holder.textCity.setVisibility(View.GONE);
        }
        if (!singleItem.getUserImage().isEmpty()) {
            Picasso.with(mContext).load(singleItem.getUserImage()).placeholder(R.mipmap.ic_launcher_app).into(holder.image);
        }

        if (singleItem.getUserResume().isEmpty()) {
            holder.btnResume.setVisibility(View.GONE);
        }

        holder.textEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", singleItem.getUserEmail(), null));
                emailIntent
                        .putExtra(Intent.EXTRA_SUBJECT, "Reply for the post ");
                mContext.startActivity(Intent.createChooser(emailIntent, "Send suggestion..."));
            }
        });

        holder.textPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", singleItem.getUserPhone(), null));
                mContext.startActivity(intent);
            }
        });

        holder.btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(singleItem.getUserResume()));
                mContext.startActivity(intent);
            }
        });

        holder.btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bookmarkAppl(dataList.get(position).getUserId());
            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        public ImageView imgCall,imgInfo,imgStatus;
        public TextView textName,textDescrip,textLoc,textWeb,textIType;
        public LinearLayout lyt_parent;
        public Button btnCall,btnInfo;

        public ItemRowHolder(View itemView) {
            super(itemView);

            textName = (TextView) itemView.findViewById(R.id.tv_name);
       imgCall=(ImageView)itemView.findViewById(R.id.img_call);
            imgInfo=(ImageView)itemView.findViewById(R.id.img_info);
            imgStatus=(ImageView)itemView.findViewById(R.id.img_status);
          /*  textDescrip = (TextView) itemView.findViewById(R.id.text_comp_description);
            textLoc = (TextView) itemView.findViewById(R.id.text_job_address);
            textWeb = (TextView) itemView.findViewById(R.id.text_comp_web);
            textIType = (TextView) itemView.findViewById(R.id.text_comp_type);
            lyt_parent = (LinearLayout) itemView.findViewById(R.id.rootLayout);
          */

        }

    }

}
