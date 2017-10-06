package com.Help.Helping_1.click_help_1;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Single_Fragment extends Fragment {

    private TextView activity_name,description,event_place,mp,place_ppp,desc_ppp;
    private Button donate;

    private DatabaseReference database_single;

    private String plc;





    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_single_, container, false);

        Bundle bundle =new Bundle();
        bundle= this.getArguments();

        mp=(TextView) v.findViewById(R.id.mp);

        String visibility=bundle.getString("visible");
        final  String post_key=bundle.getString("post_key");


        database_single=FirebaseDatabase.getInstance().getReference().child("Event");


        activity_name=(TextView)v.findViewById(R.id.activity_name);
        description=(TextView)v.findViewById(R.id.description);
        event_place=(TextView)v.findViewById(R.id.event_place);
        place_ppp=(TextView)v.findViewById(R.id.Place_ppp);
        desc_ppp=(TextView)v.findViewById(R.id.desc_ppp);

        place_ppp.setPaintFlags(place_ppp.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        desc_ppp.setPaintFlags(desc_ppp.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);







        donate=(Button)v.findViewById(R.id.donate);




        // For button visibility set up





        Toast.makeText(getActivity(),post_key,Toast.LENGTH_SHORT).show();



        //Add value Event listner for single post

        if(!TextUtils.isEmpty(post_key))
        {

            Log.i("IIIIIIIIIIIII",post_key);
            database_single.child(post_key).addValueEventListener(new ValueEventListener() {



              @Override
             public void onDataChange(DataSnapshot dataSnapshot) {

                  Log.i("IIIIIIIIIIIIINNNNN",post_key);

                  String name=dataSnapshot.child("Title").getValue().toString();
                  String desc=dataSnapshot.child("Description").getValue().toString();
                  String place=dataSnapshot.child("Place").getValue().toString();



                  Log.i("IIIIIIIIIIIIINNNNNERRRR",name);
                  Log.i("IIIIIIIIIIIIINNNNNERRRR",desc);
                  Log.i("IIIIIIIIIIIIINNNNNERRRR",place);

                  activity_name.setText(name);
                  description.setText(desc);
                  event_place.setText(place);

                 }

                   @Override
                 public void onCancelled(DatabaseError databaseError) {

                  }});




           mp.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   database_single.child(post_key).addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {

                            plc=dataSnapshot.child("Place").getValue().toString();

                         //  Toast.makeText(getActivity(),plc,Toast.LENGTH_SHORT).show();

                           Intent in=new Intent(getActivity(),MapsActivity.class);
                           in.putExtra("Place",plc);
                           startActivity(in);

                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   });



               }
           });

          // Toast.makeText(getActivity(),pl.toString(),Toast.LENGTH_SHORT).show();



        }





       /* database_single.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                activity_name.setText(dataSnapshot.child("Title").getValue().toString());
                description.setText(dataSnapshot.child("Descruption").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/





            // button visibility set krte loin tekhe asle (donate button) ar activity tekhe asle (interest button)



            donate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), Donate.class);
                    intent.putExtra("Post_Key",post_key);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                }
            });

            Toast.makeText(getActivity(),visibility,Toast.LENGTH_SHORT).show();



        return  v;


    }



}
