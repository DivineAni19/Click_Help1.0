package com.Help.Helping_1.click_help_1;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Donation_Info_Admin extends AppCompatActivity {

    private DatabaseReference db_events;

    private Query dbquery;

    ListView listviewdonation;

    Donation_List donation_list;

    List<Donation> donationlist;

    private TextView event_name,total;

    String n;

    private DatabaseReference dbt;

    FragmentActivity con;

    private int sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation__info__admin);

        listviewdonation=(ListView)findViewById(R.id.listviewdonationinfo);

        event_name=(TextView)findViewById(R.id.eve_name);
        total=(TextView)findViewById(R.id.total);



        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



       Intent j=getIntent();



        sum=0;


        final  String post_key=j.getStringExtra("post_key");

        Toast.makeText(this,post_key,Toast.LENGTH_LONG).show();

        donationlist=new ArrayList<>();


        db_events= FirebaseDatabase.getInstance().getReference().child("Donation");

        dbquery=db_events.orderByChild("Event").equalTo(post_key);


        dbt=FirebaseDatabase.getInstance().getReference().child("Event").child(post_key);

        dbt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                n=dataSnapshot.child("Title").getValue().toString();

                event_name.setText(n);

                // Toast.makeText(con,n,Toast.LENGTH_SHORT).show();




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Toast.makeText(getActivity(),n,Toast.LENGTH_SHORT).show();








    }



    @Override
    public void onStart() {
        super.onStart();

        dbquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                donationlist.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    String nm=snapshot.child("Event_Name").getValue().toString();

                    String donor_nm=snapshot.child("Donor_Name").getValue().toString();

                    String donor_add=snapshot.child("Donor_Address").getValue().toString();

                    String donor_amount=snapshot.child("Donation_Amount").getValue().toString();

                    String donor_date=snapshot.child("Donate_Date").getValue().toString();


                    sum=sum+Integer.parseInt(donor_amount);


                    Donation ddonation=new Donation(nm,donor_nm,donor_add,donor_amount,donor_date);

                    donationlist.add(ddonation);

                    //Log.i("Donoooooooooorrrr",donor_nm+"  "+donor_place);

                }


                Collections.reverse(donationlist);

                donation_list=new Donation_List(Donation_Info_Admin.this,donationlist);

                listviewdonation.setAdapter(donation_list);


                total.setText("Total Collection: "+String.valueOf(sum)+" Tk");

                //Toast.makeText(con,String.valueOf(sum),Toast.LENGTH_LONG).show();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            finish();

           /* Intent intent=new Intent(Donate.this,Navigation.class);
            intent.putExtra("For_single_frag","From_Donate");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);*/
        }

        return super.onOptionsItemSelected(item);
    }

}
