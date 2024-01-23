package com.example.crafty;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageButton arrow2;
    GridView gridView;
    ArrayList<DataClass2> dataList2;
    Adapter2 adapter2;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Product");

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrow2 = findViewById(R.id.arrow2);
        gridView = findViewById(R.id.gridview);

        dataList2 = new ArrayList<>();
        adapter2 = new Adapter2(dataList2, this);
        gridView.setAdapter(adapter2);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList2.clear(); // Clear the list to avoid duplicates on data update
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DataClass2 dataClass2 = dataSnapshot.getValue(DataClass2.class);
                    // Ensure the key is set
                    dataClass2.setKey(dataSnapshot.getKey());
                    dataList2.add(dataClass2);
                }
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });

        arrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                startActivity(intent);
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {Intent profileIntent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(profileIntent);
                    return true;
                } else if (itemId == R.id.navigation_chart) {// Handle Chart button click
                    Intent profileIntent = new Intent(MainActivity.this, ProductActivity.class);
                    startActivity(profileIntent);
                    return true;
                } else if (itemId == R.id.navigation_profile) {// Handle Profile button click
                    Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                    return true;
                }

                return false;
            }
        });



    }
}
