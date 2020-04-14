package mi.song.weekand.farm.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import mi.song.weekand.farm.R;
import mi.song.weekand.farm.ui.corplist.CorpListActivity;

public class SplashActivity extends AppCompatActivity {
    final private int SPLASH_TIME = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //end activity after 1500ms
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, CorpListActivity.class));
                finish();
            }
        }, SPLASH_TIME);
    }
}
