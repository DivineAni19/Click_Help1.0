package com.Help.Helping_1.click_help_1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Donate extends AppCompatActivity {

    EditText payment_amount,visa_number,mobile_number;

    Button confirm_donation;

    TextView activity_name_donate,none,t1,t2,t3;

    Spinner donation_type;

    String item;


    String Event_date;


    String Donor_key;
    String pay;

    String name;
    String amount;

    String whoo;
     String areaa;
     String cityy;
    String str;


    private DatabaseReference db;
    private DatabaseReference donate_data_event;
    private DatabaseReference donate_info_for_user;
    private FirebaseAuth auth;
    private DatabaseReference mnotificationdatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);




        db= FirebaseDatabase.getInstance().getReference().child("Event");
        donate_data_event= FirebaseDatabase.getInstance().getReference().child("Donation");
        mnotificationdatabase=FirebaseDatabase.getInstance().getReference().child("Notifications");

        //  donate_info_for_user= FirebaseDatabase.getInstance().getReference().child("Donate_Info_For_User");

        auth=FirebaseAuth.getInstance();



        Donor_key=auth.getCurrentUser().getUid();






        payment_amount=(EditText)findViewById(R.id.payment_amount);
        visa_number=(EditText)findViewById(R.id.visa_number);
        mobile_number=(EditText)findViewById(R.id.mobile_number);

        confirm_donation=(Button)findViewById(R.id.confirm_donation);

        activity_name_donate=(TextView)findViewById(R.id.activity_name_donate);
        none=(TextView)findViewById(R.id.none_none);
        t1=(TextView)findViewById(R.id.textView111);
        t2=(TextView)findViewById(R.id.textView222);
        t3=(TextView)findViewById(R.id.textView333);



        t1.setPaintFlags(t1.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        t2.setPaintFlags(t2.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        t3.setPaintFlags(t3.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        donation_type=(Spinner)findViewById(R.id.spinner);

        mobile_number.setVisibility(View.GONE);
        visa_number.setVisibility(View.GONE);


        Intent j=getIntent();

        //Selected Event Key

        final String Post_key=j.getStringExtra("Post_Key");


        Toast.makeText(getApplicationContext(),Post_key, Toast.LENGTH_LONG).show();



        DatabaseReference dbb=FirebaseDatabase.getInstance().getReference().child("User").child(Donor_key);

        dbb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String who=dataSnapshot.child("Name").getValue().toString();
                String area=dataSnapshot.child("Area").getValue().toString();
                String city=dataSnapshot.child("City").getValue().toString();


                whoo=who;
                areaa=area;
                cityy=city;






            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        donation_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                item = adapterView.getItemAtPosition(i).toString();

                if(item.equals("Bkash"))
                {
                    mobile_number.setVisibility(View.VISIBLE);
                    visa_number.setVisibility(View.GONE);
                    none.setVisibility(View.GONE);
                }
                else if(item.equals("Credit Card"))
                {
                    mobile_number.setVisibility(View.GONE);
                    visa_number.setVisibility(View.VISIBLE);
                    none.setVisibility(View.GONE);
                }
                else
                {
                    mobile_number.setVisibility(View.GONE);
                    visa_number.setVisibility(View.GONE);
                    none.setVisibility(View.VISIBLE);
                }

                // Showing selected spinner item
                Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        db.child(Post_key).addValueEventListener(new ValueEventListener() {



            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.i("IIIIIIIIIIIIINNNNN",Post_key);

                 name=dataSnapshot.child("Title").getValue().toString();
                 Event_date=dataSnapshot.child("Date").getValue().toString();
                str=dataSnapshot.child("Creator").getValue().toString();

                activity_name_donate.setText(name);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }});





        confirm_donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                amount=payment_amount.getText().toString();

                if(TextUtils.isEmpty(amount) && item.equals("None") )
                {
                    Toast.makeText(getApplicationContext(), "Please Select One Resource to donate and fill the amount u wanna donate" , Toast.LENGTH_LONG).show();
                    return;
                }


                if(item.equals("None"))
                {
                    Toast.makeText(getApplicationContext(), "Plzz Select One resource To Donate" , Toast.LENGTH_LONG).show();
                    return;
                }






               if(item.equals("Bkash"))
                {

                    pay=mobile_number.getText().toString();

                    if( TextUtils.isEmpty(pay)  && TextUtils.isEmpty(amount))
                    {
                        Toast.makeText(getApplicationContext(), "Plzz Enter a valid BKash Number && fill up the amount u want to donate" , Toast.LENGTH_LONG).show();
                        return;
                    }

                    if( pay.length()<11 && TextUtils.isEmpty(amount))
                    {
                        Toast.makeText(getApplicationContext(), "Plzz Enter a valid BKash Number && fill up the amount u want to donate" , Toast.LENGTH_LONG).show();
                        return;
                    }

                    if( TextUtils.isEmpty(pay) )
                    {
                        Toast.makeText(getApplicationContext(), "Plzz Enter a valid BKash Number" , Toast.LENGTH_LONG).show();
                        return;
                    }


                }

                if(item.equals("Credit Card"))
                {
                    pay=visa_number.getText().toString();

                    if( TextUtils.isEmpty(pay)  && TextUtils.isEmpty(amount))
                    {
                        Toast.makeText(getApplicationContext(), "Plzz Enter a valid Visa Number && fill up the amount u want to donate" , Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(TextUtils.isEmpty(pay))
                    {
                        Toast.makeText(getApplicationContext(), "Plzz Enter a valid Visa Number" , Toast.LENGTH_LONG).show();
                        return;
                    }
                }


                if(TextUtils.isEmpty(amount))
                {
                    Toast.makeText(getApplicationContext(), "Plzz Enter the amount u want to donate" , Toast.LENGTH_LONG).show();
                    return;
                }

                int am=Integer.valueOf(amount);

                if(am<100 || am>200000)
                {
                    Toast.makeText(getApplicationContext(), "Sorry!! Amount must be greater than 100tk and less then 200000tk" , Toast.LENGTH_LONG).show();
                    return;
                }

                if(!TextUtils.isEmpty(amount) && !TextUtils.isEmpty(pay))
                {
                    Toast.makeText(getApplicationContext(),"Pay Type: "+pay+" amount"+amount , Toast.LENGTH_LONG).show();


                    AlertDialog.Builder builder=new AlertDialog.Builder(Donate.this);

                    builder.setIcon(R.mipmap.ic_warning_black_24dp);

                    builder.setTitle("CONFIRMATION");

                    builder.setMessage("Are u sure u want to donate?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();

                            ProgressDialog p=new ProgressDialog(Donate.this);
                            p.setMessage("Donation Compleating...");
                            p.show();

                            //Date Get

                            Calendar c = Calendar.getInstance();
                            System.out.println("Current time => " + c.getTime());

                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                            String formattedDate = df.format(c.getTime());


                            //Database Entry && Notification Send

                            //nisha notification send

                            // For Admin



                             HashMap<String,String> hash=new HashMap<String, String>();
                            hash.put("Event",Post_key);



                            hash.put("Donor",Donor_key);

                            hash.put("Donor_Name",whoo);
                            hash.put("Donor_Address",areaa+","+cityy);


                            Toast.makeText(getApplicationContext(),whoo,Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),areaa,Toast.LENGTH_SHORT).show();


                            hash.put("Event_Name",name);
                            hash.put("Donate_Date",formattedDate);
                            hash.put("Paid_By",item);
                            hash.put("Donation_Mobile_Visa_Num",pay);
                            hash.put("Donation_Amount",amount);

                          //  hash.put("Address",areaa+","+cityy);




                            String id= donate_data_event.push().getKey();

                            donate_data_event.child(id).setValue(hash);



                            p.dismiss();

                            Toast.makeText(Donate.this,"Thanks For Your Donation",Toast.LENGTH_LONG).show();

                            Intent intent=new Intent(Donate.this,Navigation.class);
                            intent.putExtra("For_single_frag","From_Donate");
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);


                            HashMap<String,String> notificationdata=new HashMap<String,String>();
                            notificationdata.put("from",auth.getCurrentUser().getUid());
                            notificationdata.put("type","request");
                            notificationdata.put("Event_name",name);
                            notificationdata.put("Donation_Amount",amount);


                            mnotificationdatabase.child(str).push().setValue(notificationdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(getApplicationContext(),"Notification indeed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();

                            Toast.makeText(Donate.this,"Dismiss",Toast.LENGTH_LONG).show();

                        }
                    });

                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();


                }



            }
        });





        // back arrow enable

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    // back arrow enable

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
