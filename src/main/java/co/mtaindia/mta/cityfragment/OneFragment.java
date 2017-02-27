package co.mtaindia.mta.cityfragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.mtaindia.mta.R;
import co.mtaindia.mta.adapters.CityAdapter;
import co.mtaindia.mta.beans.CityBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {

    ProgressBar progressBar;
    String cityname;
    DatabaseReference myRef;
    private RecyclerView mRecyclerView;
    private List<CityBean> mUsers = new ArrayList<>();
    private CityAdapter mUserAdapter;

    public OneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("mta");
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cityname = getActivity().getIntent().getStringExtra("cityname");
        new MyTask().execute();
        return view;
    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            myRef.child(cityname).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        mUsers.clear();
                        ArrayList hashMap = (ArrayList) dataSnapshot.getValue();
                        int size = hashMap.size();
                        for (int i = 0; i < size; i++) {
                            HashMap hashMap1 = (HashMap) hashMap.get(i);
                            CityBean stu = new CityBean(hashMap1.get("name").toString(),
                                    hashMap1.get("duration").toString(),
                                    hashMap1.get("fee").toString(),
                                    hashMap1.get("registered").toString(),
                                    hashMap1.get("totalbatch").toString(),
                                    hashMap1.get("dp").toString());

                            mUsers.add(stu);

                        }
                        mUserAdapter = new CityAdapter(mUsers, getActivity());
                        mRecyclerView.setAdapter(mUserAdapter);
                        mUserAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(getActivity(), "Database empty!!!", Toast.LENGTH_SHORT).show();
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
