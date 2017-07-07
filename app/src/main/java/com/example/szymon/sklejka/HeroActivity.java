package com.example.szymon.sklejka;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HeroActivity extends Activity {

    String name,level;
    float lux;
    int lives,drake_hp,drake_lvl;
    TextView textView_name,textView_level;
    ImageView heart1,heart2,heart3;
    SensorManager sensorManager;
    Sensor light_sensor;
    Button dragon1,dragon2,dragon3,dragon4,sleep,exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        Bundle extras = getIntent().getExtras();
        if(extras==null) {name="Brak"; level="20";} else {name=extras.getString("last_name"); level=extras.getString("level");}
        lives=2;
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightsensorlistner();

        heart1 = (ImageView)findViewById(R.id.Heart1);
        heart2 = (ImageView)findViewById(R.id.Heart2);
        heart3 = (ImageView)findViewById(R.id.Heart3);

        dragon1 = (Button)findViewById(R.id.button_heroact_dragon1);
        dragon2 = (Button)findViewById(R.id.button_heroact_dragon2);
        dragon3 = (Button)findViewById(R.id.button_heroact_dragon3);
        dragon4 = (Button)findViewById(R.id.button_heroact_vilen);
        sleep = (Button)findViewById(R.id.button_heroact_sleep);
        exit = (Button)findViewById(R.id.button_heroact_exit);

        textView_name = (TextView)findViewById(R.id.textView_heroact_name);
        textView_level = (TextView)findViewById(R.id.textView_heroact_level);

        //Toast.makeText(HeroActivity.this,"Name : "+name,Toast.LENGTH_LONG).show();
        textView_name.setText("Name : "+name);
        textView_level.setText("Level : "+level);

        displayhearts();
        drake_listner();
        sleep_listner();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                int lvl = Integer.parseInt(level);
                lvl++;
                level = String.valueOf(lvl);
                Intent intent = getIntent();
                intent.putExtra("level",level);
                setResult(RESULT_OK,intent);*/

                finish();
            }
        });
    }

    public void lightsensorlistner() {
        SensorEventListener luxmeter = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                lux=sensorEvent.values[0];
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(luxmeter,light_sensor,SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void drake_listner() {

        dragon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lives==0)
                {
                    Toast.makeText(HeroActivity.this,"You dont have any chances in battle without any heart. Restore some",Toast.LENGTH_LONG).show();
                }
                else
                {
                    drake_lvl=1;
                    drake_hp=10;
                    Intent i = new Intent(HeroActivity.this,BattleActivity.class);
                    i.putExtra("level",level);
                    i.putExtra("drake_lvl",drake_lvl);
                    i.putExtra("drake_hp",drake_hp);
                    startActivity(i);
                }
            }
        });

        dragon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lives==0)
                {
                    Toast.makeText(HeroActivity.this,"You dont have any chances in battle without any heart. Restore some",Toast.LENGTH_LONG).show();
                }
                else
                {
                    drake_lvl=2;
                    drake_hp=30;
                    Intent i = new Intent(HeroActivity.this,BattleActivity.class);
                    i.putExtra("level",level);
                    i.putExtra("drake_lvl",drake_lvl);
                    i.putExtra("drake_hp",drake_hp);
                    startActivity(i);
                }
            }
        });
        dragon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lives==0)
                {
                    Toast.makeText(HeroActivity.this,"You dont have any chances in battle without any heart. Restore some",Toast.LENGTH_LONG).show();
                }
                else
                {
                    drake_lvl=3;
                    drake_hp=500;
                    Intent i = new Intent(HeroActivity.this,BattleActivity.class);
                    i.putExtra("level",level);
                    i.putExtra("drake_lvl",drake_lvl);
                    i.putExtra("drake_hp",drake_hp);
                    startActivity(i);
                }
            }
        });
        dragon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lives==0)
                {
                    Toast.makeText(HeroActivity.this,"You dont have any chances in battle without any heart. Restore some",Toast.LENGTH_LONG).show();
                }
                else
                {
                    drake_lvl=10;
                    drake_hp=10000;
                    Intent i = new Intent(HeroActivity.this,BattleActivity.class);
                    i.putExtra("level",level);
                    i.putExtra("drake_lvl",drake_lvl);
                    i.putExtra("drake_hp",drake_hp);
                    startActivity(i);
                }
            }
        });

    }

    public void sleep_listner(){
        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lives == 3)
                {
                    Toast.makeText(HeroActivity.this,"U cant have more hearts",Toast.LENGTH_LONG).show();
                }
                else
                if(lux>50)
                {
                    Toast.makeText(HeroActivity.this,"There is too much light for a sleep. Current light : "+lux,Toast.LENGTH_LONG).show();
                }
                else
                {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(HeroActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.activity_sleep,null);
                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    final TextView mText = (TextView)mView.findViewById(R.id.textView_sleep);
                    final Button mButton = (Button) mView.findViewById(R.id.alertDialog_sleep);
                    CountDownTimer ctd = new CountDownTimer(10*1000,1000) {
                        @Override
                        public void onTick(long l) {
                            mText.setText(""+l/1000);
                        }

                        @Override
                        public void onFinish() {
                            mText.setText("Time is up.Press Button and Collect Heart");
                            mButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    lives++;
                                    displayhearts();
                                    dialog.dismiss();
                                }
                            });
                        }
                    };
                    ctd.start();

                }
            }
        });
    }

    public void displayhearts() {
        if(lives==0)
        {
            heart1.setVisibility(View.INVISIBLE);
            heart2.setVisibility(View.INVISIBLE);
            heart3.setVisibility(View.INVISIBLE);
        }
        if(lives == 1)
        {
            heart1.setVisibility(View.VISIBLE);
            heart2.setVisibility(View.INVISIBLE);
            heart3.setVisibility(View.INVISIBLE);
        }
        if(lives == 2)
        {
            heart1.setVisibility(View.VISIBLE);
            heart2.setVisibility(View.VISIBLE);
            heart3.setVisibility(View.INVISIBLE);
        }
        if(lives == 3)
        {
            heart1.setVisibility(View.VISIBLE);
            heart2.setVisibility(View.VISIBLE);
            heart3.setVisibility(View.VISIBLE);
        }
    }
}
