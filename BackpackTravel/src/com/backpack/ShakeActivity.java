package com.backpack;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class ShakeActivity extends Activity implements SensorEventListener{
    private static final String TAG = "ShakeActivity";


    /** Called when the activity is first created. */
    private SensorManager sensorManager;
    
    
    private final float[] mScale = new float[] { 2, 2.5f, 0.5f };   // accel
    private float[] mPrev = new float[3];
    private long mLastGestureTime;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(SensorManager.SENSOR_ORIENTATION),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }
    
    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {
        boolean show = false;
        float[] diff = new float[3];

        for (int i = 0; i < 3; i++) {
            diff[i] = Math.round(mScale[i] * (event.values[i] - mPrev[i]) * 0.45f);
            if (Math.abs(diff[i]) > 0) {
                show = true;
            }
            mPrev[i] = event.values[i];
        }

        if (show) {
            // only shows if we think the delta is big enough, in an attempt
            // to detect "serious" moves left/right or up/down
            Log.e(TAG, "sensorChanged " + event.sensor.getName() +
                    " (" + event.values[0] + ", " + event.values[1] + ", " +
                    event.values[2] + ")" + " diff(" + diff[0] +
                    " " + diff[1] + " " + diff[2] + ")");
        }

        long now = android.os.SystemClock.uptimeMillis();
        if (now - mLastGestureTime > 1000) {
            mLastGestureTime = 0;

            float x = diff[0];
            float y = diff[1];
            boolean gestX = Math.abs(x) > 3;
            boolean gestY = Math.abs(y) > 3;

            if ((gestX || gestY) && !(gestX && gestY)) {
                if (gestX) {
                    if (x < 0) {
                        Log.e("test", "<<<<<<<< LEFT <<<<<<<<<<<<");
                    } else {
                        Log.e("test", ">>>>>>>>> RITE >>>>>>>>>>>");
                    }
                } else {
                    if (y < -2) {
                        Log.e("test", "<<<<<<<< UP <<<<<<<<<<<<");
                    } else {
                        Log.e("test", ">>>>>>>>> DOWN >>>>>>>>>>>");
                    }
                }
                mLastGestureTime = now;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}