package com.celerysoft.bedtime.activity.browser.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
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

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.browser.presenter.IPresenterBrowserActivity;
import com.celerysoft.bedtime.activity.browser.presenter.PresenterBrowserActivity;
import com.celerysoft.bedtime.base.BaseActivity;
import com.celerysoft.rippletransitionanimationview.RippleTransitionAnimationView;

/**
 * Created by admin on 16/5/30.
 *
 */
public class BrowserActivity extends BaseActivity implements IViewBrowserActivity {
    public static final String INTENT_EXTRA_STRING_NAME_OF_URL = "the key name of url";

    private IPresenterBrowserActivity mPresenter;

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private FloatingActionButton mFloatingActionButton;
    private RippleTransitionAnimationView mAnimationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_browser);

        initActivity();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initActivity() {
        mPresenter = new PresenterBrowserActivity(this);

        String url = getIntent().getStringExtra(INTENT_EXTRA_STRING_NAME_OF_URL);

        mAnimationView = (RippleTransitionAnimationView) findViewById(R.id.browser_ripple_animation);
        mAnimationView.setAnimatorListenerAdapter(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mPresenter.finishActivityWithAnimation();
            }
        });

        mProgressBar = (ProgressBar) findViewById(R.id.browser_progress_bar);

        mWebView = (WebView) findViewById(R.id.browser_web_view);
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
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        mWebView.loadUrl(url);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.browser_fab);
        if (mFloatingActionButton != null) {
            mFloatingActionButton.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFloatingActionButton.show();
                }
            }, 300);
            mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.displayAnimationOfFinishingActivity();
                }
            });
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onStart() {
        super.onStart();

        WebSettings webSettings = mWebView.getSettings();
        if (webSettings != null) {
            webSettings.setJavaScriptEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        WebSettings webSettings = mWebView.getSettings();
        if (webSettings != null) {
            webSettings.setJavaScriptEnabled(false);
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
    public RippleTransitionAnimationView getAnimationView() {
        return mAnimationView;
    }
}
