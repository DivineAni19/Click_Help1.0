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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class LogIn extends AppCompatActivity  {

    EditText email_login,password_login;
    Button login_login;
    Button need_signup,login_forgot;

    private Button google_signin;

    private FirebaseAuth mauth;

    private DatabaseReference mdb;

    private ProgressDialog pr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mauth=FirebaseAuth.getInstance();
        mdb= FirebaseDatabase.getInstance().getReference().child("User");


        email_login=(EditText)findViewById(R.id.email_login);
        password_login=(EditText)findViewById(R.id.password_login);

        login_login=(Button)findViewById(R.id.login_login);


        pr=new ProgressDialog(LogIn.this);

        Intent j=getIntent();

        final String for_single=j.getStringExtra("Main_activity");






        login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                check_login();

               /* Intent intent=new Intent(LogIn.this,Navigation.class);

                intent.putExtra("For_single_frag","log");
                intent.putExtra("Main_activity","log");

                 startActivity(intent);*/

            }
        });



        need_signup=(Button)findViewById(R.id.need_signup);

        need_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LogIn.this,SignUp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
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



    private void check_login() {

        String em=email_login.getText().toString().trim();
        String pass=password_login.getText().toString().trim();




        if(!TextUtils.isEmpty(em) && !TextUtils.isEmpty(pass))
        {

            pr.setMessage("Logging In...");
            pr.show();


            mauth.signInWithEmailAndPassword(em,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())
                    {
                       check_user_exist();
                        pr.dismiss();
                        String current_user_id=mauth.getCurrentUser().getUid();
                        String token_id= FirebaseInstanceId.getInstance().getToken();
                        mdb.child(current_user_id).child("device_token").setValue(token_id).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    pr.dismiss();

                    Toast.makeText(LogIn.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void check_user_exist() {


        if(mauth.getCurrentUser()!=null) {



            final String uid = mauth.getCurrentUser().getUid();

            mdb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChild(uid)) {


                        Intent intent = new Intent(LogIn.this, Navigation.class);

                        intent.putExtra("For_single_frag", "log");
                        intent.putExtra("Main_activity", "log");

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);
                        finish();
                    } else {


                        Toast.makeText(LogIn.this, "U need to sign up", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
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


        Toast.makeText(LogIn.this,"Please press back again to exit",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                twice=false;

            }
        },4000);

        twice=true;


    }


}
