package com.Help.Helping_1.click_help_1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mauth;

    private FirebaseAuth.AuthStateListener mauthStateListener;

    private DatabaseReference admin_user;

    private Intent j;

    private String check_admin;

    private TextView acc_mail_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);




        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();


        mauth=FirebaseAuth.getInstance();



        mauthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

               if(firebaseAuth.getCurrentUser()==null)
               {
                   Intent intent=new Intent(Navigation.this, MainActivity.class);

                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                   startActivity(intent);
               }

            }
        };




        String mail=mauth.getCurrentUser().getEmail();

        Toast.makeText(this,mail,Toast.LENGTH_SHORT).show();

      //  acc_mail_save.setText(mail);




        admin_user= FirebaseDatabase.getInstance().getReference().child("User");


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);

        acc_mail_save=(TextView)header.findViewById(R.id.acc_nav_mine);

        acc_mail_save.setText(mail);




        String key11=mauth.getCurrentUser().getUid();

        final Menu menu = navigationView.getMenu();


        admin_user.child(key11).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                check_admin=dataSnapshot.child("Admin").getValue().toString();

                if(check_admin.equals("1"))
                {

                    for (int menuItemIndex = 0; menuItemIndex < menu.size(); menuItemIndex++) {
                        MenuItem menuItem= menu.getItem(menuItemIndex);
                        if(menuItem.getItemId() == R.id.nav_mycreation){
                            menuItem.setVisible(true);
                        }
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ActionBar actionBar=getSupportActionBar();


        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_view_headline_white_24dp);


         j=getIntent();





        String key=j.getStringExtra("For_single_frag");//login tekhe asce "log" or single activity dekhte asce

        String post_Key=j.getStringExtra("post_key");





        // for button visibility
        String btn=j.getStringExtra("btn_visible");

       // After login fragmrntation j kokhon kon fragment change hosse



        if(key.equals("single"))
        {

            // Single activity te click krar por after login page e ase single activity dekhte



                Single_Fragment single_fragment = new Single_Fragment();

            FragmentManager fm = getSupportFragmentManager();

            FragmentTransaction ft = fm.beginTransaction();

            Bundle args = new Bundle();

            args.putString("visible", "log");


            args.putString("post_key",post_Key);


            single_fragment.setArguments(args);



            single_fragment.setArguments(args);


            ft.replace(R.id.mylayout, single_fragment);

          

            ft.commit();

        }
        else if(key.equals("log"))
        {

            // login krar por activity page navigation page e ase page change krte hobe all activity dekhte

            After_LogIn after_logIn=new After_LogIn();

            FragmentManager fm=getSupportFragmentManager();

            FragmentTransaction ft=fm.beginTransaction();

           // for button visiblity
            Bundle args = new Bundle();

            args.putString("Main_activity","log");


            after_logIn.setArguments(args);



            ft.replace(R.id.mylayout,after_logIn);



            ft.commit();
        }
        else if(key.equals("From_Donate"))
        {
            After_LogIn after_logIn=new After_LogIn();

            FragmentManager fm=getSupportFragmentManager();

            FragmentTransaction ft=fm.beginTransaction();

            // for button visiblity
            Bundle args = new Bundle();
            args.putString("Main_activity", "log");
            after_logIn.setArguments(args);



            ft.replace(R.id.mylayout,after_logIn);



            ft.commit();
        }
        else if(key.equals("single_for_creator"))
        {
           Single_Fragment_Admin single_fragment_admin=new Single_Fragment_Admin();

            FragmentManager fm = getSupportFragmentManager();

            FragmentTransaction ft = fm.beginTransaction();

            Bundle args = new Bundle();




            args.putString("post_key",post_Key);


            single_fragment_admin.setArguments(args);




            ft.replace(R.id.mylayout,single_fragment_admin);



            ft.commit();
        }



    }

    boolean twice;

    @Override
    public void onBackPressed() {




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }

    }








    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.navigation, menu);

       String key11=mauth.getCurrentUser().getUid();

        Menu item3 = menu;

        final MenuItem item=item3.findItem(R.id.action_add);


        admin_user.child(key11).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                check_admin=dataSnapshot.child("Admin").getValue().toString();

                if(check_admin.equals("1"))
                {
                    item.setVisible(true);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





           return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement



           if (id == R.id.action_add) {


               FragmentManager fm = getSupportFragmentManager();

               FragmentTransaction ft = fm.beginTransaction();


               ft.replace(R.id.mylayout, new New_Event());



               ft.commit();

               return true;
           }

        if(item.getItemId()==android.R.id.home)
        {

        }




        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        String key11=mauth.getCurrentUser().getUid();

       final  MenuItem men=item;



        if (id == R.id.nav_account) {


           My_Account my_account=new My_Account();

            FragmentManager fm = getSupportFragmentManager();

            FragmentTransaction ft = fm.beginTransaction();

            ft.replace(R.id.mylayout, my_account);



            ft.commit();


            } else if (id == R.id.nav_donate) {

                After_LogIn after_logIn=new After_LogIn();

                FragmentManager fm = getSupportFragmentManager();

                FragmentTransaction ft = fm.beginTransaction();

                Bundle args = new Bundle();
                args.putString("Main_activity", "log");
                after_logIn.setArguments(args);



                ft.replace(R.id.mylayout, after_logIn);



                ft.commit();

            }else if (id == R.id.nav_mydonation) {

                My_Donation my_donation=new My_Donation();

            FragmentManager fm = getSupportFragmentManager();

            FragmentTransaction ft = fm.beginTransaction();




            ft.replace(R.id.mylayout, my_donation);



            ft.commit();



            }
            else if (id == R.id.nav_mycreation) {

            My_Created_Events my_created_events=new My_Created_Events();

            FragmentManager fm = getSupportFragmentManager();

            FragmentTransaction ft = fm.beginTransaction();

            Bundle args = new Bundle();
            args.putString("Main_activity", "log");
            my_created_events.setArguments(args);

            ft.replace(R.id.mylayout, my_created_events);



            ft.commit();


           }
            else if (id == R.id.nav_logout) {

                mauth.signOut();

                /*Intent intent=new Intent(Navigation.this, LogIn.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);*/

            }






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();

        mauth.addAuthStateListener(mauthStateListener);


    }




}
