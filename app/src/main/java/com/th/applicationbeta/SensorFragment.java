package com.th.applicationbeta;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SensorFragment extends Fragment {

    private SensorManager sensorManager;
    private Sensor acceleromemter, gyroscope, proximity, pressure, light, temperature;
    private TextView textView2,textView3,textView4,textView5,textView6,textView7;

    public SensorFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        textView4 = (TextView) view.findViewById(R.id.textView4);
        textView5 = (TextView) view.findViewById(R.id.textView5);
        textView6 = (TextView) view.findViewById(R.id.textView6);
        textView7 = (TextView) view.findViewById(R.id.textView7);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        acceleromemter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        SensorEventListener acceleromemterSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                Sensor mySensor = sensorEvent.sensor;
                if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    String stracceler = "X: " + sensorEvent.values[0] + ", Y: " + sensorEvent.values[1] +
                            ", Z: " + sensorEvent.values[2];
                    textView2.setText(stracceler);
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        SensorEventListener gyroscopeSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                Sensor mySensor = sensorEvent.sensor;
                if(mySensor.getType() == Sensor.TYPE_GYROSCOPE) {
                    String stracceler = "X: " + sensorEvent.values[0] + ", Y: " + sensorEvent.values[1] +
                            ", Z: " + sensorEvent.values[2];
                    textView3.setText(stracceler);
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        SensorEventListener proximitySensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                Sensor mySensor = sensorEvent.sensor;
                if(mySensor.getType() == Sensor.TYPE_PROXIMITY) {
                    String stracceler = " " + sensorEvent.values[0];
                    textView4.setText(stracceler);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        SensorEventListener pressureSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                Sensor mySensor = sensorEvent.sensor;

                if (mySensor.getType() == Sensor.TYPE_PRESSURE) {
                    String str = "" + sensorEvent.values[0] + " hPa";
                    textView5.setText(str);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        SensorEventListener lightSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                Sensor mySensor = sensorEvent.sensor;

                if (mySensor.getType() == Sensor.TYPE_LIGHT) {
                    float lightValue = sensorEvent.values[0];
                    String str = lightValue + " lx";
                    textView6.setText(str);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        SensorEventListener temperatureSensorListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {

                if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {

                    float temp = event.values[0]; // °C
                    String str = temp + " °C";
                    textView7.setText(str);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };




        sensorManager.registerListener(pressureSensorListener, pressure,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(acceleromemterSensorListener, acceleromemter ,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(gyroscopeSensorListener, gyroscope ,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(proximitySensorListener, proximity , 2 * 1000 * 1000);

        // ===== Temperature Sensor =====
        if (temperature != null) {

            sensorManager.registerListener(
                    temperatureSensorListener,
                    temperature,
                    SensorManager.SENSOR_DELAY_NORMAL
            );

        } else {
            textView7.setText("No Temperature Sensor");
        }

        // ===== Light Sensor =====
        if (light != null) {
            sensorManager.registerListener(lightSensorListener, light,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            textView6.setText("No Light Sensor");
        }

        return view;
    }
}
