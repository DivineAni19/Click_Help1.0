package com.Help.Helping_1.click_help_1;


import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Single_Fragment_Admin extends Fragment {

    private Button Edit,Save,donation_info;
    private EditText event_name,event_place,event_description;

   private String nm;

    private TextView pl,ds;

    private DatabaseReference single_event,donation_event_name_edit;

    private Query dbq;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_single__fragment__admin, container, false);

        Edit=(Button)v.findViewById(R.id.edit);
        Save=(Button)v.findViewById(R.id.save);
        donation_info=(Button)v.findViewById(R.id.Donation_info);


        event_name=(EditText)v.findViewById(R.id.event_name_pl);
        event_place=(EditText)v.findViewById(R.id.event_place_pl);
        event_description=(EditText)v.findViewById(R.id.event_description_pl);

        pl=(TextView)v.findViewById(R.id.Pl_);
        ds=(TextView)v.findViewById(R.id.ds);


        pl.setPaintFlags(pl.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        ds.setPaintFlags(ds.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);





        Edit.setVisibility(View.VISIBLE);
        Save.setVisibility(View.GONE);



        Bundle bundle =new Bundle();
        bundle= this.getArguments();


        final  String post_key=bundle.getString("post_key");


        single_event= FirebaseDatabase.getInstance().getReference().child("Event").child(post_key);

        donation_event_name_edit=FirebaseDatabase.getInstance().getReference().child("Donation");
        dbq= donation_event_name_edit.orderByChild("Event").equalTo(post_key);

        single_event.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name=dataSnapshot.child("Title").getValue().toString();
                String desc=dataSnapshot.child("Description").getValue().toString();
                String place=dataSnapshot.child("Place").getValue().toString();

                event_name.setText(name);
                event_place.setText(place);
                event_description.setText(desc);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Toast.makeText(getActivity(),post_key,Toast.LENGTH_LONG).show();


        Edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Edit.setVisibility(View.GONE);
                Save.setVisibility(View.VISIBLE);
                event_name.setEnabled(true);
                event_place.setEnabled(true);
                event_description.setEnabled(true);
            }
        });

        Save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                event_name.setEnabled(false);
                event_place.setEnabled(false);
                event_description.setEnabled(false);
                Save.setVisibility(View.GONE);
                Edit.setVisibility(View.VISIBLE);

                Update_Event(post_key);

            }
        });

        donation_info.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in=new Intent(getActivity(),Donation_Info_Admin.class);

                // single activity te click krle ta navigation class e jaye single event dekhabe

                in.putExtra("For_single_frag","Single_donation_info_creator");

                // in.putExtra("Main_activity","log");
                in.putExtra("post_key",post_key);

                // Toast.makeText(getActivity(),post_key,Toast.LENGTH_SHORT).show();

                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


                startActivity(in);


            }
        });


        return v;
    }

    private void Update_Event(String post_key) {

        DatabaseReference data_edit_1=FirebaseDatabase.getInstance().getReference().child("Event").child(post_key);
        DatabaseReference data_edit_2=FirebaseDatabase.getInstance().getReference().child("Event_Total").child(post_key);

        nm=event_name.getText().toString().trim();
        String pl=event_place.getText().toString().trim();
        String des=event_description.getText().toString().trim();

        data_edit_1.child("Title").setValue(nm);
        data_edit_1.child("Place").setValue(pl);
        data_edit_1.child("Description").setValue(des);

        data_edit_2.child("Title").setValue(nm);
        data_edit_2.child("Place").setValue(pl);
        data_edit_2.child("Description").setValue(des);

        dbq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snap: dataSnapshot.getChildren()) {

                    String k=snap.getKey().toString();

                  //  Toast.makeText(getActivity(),k,Toast.LENGTH_SHORT).show();

                    snap.getRef().child("Event_Name").setValue(nm);

                  // Toast.makeText(getActivity(),key,Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Toast.makeText(getActivity(), " Updated!", Toast.LENGTH_SHORT).show();
    }




}
