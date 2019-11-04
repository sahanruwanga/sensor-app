package org.devs.sensor_app;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SensorManager sensorManager;

    TextView xValue;
    TextView yValue;
    TextView zValue;

    Button startButton;
    Button stopButton;

    List sensors;

    SensorEventListener sensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set text views
        xValue = findViewById(R.id.sensor2Reading);
        yValue = findViewById(R.id.sensor3Reading);
        zValue = findViewById(R.id.sensor4Reading);

        // Set buttons
        startButton = findViewById(R.id.button1);
        stopButton = findViewById(R.id.button2);

        // Get system sensor service
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorEventListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                xValue.setText(String.valueOf(values[0]));
                yValue.setText(String.valueOf(values[1]));
                zValue.setText(String.valueOf(values[2]));
            }
        };

        // Get accelerometer sensors into a list
        sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);

        // Button action listeners
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sensors.size() > 1) {
                    sensorManager.registerListener(sensorEventListener, (Sensor) sensors.get(0), SensorManager.SENSOR_DELAY_NORMAL);
                } else {
                    Toast.makeText(getBaseContext(), "Error: No Accelerometer!", Toast.LENGTH_LONG).show();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sensors.size() > 0) {
                    sensorManager.unregisterListener(sensorEventListener);
                }
            }
        });


    }

    @Override
    protected void onStop() {
        if (sensors.size() > 0) {
            sensorManager.unregisterListener(sensorEventListener);
        }
        super.onStop();
    }
}
