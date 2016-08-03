package com.celerysoft.bedtime.activity.browser.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.browser.presenter.IPresenterBrowserActivity;
import com.celerysoft.bedtime.activity.browser.presenter.PresenterBrowserActivity;
import com.celerysoft.bedtime.base.BaseActivity;
import com.celerysoft.ripple.Wrapper;

/**
 * Created by admin on 16/5/30.
 *
 */
public class BrowserActivity extends BaseActivity implements IViewBrowserActivity {
    public static final String INTENT_EXTRA_STRING_NAME_OF_URL = "the key name of url";
    public static final String INTENT_EXTRA_STRING_NAME_OF_TITLE = "the key name of title";

    private IPresenterBrowserActivity mPresenter;

    private ActionBar mActionBar;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private FloatingActionButton mFloatingActionButton;
    private Wrapper mAnimationWrapper;

    WebSettings mWebSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_browser);

        initActivity();
    }

    private void bindView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mAnimationWrapper = (Wrapper) findViewById(R.id.browser_ripple_animation);
        mProgressBar = (ProgressBar) findViewById(R.id.browser_progress_bar);
        mWebView = (WebView) findViewById(R.id.browser_web_view);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.browser_fab);
    }

    private void bindListener() {
        mAnimationWrapper.addAnimatorListenerAdapter(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mPresenter.finishActivityWithAnimation();
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            private int mOldProgress = 0;

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            public void onProgressChanged(WebView view, int progress) {
                super.onProgressChanged(view, progress);

                if (progress > mOldProgress) {
                    mProgressBar.setProgress(progress);
                }

                if (progress == 100) {
                    mProgressBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }, 1000);
                }

                mOldProgress = progress;
            }

        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {
                mActionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_left);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                // TODO display error page
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }


        });

//        mWebView.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_DOWN){
//                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
//                        mWebView.goBack();
//                        return true;
//                    }
//                }
//
//                mActionBar.setHomeAsUpIndicator(R.mipmap.ic_close);
//                return false;
//            }
//        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.displayAnimationOfFinishingActivity();
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initData() {
        mPresenter = new PresenterBrowserActivity(this);

        mWebSettings = mWebView.getSettings();
    }

    private void initView() {
        String url = getIntent().getStringExtra(INTENT_EXTRA_STRING_NAME_OF_URL);
        String title = getIntent().getStringExtra(INTENT_EXTRA_STRING_NAME_OF_TITLE);

        if (mActionBar != null) {
            title = (title != null && title.length() > 0) ? title : getString(R.string.app_name);

            mActionBar.setTitle(title);
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeAsUpIndicator(R.mipmap.ic_close);

//            TextView tvTitle = (TextView) findViewById(R.id.title);
//            if (tvTitle != null) {
//                tvTitle.setText(title);
//            }
//
//            View btnBack = findViewById(R.id.btn_back);
//            if (btnBack != null) {
//                btnBack.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        onBackPressed();
//                    }
//                });
//            }
//
//            View btnClose = findViewById(R.id.btn_close);
//            if (btnClose != null) {
//                btnClose.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        finish();
//                    }
//                });
//            }
        }

        mFloatingActionButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFloatingActionButton.show();
            }
        }, 300);

        mWebView.loadUrl(url);
    }

    private void initActivity() {
        bindView();
        bindListener();
        initData();
        initView();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            if (!mWebView.canGoBack()) {
                mActionBar.setHomeAsUpIndicator(R.mipmap.ic_close);
            }
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onStart() {
        super.onStart();

        if (mWebSettings != null) {
            mWebSettings.setJavaScriptEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mWebSettings != null) {
            mWebSettings.setJavaScriptEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mWebView.destroy();
    }

    @Override
    public BrowserActivity getActivity() {
        return this;
    }

    @Override
    public FloatingActionButton getFloatingActionButton() {
        return mFloatingActionButton;
    }

    @Override
    public Wrapper getAnimationWrapper() {
        return mAnimationWrapper;
    }
}
