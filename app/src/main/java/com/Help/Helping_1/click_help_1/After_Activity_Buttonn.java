package com.Help.Helping_1.click_help_1;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class After_Activity_Buttonn extends AppCompatActivity {

    // Button no_use;

    private RecyclerView activity_list_before;

    private DatabaseReference before_login_data;

    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after___buttonn);


        activity_list_before=(RecyclerView)findViewById(R.id.activity_list_before);

        activity_list_before.setHasFixedSize(true);


        mLayoutManager = new LinearLayoutManager(After_Activity_Buttonn.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        activity_list_before.setLayoutManager(mLayoutManager);


        before_login_data= FirebaseDatabase.getInstance().getReference().child("Event_Total");



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


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Event_Class,Event_View_Holder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Event_Class, Event_View_Holder>(

                Event_Class.class,
                R.layout.activity_row,
                Event_View_Holder.class,
                before_login_data


        ) {
            @Override
            protected void populateViewHolder(Event_View_Holder viewHolder, Event_Class model, int position) {

                final String post_key=getRef(position).getKey();

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDate(model.getDate());
                viewHolder.setPlace(model.getPlace());
                viewHolder.setImage(After_Activity_Buttonn.this,model.getImage());

                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent(After_Activity_Buttonn.this,After_Activity_Single.class);

                        intent.putExtra("For_Signle_Activity","before_login");
                        intent.putExtra("post_key",post_key);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);
                    }
                });



            }
        };

        activity_list_before.setAdapter(firebaseRecyclerAdapter);


    }

    public static class Event_View_Holder extends RecyclerView.ViewHolder
    {
        View mview;

        public Event_View_Holder(View itemView) {
            super(itemView);

            mview=itemView;
        }


        public void setTitle(String title)
        {
            TextView post_title=(TextView) mview.findViewById(R.id.activity_title);

            post_title.setText(title);
        }

        public void setDate(String date)
        {
            TextView post_date=(TextView) mview.findViewById(R.id.activity_date);

            post_date.setText(date);
        }

        public void setImage(Context ctx, String image)
        {
            ImageView post_image=(ImageView)mview.findViewById(R.id.activity_image);

            Picasso.with(ctx).load(image).into(post_image);
        }

        public void setPlace(String place)
        {
            TextView post_place=(TextView)mview.findViewById(R.id.activity_place);

            post_place.setText(place);
        }

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


        Toast.makeText(After_Activity_Buttonn.this,"Please press back again to exit",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                twice=false;

            }
        },4000);

        twice=true;


    }

}
