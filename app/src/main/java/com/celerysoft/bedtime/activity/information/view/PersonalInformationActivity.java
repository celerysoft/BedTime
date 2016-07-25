package com.celerysoft.bedtime.activity.information.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.information.presenter.IPresenterPersonalInformationActivity;
import com.celerysoft.bedtime.activity.information.presenter.PresenterPersonalInformationActivity;
import com.celerysoft.bedtime.base.BaseActivity;
import com.celerysoft.bedtime.util.FileUtil;

import java.io.File;

/**
 * Created by admin on 16/5/3.
 */
public class PersonalInformationActivity extends BaseActivity implements IViewPersonalInformationActivity {
    private ImageView mIvAvatar;
    private AppCompatTextView mTvNickname;
    private AppCompatEditText mEtNickname;
    private AppCompatTextView mTvAge;
    private AppCompatEditText mEtAge;
    private AppCompatTextView mTvSleepTime;
    private FloatingActionButton mFloatingActionButton;

    private IPresenterPersonalInformationActivity mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_personal_information);

        init();
    }

    private void init() {
        mPresenter = new PresenterPersonalInformationActivity(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.personal_information_toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.mipmap.ic_arrow_left);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonalInformationActivity.this.finish();
                }
            });
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.personal_information_title);
        }

        mIvAvatar = (ImageView) findViewById(R.id.personal_information_iv_avatar);
        mIvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showModifyAvatarDialog();
            }
        });
        Bitmap avatar = mPresenter.getAvatar();
        if (avatar != null) {
            mIvAvatar.setImageBitmap(mPresenter.getAvatar());
        }

        mTvNickname = (AppCompatTextView) findViewById(R.id.personal_information_tv_nickname);
        mTvNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.editNickname();
            }
        });
        mTvNickname.setText(mPresenter.getNickname());
        mEtNickname = (AppCompatEditText) findViewById(R.id.personal_information_et_nickname);

        mTvAge = (AppCompatTextView) findViewById(R.id.personal_information_tv_age);
        mTvAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.editAge();
            }
        });
        mTvAge.setText(mPresenter.getAge());
        mEtAge = (AppCompatEditText) findViewById(R.id.personal_information_et_age);

        mTvSleepTime = (AppCompatTextView) findViewById(R.id.personal_information_tv_sleep_time);
        mTvSleepTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showSleepTimePickerDialog();
            }
        });
        mTvSleepTime.setText(mPresenter.getSleepTime());

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.personal_information_fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveInformation();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IPresenterPersonalInformationActivity.REQUEST_CODE_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    mPresenter.crop(uri);
                }
            }
        } else if (requestCode == IPresenterPersonalInformationActivity.REQUEST_CODE_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                File file = new File(FileUtil.getInstance().getAvatarTempPath(this));
                if (file.exists()) {
                    mPresenter.crop(Uri.fromFile(file));
                }
            }
        } else if (requestCode == IPresenterPersonalInformationActivity.REQUEST_CODE_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = data.getParcelableExtra("data");
                mIvAvatar.setImageBitmap(bitmap);
                mPresenter.saveAvatar(bitmap);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (mPresenter.isInEditMode()) {
            mPresenter.cancelEditMode();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public AppCompatTextView getTvNickname() {
        return mTvNickname;
    }

    @Override
    public AppCompatEditText getEtNickname() {
        return mEtNickname;
    }

    @Override
    public AppCompatTextView getTvAge() {
        return mTvAge;
    }

    @Override
    public AppCompatEditText getEtAge() {
        return mEtAge;
    }

    @Override
    public AppCompatTextView getTvSleepTime() {
        return mTvSleepTime;
    }

    @Override
    public FloatingActionButton getFloatingActionButton() {
        return mFloatingActionButton;
    }
}
