package co.mtaindia.mta.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.mtaindia.mta.R;
import co.mtaindia.mta.beans.TabCourseBean;

/**
 * Created by Kapil Gehlot on 2/25/2017.
 */

public class TabCourseAdapter extends RecyclerView.Adapter<TabCourseAdapter.MyViewHolder> {
    private List<TabCourseBean> gallaryList;
    private Context context;
    private TabCourseAdapter.ClickListener clickListener;

    public TabCourseAdapter(List<TabCourseBean> gList, Context c) {
        this.gallaryList = gList;
        this.context = c;
    }

    public void setClickListener(TabCourseAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public TabCourseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tabcourse, parent, false);

        return new TabCourseAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TabCourseAdapter.MyViewHolder holder, final int position) {
        final TabCourseBean gallary = gallaryList.get(position);
        holder.tvHeading.setText(gallary.getName());

    }

    @Override
    public int getItemCount() {
        return gallaryList.size();
    }

    public interface ClickListener {
        void itemClicked(View v, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvHeading;

        MyViewHolder(View itemView) {
            super(itemView);
            tvHeading = (TextView) itemView.findViewById(R.id.coursename);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.itemClicked(view, getPosition());
            }
        }
    }
}
