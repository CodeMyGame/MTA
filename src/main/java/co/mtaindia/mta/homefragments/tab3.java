package co.mtaindia.mta.homefragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.mtaindia.mta.R;
import co.mtaindia.mta.activities.StudyActivity;
import co.mtaindia.mta.adapters.TabCourseAdapter;
import co.mtaindia.mta.beans.TabCourseBean;

public class tab3 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView;
        final List<TabCourseBean> gallaryList = new ArrayList<>();
        final TabCourseAdapter adapter = new TabCourseAdapter(gallaryList, getContext());
        View rootView = inflater.inflate(R.layout.activity_material, container, false);
        gallaryList.add(new TabCourseBean("JAVA"));
        gallaryList.add(new TabCourseBean("AD. JAVA"));
        gallaryList.add(new TabCourseBean("Python"));
        gallaryList.add(new TabCourseBean("Hadoop"));
        gallaryList.add(new TabCourseBean("Android"));
        gallaryList.add(new TabCourseBean("iOS"));
        gallaryList.add(new TabCourseBean("ASP .Net"));
        gallaryList.add(new TabCourseBean("C#"));
        gallaryList.add(new TabCourseBean("C"));
        gallaryList.add(new TabCourseBean("PHP"));
        gallaryList.add(new TabCourseBean("MYSQL"));
        gallaryList.add(new TabCourseBean("Aptitude"));
        gallaryList.add(new TabCourseBean("Data Structure"));
        gallaryList.add(new TabCourseBean("DBMS"));
        gallaryList.add(new TabCourseBean("Computer networks"));
        adapter.notifyDataSetChanged();
        adapter.setClickListener(new TabCourseAdapter.ClickListener() {
            @Override
            public void itemClicked(View v, int position) {
                Intent intent = new Intent(getContext(), StudyActivity.class);
                TextView textView = (TextView) v.findViewById(R.id.coursename);
                intent.putExtra("name", textView.getText().toString());
                startActivity(intent);
                getActivity().overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
            }
        });
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

}