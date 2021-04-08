package com.codewithcup.chalisa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.codewithcup.chalisa.Model.MainModel;
import com.codewithcup.chalisa.R;
import com.codewithcup.chalisa.Model.SubModelClass;
import com.codewithcup.chalisa.Adpater.SubRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChalisaContainer extends AppCompatActivity {

    private static final String TAG = "ChalisaContainer";
    RecyclerView recyclerViewSub;
    List<SubModelClass> subList;
    Context subContext;
    DatabaseReference mReference;
    SubRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chalisa_container);

        //hooks
        recyclerViewSub = findViewById(R.id.recyclerView_sub);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewSub.setLayoutManager(layoutManager);

        recyclerViewSub.setHasFixedSize(true);
        //Database calling
        mReference = FirebaseDatabase.getInstance().getReference();
        clearAll();
        callingSubCategory();
        /*subList = new ArrayList<>();
        //String _title =  getIntent().getStringExtra("title");
       // String _thumbnail =  getIntent().getStringExtra("thumbnail");
       String _name =  getIntent().getStringExtra("name");
       String _image =  getIntent().getStringExtra("image");
       String _description =  getIntent().getStringExtra("description");
       //subList.add(new MainModel(_name, _image, _description));
        //pass recyclerview
        callingSubRecyclerView();*/

    }


    private void callingSubCategory() {
        //Log.d(TAG,"++++++++++++++++++++++++++++++++++++++++++++++++ Inside Method");
        //Before Loading clearAll List
        clearAll();

        subList = new ArrayList<>();

        Query query = mReference.child("SubMainData");
       // Log.d(TAG,"++++++++++++++++++++++++++++++++++++++++++++++++ Query is " + mReference.child("SubMainData"));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    SubModelClass modelClass = new SubModelClass();
                    modelClass.setName(snapshot.child("name").getValue().toString());
                    modelClass.setImage(snapshot.child("image").getValue().toString());
                    modelClass.setDescription(snapshot.child("description").getValue().toString());
                    subList.add(modelClass);
                }
                //pass recyclerview
                callingSubRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(subContext, databaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }


    private void callingSubRecyclerView() {

        recyclerViewAdapter = new SubRecyclerViewAdapter(this, subList);
        recyclerViewSub.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void clearAll() {

        if(subList != null){
            subList.clear();
            if(recyclerViewAdapter != null){
                recyclerViewAdapter.notifyDataSetChanged();
            }
        }
        subList = new ArrayList<>();
    }
}