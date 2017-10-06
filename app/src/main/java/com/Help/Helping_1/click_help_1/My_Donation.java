package com.Help.Helping_1.click_help_1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class My_Donation extends Fragment {

   private ListView listviewdonation;

    private My_Donation_List donation_list;

    private List<Donation_Mine_Class> donationlist;

    private FragmentActivity con;

   private DatabaseReference mydonation;

   private Query mydon;

   private FirebaseAuth mauth;

    private int sum;

    private TextView t;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my__donation, container, false);

        listviewdonation=(ListView)v.findViewById(R.id.listview_my_donation);

        con=getActivity();

        mauth=FirebaseAuth.getInstance();

        String key=mauth.getCurrentUser().getUid();

        donationlist=new ArrayList<>();

        mydonation= FirebaseDatabase.getInstance().getReference().child("Donation");

        mydon=mydonation.orderByChild("Donor").equalTo(key);

        t=(TextView)v.findViewById(R.id.total_dn);

        sum=0;

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        mydon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                donationlist.clear();

                for(DataSnapshot snap: dataSnapshot.getChildren())
                {
                        String evnm= snap.child("Event_Name").getValue().toString();
                        String tk_=snap.child("Donation_Amount").getValue().toString();
                        String type_payment=snap.child("Paid_By").getValue().toString();
                        String pay_date=snap.child("Donate_Date").getValue().toString();

                    Donation_Mine_Class donation_mine_class=new Donation_Mine_Class(evnm,pay_date,tk_,type_payment);

                    sum=sum+Integer.parseInt(tk_);

                    donationlist.add(donation_mine_class);



                }

                Collections.reverse(donationlist);

                donation_list=new My_Donation_List(con,donationlist);

                listviewdonation.setAdapter(donation_list);

                t.setText("Total Donation: "+String.valueOf(sum)+" Tk");



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
