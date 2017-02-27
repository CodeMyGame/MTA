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
 * Created by Kapil Gehlot on 2/26/2017.
 */

public class StudyAdapter extends RecyclerView.Adapter<StudyAdapter.MyViewHolder> {
    private List<TabCourseBean> gallaryList;
    private Context context;
    private StudyAdapter.ClickListener clickListener;

    public StudyAdapter(List<TabCourseBean> gList, Context c) {
        this.gallaryList = gList;
        this.context = c;
    }

    public void setClickListener(StudyAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public StudyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.study_row, parent, false);

        return new StudyAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StudyAdapter.MyViewHolder holder, final int position) {
        final TabCourseBean gallary = gallaryList.get(position);
        holder.tvHeading.setText(gallary.getName());
        holder.tvHeadingBig.setText("" + gallary.getName().toUpperCase().charAt(0));

    }

    @Override
    public int getItemCount() {
        return gallaryList.size();
    }

    public interface ClickListener {
        void itemClicked(View v, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvHeadingBig;
        TextView tvHeading;

        MyViewHolder(View itemView) {
            super(itemView);
            tvHeading = (TextView) itemView.findViewById(R.id.textView3);
            tvHeadingBig = (TextView) itemView.findViewById(R.id.textViewBig);
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
