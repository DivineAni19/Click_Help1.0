package com.Help.Helping_1.click_help_1;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class My_Account extends Fragment {

    private FirebaseAuth mauth;
    private DatabaseReference databaseReference;

    private EditText acc_name,acc_city,acc_area,acc_login_pass;

    private Button acc_save,acc_edit,acc_edit_pass,acc_save_pass;
    private TextView my_mail,my;

    String n,c,a;

    String uid;

    String mail_add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my__account, container, false);


        acc_name=(EditText)v.findViewById(R.id.acc_name);
        acc_area=(EditText)v.findViewById(R.id.acc_area);
        acc_city=(EditText)v.findViewById(R.id.acc_city);
        acc_login_pass=(EditText)v.findViewById(R.id.acc_login_pass);

        acc_save=(Button)v.findViewById(R.id.acc_save);
        acc_edit=(Button)v.findViewById(R.id.acc_edit);

        acc_edit_pass=(Button)v.findViewById(R.id.acc_edit_pass);
        acc_save_pass=(Button)v.findViewById(R.id.acc_save_pass);

        my_mail=(TextView)v.findViewById(R.id.my_mail);
        my=(TextView)v.findViewById(R.id.my);


        my.setPaintFlags(my.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);


        acc_edit.setVisibility(View.VISIBLE);
        acc_save.setVisibility(View.GONE);

        acc_edit_pass.setVisibility(View.VISIBLE);
        acc_save_pass.setVisibility(View.GONE);




        mauth=FirebaseAuth.getInstance();



        uid=mauth.getCurrentUser().getUid();

        mail_add=mauth.getCurrentUser().getEmail();



        Toast.makeText(getActivity(),mail_add,Toast.LENGTH_SHORT).show();

        my_mail.setText(mail_add);



        databaseReference= FirebaseDatabase.getInstance().getReference().child("User").child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                n=dataSnapshot.child("Name").getValue().toString();
                a=dataSnapshot.child("Area").getValue().toString();
                c=dataSnapshot.child("City").getValue().toString();

                acc_name.setText(n);
                acc_area.setText(a);
                acc_city.setText(c);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        acc_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                acc_edit.setVisibility(View.GONE);
                acc_save.setVisibility(View.VISIBLE);

                acc_name.setEnabled(true);
                acc_area.setEnabled(true);
                acc_city.setEnabled(true);

            }
        });


        acc_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                acc_name.setEnabled(false);
                acc_area.setEnabled(false);
                acc_city.setEnabled(false);

                acc_save.setVisibility(View.GONE);

                acc_edit.setVisibility(View.VISIBLE);




                update_acc(uid);

            }
        });


        acc_edit_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                acc_edit_pass.setVisibility(View.GONE);
                acc_save_pass.setVisibility(View.VISIBLE);

                acc_login_pass.setEnabled(true);

            }
        });


        acc_save_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                acc_save_pass.setVisibility(View.GONE);
                acc_edit_pass.setVisibility(View.VISIBLE);

                String pass=acc_login_pass.getText().toString();
                acc_login_pass.setEnabled(false);

                if(TextUtils.isEmpty(pass))
                {
                    Toast.makeText(getActivity(),"Please Enter a valid Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pass.length()<6)
                {
                    Toast.makeText(getActivity(),"Please Enter  Password with 6 character",Toast.LENGTH_SHORT).show();
                    return;
                }
                
                Update_Password(pass);

            }
        });


        return v;
    }

    private void Update_Password(String pass) {

        FirebaseUser user=mauth.getCurrentUser();

        user.updatePassword(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(getActivity(),"Password Changed Successfully!!",Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getActivity(),"Sorry!!!"+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void update_acc(String uid) {

        DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("User").child(uid);

        String nm=acc_name.getText().toString();
        String ar=acc_area.getText().toString();
        String ct=acc_city.getText().toString();

        db.child("Name").setValue(nm);
        db.child("Area").setValue(ar);
        db.child("City").setValue(ct);

        Toast.makeText(getActivity(), " Updated Account Successfully!", Toast.LENGTH_SHORT).show();
    }


}
