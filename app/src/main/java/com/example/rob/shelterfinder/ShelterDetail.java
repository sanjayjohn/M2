package com.example.rob.shelterfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShelterDetail extends AppCompatActivity {
    private ArrayList<String> array;
    DatabaseReference mRef;
    ListView listview2;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AppActivity.class);
                startActivity(i);
            }
        });

        mRef = FirebaseDatabase.getInstance().getReference();
        array = new ArrayList<>();
        final ListView listView2 = (ListView) findViewById(R.id.listView2);

        mRef.child("Shelters").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                array.clear();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    ShelterData value = child.getValue(ShelterData.class);
                    if (value.getUniqueKey().equals(Integer.toString(AppActivity.positionList))) {
                        array.add("Shelter name:" + "    " + value.getShelterName());
                        array.add("Address:" + "    " + value.getAddress());
                        array.add("Capacity:" + "    " +  value.getCapacity());
                        array.add("Latitude:" + "    " + value.getLatitude());
                        array.add("Longitude:" +  "    " + value.getLongitude());
                        array.add("Gender:" + "    " +  value.getRestrictions());
                        Log.w("Item", ""+ value.getAddress());
//                        Log.w("Item", ""+ AppActivity.positionList);
                    }
//                    Log.w("Item", ""+ newValue);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, array);

                listView2.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void goBack() {
        Intent i = new Intent(this, AppActivity.class);
        startActivity(i);
    }



}
