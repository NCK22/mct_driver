package com.example.joelwasserman.androidbletutorial.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
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
    public void onBindViewHolder(final ItemRowHolder holder, final int position) {
        final ChildPojoStudProf singleItem = dataList.get(position);
        holder.textName.setText(singleItem.getChildName());

       /* if(MainActivity.list_macId.contains(singleItem.getChildMacID()))
            holder.imgStatus.setImageResource(R.drawable.ic_present);*/

       if(!singleItem.getFound().equalsIgnoreCase("ignore")) {
           if (singleItem.getFound().equalsIgnoreCase("true"))
               holder.imgStatus.setImageResource(R.drawable.ic_present);
           else
               holder.imgStatus.setImageResource(R.drawable.ic_absent);
       }

      /*  holder.textDescrip.setText(singleItem.getCompDescr());
        holder.textIType.setText(singleItem.getCompIType());
        holder.textLoc.setText(singleItem.getCompLoc());
        holder.textWeb.setText(singleItem.getCompWebEmail());*/

      holder.textIgnore.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(holder.textIgnore.getText().toString().equalsIgnoreCase("ignore")) {
                  singleItem.setFound("ignore");
                  holder.imgStatus.setImageResource(R.drawable.ic_ignore);
                  holder.textIgnore.setText("Remove from Ignore");
              }
              else{
                  singleItem.setFound("");
                  holder.imgStatus.setImageResource(R.drawable.ic_retry);
                  holder.textIgnore.setText("Ignore");
              }
          }
      });

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

        holder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+singleItem.getPhone()));
                //callIntent.setData(Uri.parse("tel:"+));

                if (ActivityCompat.checkSelfPermission(mContext,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mContext.startActivity(callIntent);
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
        public TextView textName,textIgnore,textDescrip,textLoc,textWeb,textIType;
        public LinearLayout lyt_parent;
        public Button btnCall,btnInfo;

        public ItemRowHolder(View itemView) {
            super(itemView);

            textName = (TextView) itemView.findViewById(R.id.tv_name);
            textIgnore = (TextView) itemView.findViewById(R.id.tv_ignore);
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
