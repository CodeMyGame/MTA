package co.mtaindia.mta.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import co.mtaindia.mta.R;
import co.mtaindia.mta.activities.CityActivity;
import co.mtaindia.mta.activities.HomeActivity;
import co.mtaindia.mta.beans.BatchBean;
import co.mtaindia.mta.fragments.tab4;

/**
 * Created by Kapil Gehlot on 1/19/2017.
 */

public class BatchAdapter extends RecyclerView.Adapter<BatchAdapter.MyViewHolder> {
    private List<BatchBean> gallaryList;
    private Context context;
    private DatabaseReference myRef;
    public BatchAdapter(List<BatchBean> gList, Context c) {
        this.gallaryList = gList;
        this.context = c;
    }

    @Override
    public BatchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.batch_row, parent, false);

        return new BatchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BatchAdapter.MyViewHolder holder, final int position) {
        final BatchBean gallary = gallaryList.get(position);
        holder.tvHeading.setText(gallary.getHeading());
        Glide
                .with(context)
                .load(gallaryList.get(position).url)
                .placeholder(R.drawable.image_no)
                .crossFade()
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CityActivity.class);
                intent.putExtra("cityname", gallary.getHeading().toLowerCase());
                context.startActivity(intent);
            }
        });
        holder.moreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.vibe.vibrate(15);
                tab4.popup.setVisibility(View.VISIBLE);
                tab4.frameLayout.getBackground().setAlpha(240);
                myRef.child("cityinfo").child(gallary.getHeading().toLowerCase()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        tab4.progressBarPopup.setVisibility(View.GONE);
                        if (dataSnapshot.exists()) {
                            String address = dataSnapshot.getValue().toString();
                            tab4.cityinfo.setText(address);
                        } else {
                            tab4.cityinfo.setText("Information available soon!!!!!");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return gallaryList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        TextView tvHeading;
        ImageView moreinfo;

        MyViewHolder(View itemView) {
            super(itemView);
            tvHeading = (TextView) itemView.findViewById(R.id.title_city);
            imageView = (ImageView) itemView.findViewById(R.id.head_img);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            moreinfo = (ImageView) itemView.findViewById(R.id.moreinfo);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            myRef = database.getReference("mta");
        }
    }
}
