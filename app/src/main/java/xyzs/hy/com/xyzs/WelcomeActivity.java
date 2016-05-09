package xyzs.hy.com.xyzs;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import cn.bmob.v3.*;
import xyzs.hy.com.xyzs.entity.User;


public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);

        Bmob.initialize(this, "4de3a6f96b63b37633d2c1ae5fb7cb07");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                User user = BmobUser.getCurrentUser(WelcomeActivity.this, User.class);
                if (user != null) {
                    Intent intent = new Intent(WelcomeActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(WelcomeActivity.this,
                            LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 1000 * 2);
    }

}