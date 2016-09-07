package androarmy.pooliodrivers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


public class SplashScreen extends Activity {
    MyCountdownTimer myCountdownTimer;
    Animation fadeIn;
    private ImageView logo;
    public String NO_VAL = "empty";
    private String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spashscreen);
        StartAnimations();
        myCountdownTimer = new MyCountdownTimer(3000, 1000);
        myCountdownTimer.start();

        logo = (ImageView) findViewById(R.id.logo);

        fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(2100);
        fadeIn.setFillAfter(true);
        logo.startAnimation(fadeIn);

    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim);
    }

    public class MyCountdownTimer extends CountDownTimer {

        public MyCountdownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override

        public void onFinish() {
            myCountdownTimer.cancel();
            /*
            SharedPreferences session = getSharedPreferences("session", MODE_PRIVATE);
            String mob = session.getString("mobile", NO_VAL);
            String pass = session.getString("password", NO_VAL);
            */

            Intent intent = new Intent(getApplicationContext(), SignIn.class);
            overridePendingTransition(R.anim.next_slide_in,R.anim.next_slide_out);
            startActivity(intent);


        }
    }


        public String md5(String s) {
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("MD5");
                digest.update(s.getBytes(Charset.forName("US-ASCII")), 0, s.length());
                byte[] magnitude = digest.digest();
                BigInteger bi = new BigInteger(1, magnitude);
                String hash = String.format("%0" + (magnitude.length << 1) + "X", bi);
                password = hash;
                return hash;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            return "";
        }
    }

