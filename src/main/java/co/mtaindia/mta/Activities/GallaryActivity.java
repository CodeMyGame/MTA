package co.mtaindia.mta.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import co.mtaindia.mta.R;
import co.mtaindia.mta.adapters.GallaryAdapter;
import co.mtaindia.mta.beans.GallaryBean;

public class GallaryActivity extends Activity {

    RecyclerView recyclerView;
    GallaryAdapter adapter;
    List<GallaryBean> gallaryList;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallary);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("mta");
        myRef.child("gallary").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    gallaryList.clear();
                    ArrayList data = (ArrayList)dataSnapshot.getValue();
                    int size = data.size();
                    for(int i=0;i<size;i++){
                        gallaryList.add(new GallaryBean(data.get(i).toString()));
                    }
                        adapter.notifyDataSetChanged(); //very important for recycler view data changed
                        progressBar.setVisibility(View.GONE);
                    }
                }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        gallaryList = new ArrayList<>();
        adapter = new GallaryAdapter(gallaryList,this);
        recyclerView.setAdapter(adapter);
    }
}
