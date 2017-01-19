package co.mtaindia.mta.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.mtaindia.mta.R;
import co.mtaindia.mta.RecyclerViewPack.CertificationAdapter;
import co.mtaindia.mta.RecyclerViewPack.CertificationBean;
import co.mtaindia.mta.RecyclerViewPack.HomeAdapter;
import co.mtaindia.mta.RecyclerViewPack.HomeBean;

public class tab2 extends Fragment {

    private RecyclerView mRecyclerView;
    private List<CertificationBean> mUsers = new ArrayList<>();
    private CertificationAdapter mUserAdapter;
    ProgressBar progressBar;
    DatabaseReference myRef;
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new MyTask().execute();
        return view;


    }
    private class MyTask extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            //  progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... params) {
            myRef.child("certificate").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        mUsers.clear();
                        ArrayList hashMap = (ArrayList) dataSnapshot.getValue();
                        int size = hashMap.size();
                        for (int i = 0; i < size; i++) {
                            HashMap hashMap1 = (HashMap) hashMap.get(i);
                            CertificationBean stu = new CertificationBean(hashMap1.get("url").toString(),
                                    hashMap1.get("heading").toString(),
                                    hashMap1.get("description").toString(),
                                    hashMap1.get("code").toString());

                            mUsers.add(stu);

                        }
                        mUserAdapter = new CertificationAdapter(mUsers,getActivity());
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

        @Override
        protected void onPostExecute(String s) {
            // progressBar.setVisibility(View.INVISIBLE);
            super.onPostExecute(s);
        }
    }

}
