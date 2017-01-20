package co.mtaindia.mta.Activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import co.mtaindia.mta.adapters.BatchAdapter;
import co.mtaindia.mta.beans.BatchBean;

public class BatchActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<BatchBean> mUsers = new ArrayList<>();
    private BatchAdapter mUserAdapter;
    ProgressBar progressBar;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("mta");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycleView);
        GridLayoutManager glm = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(glm);
        new MyTask().execute();
    }

    public void moreBatchInfo(View view) {
        Toast.makeText(this, "comming soon!!!!", Toast.LENGTH_SHORT).show();
    }

    private class MyTask extends AsyncTask<String,String,String>
    {

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
                        mUserAdapter = new BatchAdapter(mUsers,BatchActivity.this);
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
