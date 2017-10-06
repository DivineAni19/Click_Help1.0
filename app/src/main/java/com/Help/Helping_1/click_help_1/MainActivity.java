package com.Help.Helping_1.click_help_1;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Button login,signup,activity;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mauthStateListener;


    private SensorManager sensorManager;
    private Sensor sensor;
    //private Button btn;

    float lastx, lasty, lastz;
    long  lastTime=0;
    double SHAKE_THRESHOLD = 1100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=(Button)findViewById(R.id.login);
        signup=(Button)findViewById(R.id.signup);
        activity=(Button)findViewById(R.id.activity);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener( this,sensor,SensorManager.SENSOR_DELAY_NORMAL);






        mAuth=FirebaseAuth.getInstance();
        mauthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(mAuth.getCurrentUser()!=null)
                {
                    Intent intent=new Intent(MainActivity.this, Navigation.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    intent.putExtra("For_single_frag","log");
                    intent.putExtra("Main_activity","log");

                    startActivity(intent);
                }
                else
                {

                }

            }
        };


        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(MainActivity.this, After_Activity_Buttonn.class);

                intent.putExtra("For_Signle_Activity","total_activity");
                intent.putExtra("Main_activity","activity");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SignUp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,LogIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Main_activity","log");
                startActivity(intent);

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mauthStateListener);




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


        Toast.makeText(MainActivity.this,"Please press back again to exit",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                twice=false;

            }
        },10000);

        twice=true;


    }




    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER  ) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            long curTime = System.currentTimeMillis();


            if ((curTime - lastTime) > 100) {
                long diffTime = (curTime - lastTime);

                lastTime = curTime;
                double speed = Math.abs(x - lastx + y - lasty + z - lastx) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {

                  Toast.makeText(MainActivity.this,"Please do not Shake",Toast.LENGTH_SHORT).show();

                }

                lastx = x;
                lasty = y;
                lastz = z;

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        sensorManager.registerListener( this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener( this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
