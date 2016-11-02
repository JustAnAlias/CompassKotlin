package com.example.rl.compass

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), SensorEventListener {

    // record the compass picture angle turned
    private var currentDegree = 0f

    // device sensor manager
    private var mSensorManager: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize your android device sensor capabilities
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()

        // for the system's orientation sensor registered listeners
        mSensorManager!!.registerListener(
                this,
                mSensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()

        // to stop the listener and save battery
        mSensorManager!!.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {

        // get the angle around the z-axis rotated
        val degree = Math.round(event.values[0]).toFloat()

        tvHeading.text = "Heading: $degree degrees"

        // create a rotation animation (reverse turn degree degrees)
        val ra = RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f)

        // how long the animation will take place
        ra.duration = 210

        // set the animation after the end of the reservation status
        ra.fillAfter = true

        // Start the animation
        imageViewCompass!!.startAnimation(ra)
        currentDegree = -degree

    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // not in use
    }
}