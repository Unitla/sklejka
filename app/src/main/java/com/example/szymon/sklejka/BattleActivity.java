package com.example.szymon.sklejka;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BattleActivity extends AppCompatActivity {

    ImageView imageview_bottle;
    TextView timer;
    Button button_spin;
    SensorManager sensorManager;
    MediaPlayer MPlayer;
    Sensor sensor;
    int angle=45;
    int last_angle=0;
    int points=0;
    int last=0;
    int drake_lvl,drake_hp,level;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle2);

        /*Bundle extras = getIntent().getExtras();
        if(extras==null) {} else {drake_lvl=extras.getInt("drake_lvl"); level=Integer.getInteger(extras.getString("level"));
        drake_hp=extras.getInt("drake_hp");
        }*/
        imageview_bottle = (ImageView)findViewById(R.id.imageView_paladin);
        timer = (TextView)findViewById(R.id.textView_battle);
        MPlayer = MediaPlayer.create(this,R.raw.bell);
        //button_spin = (Button)findViewById(R.id.button);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener accelListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                if(x>6.00)
                {
                    if(last<=0)
                    {
                        last=1;
                        points++;
                        spin();
                    }
                }
                else
                if(x<-6.00)
                {
                    if(last>=0)
                    {
                        last=-1;
                        points++;
                        spin();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(accelListener, sensor,SensorManager.SENSOR_DELAY_NORMAL);
        timer.setText("15");
        timer_start();
        countDownTimer.start();

    }

    public void timer_start() {
        countDownTimer = new CountDownTimer(15*1000,1000) {
            @Override
            public void onTick(long l) {
                //System.out.println("Wywolanie");
                timer.setText(""+l/1000);
            }

            @Override
            public void onFinish() {
                timer.setText("Time is up");
                MPlayer.start();
                //Toast.makeText(BattleActivity.this,"You deal ",Toast.LENGTH_LONG).show();
                int dmg = points * level;
                System.out.println("Dmg "+dmg);
                if(dmg>Singleton.getInstance().drake_hp)
                {
                    Toast.makeText(BattleActivity.this,"You Won. Damage delt "+dmg+" Drake Hp "+drake_hp,Toast.LENGTH_LONG).show();
                    //Singleton.getInstance().battle_result=true;
                }
                else
                {
                    Toast.makeText(BattleActivity.this,"You Lost. Damage delt "+dmg+" Drake Hp "+drake_hp,Toast.LENGTH_LONG).show();
                    //Singleton.getInstance().battle_result=false;
                }
            }
        };
    }

    public void spin() {
        //final int last_angle;
        if(angle == 45)
        {
            angle=315;
            last_angle=45;
        }
        else {
            angle = 45;
            last_angle=315;
        }
        //System.out.println(180-angle);
        //angle=180-angle;
        float pivotX = imageview_bottle.getWidth()/2;
        float pivotY = imageview_bottle.getHeight()/2;
        final Animation animation = new RotateAnimation(last_angle,angle,pivotX,pivotY);
        animation.setDuration(5);
        animation.setFillAfter(true);
        imageview_bottle.startAnimation(animation);
        last_angle=angle;
        //Toast.makeText(MainActivity.this,"Dziala klawisz",Toast.LENGTH_LONG).show();
    };
}
