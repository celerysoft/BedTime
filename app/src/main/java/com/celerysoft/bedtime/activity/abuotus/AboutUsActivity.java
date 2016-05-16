package com.celerysoft.bedtime.activity.abuotus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.webkit.WebView;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.util.Const;
import com.celerysoft.bedtime.view.BaseActivity;

/**
 * Created by admin on 16/5/16.
 *
 */
public class AboutUsActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about_us);

        initActivity();
    }

    private void initActivity() {
        WebView webView = (WebView) findViewById(R.id.about_us_web_view);
        if (webView != null) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(Const.ABOUT_BED_TIME_URL);
        }

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.about_us_fab);
        if (fab != null) {
            fab.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fab.show();
                }
            }, 300);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fab.hide();
                    fab.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AboutUsActivity.this.finish();
                        }
                    }, 300);
                }
            });
        }
    }
}
