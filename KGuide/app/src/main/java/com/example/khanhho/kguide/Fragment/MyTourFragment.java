package com.example.khanhho.kguide.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.khanhho.kguide.Activities.AddTourActivity;
import com.example.khanhho.kguide.Activities.TourDetailActivity;
import com.example.khanhho.kguide.Adapter.MytourAdapter;
import com.example.khanhho.kguide.Model.Tour;
import com.example.khanhho.kguide.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyTourFragment extends Fragment implements MytourAdapter.OnClickListener{
    private View nRootView;
    private String currentUser;
    private FirebaseAuth mAuth;
    private Tour tour;
    private MytourAdapter adapter;
    private List<String> idList = new ArrayList<>();
    private Task<Void> reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nRootView = inflater.inflate(R.layout.fragment_my_tour, container, false);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        List<Tour> data = getListData();
        final ListView listView = (ListView) nRootView.findViewById(R.id.lv_mytour);
        adapter = new MytourAdapter(getContext(),data, this);
        listView.setAdapter(adapter);

        // Khi người dùng click vào các ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent mIntent = new Intent(getContext(), TourDetailActivity.class);
                mIntent.putExtra("id",idList.get(position));
                startActivity(mIntent);
            }
        });
        FloatingActionButton fab = nRootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddTourActivity.class);
                startActivity(intent);
            }
        });
        return nRootView;
    }

    private  List<Tour> getListData() {
        final List<Tour> list = new ArrayList<Tour>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("tour").child(currentUser);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    tour = messageSnapshot.getValue(Tour.class);
                    list.add(tour);
                    idList.add(messageSnapshot.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

        return list;
    }

    @Override
    public void onEditClick(int pos) {
        Intent intent = new Intent(getContext(), AddTourActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int pos) {
        alertView("Are your sure to delete?", pos);
    }

    private void alertView(String message, final int pos) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Confirm")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        reference = FirebaseDatabase.getInstance().getReference("tour").child(currentUser).child(idList.get(pos)).removeValue();
                    }
                }).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}
