package co.mtaindia.mta.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.mtaindia.mta.Interfaces.LoadMoreListener;
import co.mtaindia.mta.Picasso.PicasoClient;
import co.mtaindia.mta.R;
import co.mtaindia.mta.RecyclerViewPack.GallaryBean;
import co.mtaindia.mta.RecyclerViewPack.HomeAdapter;
import co.mtaindia.mta.RecyclerViewPack.HomeBean;
import co.mtaindia.mta.RecyclerViewPack.Home_horizontalAdapter;
import co.mtaindia.mta.RecyclerViewPack.Home_horizontalBean;

public class tab1 extends Fragment {
    private RecyclerView mRecyclerView,mRecyclerViewHori;
    private List<HomeBean> mUsers = new ArrayList<>();
    private List<Home_horizontalBean> mUsersH = new ArrayList<>();
    private HomeAdapter mUserAdapter;
    private Home_horizontalAdapter home_horizontalAdapter;
    ProgressBar progressBar;
    DatabaseReference myRef;
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("mta");
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar4);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        mRecyclerViewHori = (RecyclerView) view.findViewById(R.id.recycleViewHorizontal);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewHori.setLayoutManager(layoutManager);
        mUserAdapter = new HomeAdapter(mUsers,getActivity());
        mRecyclerView.setAdapter(mUserAdapter);
        home_horizontalAdapter = new Home_horizontalAdapter(mUsersH,getActivity());
        mRecyclerViewHori.setAdapter(home_horizontalAdapter);
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
            myRef.child("homecourseimg").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        mUsersH.clear();
                        ArrayList data = (ArrayList)dataSnapshot.getValue();
                        int size = data.size();
                        for(int i=0;i<size;i++){
                            mUsersH.add(new Home_horizontalBean(data.get(i).toString()));
                        }
                        home_horizontalAdapter.notifyDataSetChanged(); //very important for recycler view data changed
                        new MyTask2().execute();

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
    private class MyTask2 extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            //  progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... params) {

            myRef.child("home").addValueEventListener(new ValueEventListener() {
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
                            //mUsersH.add(new Home_horizontalBean(hashMap1.get("url").toString()));
                        }
                       // home_horizontalAdapter.notifyDataSetChanged();
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