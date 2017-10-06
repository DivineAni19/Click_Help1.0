package com.Help.Helping_1.click_help_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class After_LogIn extends Fragment {



    //Button no_use;

    String Main_act;

    private RecyclerView activity_list;

    DatabaseReference eventdbreference;

    private LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_after__log_in, container, false);

       // no_use=(Button)v.findViewById(R.id.no_use);

        activity_list=(RecyclerView)v.findViewById(R.id.activity_list);

        activity_list.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        activity_list.setLayoutManager(mLayoutManager);

       // activity_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        eventdbreference= FirebaseDatabase.getInstance().getReference().child("Event");

     /*   Toast t=Toast.makeText(getActivity(),"Click here to donate",Toast.LENGTH_LONG);

        t.setGravity(Gravity.CENTER, 0, 0);

        t.show();*/



        //*** set the flags

       /* Bundle bundle=getArguments();

        Main_act=bundle.getString("Main_activity");

        Toast.makeText(getActivity(),Main_act,Toast.LENGTH_LONG).show();*/


     /*  no_use.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {



               // single activity dekhar jonno click kra


               Intent in=new Intent(getActivity().getApplicationContext(),Navigation.class);
               in.putExtra("For_single_frag","single");
               in.putExtra("For_just_activity","before_login_activity");
               in.putExtra("Main_activity",Main_act);



               startActivity(in);


           }
       });*/








        return v;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Event_Class,EventViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Event_Class, EventViewHolder>(

                Event_Class.class,
                R.layout.activity_row,
                EventViewHolder.class,
                eventdbreference

        ) {
            @Override
            protected void populateViewHolder(EventViewHolder viewHolder, Event_Class model, int position) {

               //** it returns the whole URL
                // final String post_key=getRef(position).toString();

                //** it returns only the key
                final String post_key=getRef(position).getKey();




                viewHolder.setTitle(model.getTitle());
                viewHolder.setDate(model.getDate());
                viewHolder.setPlace(model.getPlace());
                viewHolder.setImage(getActivity(),model.getImage());

                viewHolder.mview.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        Intent in=new Intent(getActivity().getApplicationContext(),Navigation.class);

                        // single activity te click krle ta navigation class e jaye single event dekhabe

                        in.putExtra("For_single_frag","single");

                       // in.putExtra("Main_activity","log");
                        in.putExtra("post_key",post_key);

                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                       // Toast.makeText(getActivity(),post_key,Toast.LENGTH_SHORT).show();


                        startActivity(in);

                    }
                });


            }
        };

        activity_list.setAdapter(firebaseRecyclerAdapter);


    }


    public static class EventViewHolder extends RecyclerView.ViewHolder {

        View mview;

        public EventViewHolder(View itemView) {
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

        public void setImage(Context ctx,String image)
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

}
