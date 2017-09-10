package com.example.marty.concatenar;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class concat extends AppCompatActivity {

    EditText name;
    EditText last;
    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            onPause();
            last = (EditText)findViewById(R.id.apellidos);
            name = (EditText)findViewById(R.id.nombres);
            if (mAccel > 30) {
                name.setText("");
                last.setText("");
            }
            onResume();
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_concat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        last = (EditText)findViewById(R.id.apellidos);
        name = (EditText)findViewById(R.id.nombres);

            //noinspection SimplifiableIfStatement
            switch (id) {
            case R.id.CNA:
                if (name.getText().length() > 0 && last.getText().length() > 0) {
                    Toast.makeText(this, name.getText().toString() + " " + last.getText().toString(), Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(this, "Favor de introducir ambos campos.", Toast.LENGTH_SHORT).show();
                }
            break;
            case R.id.CAN:
                if (name.getText().length() > 0 && last.getText().length() > 0) {
                    Toast.makeText(this, last.getText().toString() + " " + name.getText().toString(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Favor de introducir ambos campos.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.borrar:
                name.setText("");
                last.setText("");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
