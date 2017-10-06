package com.Help.Helping_1.click_help_1;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class After_Activity_Single extends AppCompatActivity {

    private TextView activity_name,description,event_place,Place_un,About_un;
    private Button interested;

    private DatabaseReference database_single;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after___single);


        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent j=getIntent();


        final String post_key=j.getStringExtra("post_key");

        database_single=FirebaseDatabase.getInstance().getReference().child("Event_Total");





        activity_name=(TextView)findViewById(R.id.aactivity_name);
        description=(TextView)findViewById(R.id.ddescription);
        event_place=(TextView)findViewById(R.id.eevent_place);


        Place_un=(TextView)findViewById(R.id.Place_un);
        About_un=(TextView)findViewById(R.id.about_un);


        Place_un.setPaintFlags(Place_un.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        About_un.setPaintFlags(About_un.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);





        interested=(Button)findViewById(R.id.iinterested);



        // For button visibility set up





        Toast.makeText(this,post_key,Toast.LENGTH_SHORT).show();



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



            interested.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // if donate krte interested hoi the tahole login page e redirect

                    Intent intent = new Intent(After_Activity_Single.this, LogIn.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }
            });









    }

    boolean twice;

    @Override
    public void onBackPressed() {





        if(twice==true)
        {
            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }


        Toast.makeText(After_Activity_Single.this,"Please press back again to exit",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                twice=false;

            }
        },4000);

        twice=true;


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
