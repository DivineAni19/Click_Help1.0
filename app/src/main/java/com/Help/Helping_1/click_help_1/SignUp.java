package com.Help.Helping_1.click_help_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    EditText name_signup,email_signup,password_signup,area_signup,city_signup;

    Button signup_signup;

    private FirebaseAuth mauth;

    private DatabaseReference mdb;

    private ProgressDialog prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        name_signup=(EditText)findViewById(R.id.name_signup);
        email_signup=(EditText)findViewById(R.id.email_signup);
        password_signup=(EditText)findViewById(R.id.password_signup);
        area_signup=(EditText)findViewById(R.id.area_signup);
        city_signup=(EditText)findViewById(R.id.city_signup);

        signup_signup=(Button)findViewById(R.id.signup_signup);

        mauth=FirebaseAuth.getInstance();
        mdb= FirebaseDatabase.getInstance().getReference().child("User");

        prog=new ProgressDialog(this);


        signup_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register_user();
            }
        });


       /* signup_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(SignUp.this,LogIn.class);

                startActivity(intent);
            }
        });*/
    }

    private void register_user() {

        final String nm=name_signup.getText().toString().trim();
        final String ar=area_signup.getText().toString().trim();
        final String ct=city_signup.getText().toString().trim();
        String em=email_signup.getText().toString().trim();
        String pass=password_signup.getText().toString().trim();


        if(!TextUtils.isEmpty(nm) && !TextUtils.isEmpty(ar) && !TextUtils.isEmpty(ct) && !TextUtils.isEmpty(em) && !TextUtils.isEmpty(pass) && String.valueOf(pass).length()>=6)
        {
            prog.setMessage("Creating User...");
            prog.show();

           mauth.createUserWithEmailAndPassword(em,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {

                   if(task.isSuccessful())
                   {
                       prog.dismiss();

                       String uid=mauth.getCurrentUser().getUid();
                       String current_user_id=mauth.getCurrentUser().getUid();
                       String token_id= FirebaseInstanceId.getInstance().getToken();


                       HashMap<String,String> usr=new HashMap<String, String>();

                       usr.put("Name",nm);
                       usr.put("Area",ar);
                       usr.put("City",ct);
                       usr.put("device_token",token_id);
                       usr.put("Admin","0");

                       mdb.child(uid).setValue(usr);

                       Toast.makeText(SignUp.this,"Successfully Registered",Toast.LENGTH_SHORT).show();

                       Intent intent=new Intent(SignUp.this,LogIn.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       startActivity(intent);
                   }

               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {

                   Toast.makeText(SignUp.this,e.getMessage(),Toast.LENGTH_SHORT).show();

               }
           });
        }




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


        Toast.makeText(SignUp.this,"Please press back again to exit",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                twice=false;

            }
        },4000);

        twice=true;


    }
}
