package co.mtaindia.mta.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import co.mtaindia.mta.activities.MainActivity;
import co.mtaindia.mta.adapters.BatchAdapter;
import co.mtaindia.mta.beans.BatchBean;

public class tab4 extends Fragment {
    public static TextView cityinfo;
    public static CardView popup;
    public static FrameLayout frameLayout;
    public static ProgressBar progressBarPopup;
    DatabaseReference myRef;
    ImageView closepopup;
    ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private List<BatchBean> mUsers = new ArrayList<>();
    private BatchAdapter mUserAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab4, container, false);
        cityinfo = (TextView) view.findViewById(R.id.info);
        popup = (CardView) view.findViewById(R.id.popup);
        progressBarPopup = (ProgressBar) view.findViewById(R.id.progressBarPopup);
        frameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
        frameLayout.getBackground().setAlpha(0);
        closepopup = (ImageView) view.findViewById(R.id.close);
        closepopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.vibe.vibrate(15);
                popup.setVisibility(View.GONE);
                frameLayout.getBackground().setAlpha(0);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("mta");
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar4);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(sglm);
        new MyTask().execute();
        return view;
    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            myRef.child("city").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        mUsers.clear();
                        ArrayList hashMap = (ArrayList) dataSnapshot.getValue();
                        int size = hashMap.size();
                        for (int i = 0; i < size; i++) {
                            HashMap hashMap1 = (HashMap) hashMap.get(i);
                            BatchBean stu = new BatchBean(hashMap1.get("url").toString(),
                                    hashMap1.get("name").toString());

                            mUsers.add(stu);

                        }
                        mUserAdapter = new BatchAdapter(mUsers, getActivity());
                        mRecyclerView.setAdapter(mUserAdapter);
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
