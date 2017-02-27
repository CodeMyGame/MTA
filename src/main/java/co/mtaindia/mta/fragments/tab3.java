package co.mtaindia.mta.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.mtaindia.mta.R;
import co.mtaindia.mta.activities.CourseNextActivity;
import co.mtaindia.mta.adapters.HomeAdapter;
import co.mtaindia.mta.beans.HomeBean;

public class tab3 extends Fragment implements HomeAdapter.ClickListener {
    public static Drawable drawable;
    ProgressBar progressBar;
    DatabaseReference myRef;
    SwipeRefreshLayout swipeRefreshLayout;
    private List<HomeBean> mUsers = new ArrayList<>();
    private HomeAdapter mUserAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("mta");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar4);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        mUserAdapter = new HomeAdapter(mUsers, getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUserAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mUserAdapter);
        new MyTask().execute();
        return view;
    }

    @Override
    public void itemClicked(View v, int position) {
        Intent intent = new Intent(getContext(), CourseNextActivity.class);
        intent.putExtra("position", position);
        ImageView imageView = (ImageView) v.findViewById(R.id.head_img);
        TextView textView = (TextView) v.findViewById(R.id.heading_summer);
        drawable = imageView.getDrawable();
        intent.putExtra("name", textView.getText().toString());
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imageView, "mytransition");
        startActivity(intent, activityOptionsCompat.toBundle());
    }

    private class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            myRef.child("course").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        mUsers.clear();
                        ArrayList hashMap = (ArrayList) dataSnapshot.getValue();
                        int size = hashMap.size();
                        for (int i = 0; i < size; i++) {
                            HashMap hashMap1 = (HashMap) hashMap.get(i);
                            HomeBean stu = new HomeBean(hashMap1.get("url").toString(),
                                    hashMap1.get("heading").toString(),
                                    hashMap1.get("description").toString());
                            mUsers.add(stu);
                        }

                        mUserAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            return null;
        }
    }
}