package org.aurora.androidexam.ui.swipeback;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.aurora.androidexam.R;
import org.aurora.androidexam.log.Logger;

public class SwipeBackTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_back_test);
//        findViewById(R.id.iv_child2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Logger.info("onclick");
//            }
//        });

    }
}
