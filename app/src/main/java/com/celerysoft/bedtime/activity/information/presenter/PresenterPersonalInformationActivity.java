package com.celerysoft.bedtime.activity.information.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ListViewCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.information.adapter.ModifyAvatarDialogAdapter;
import com.celerysoft.bedtime.activity.information.model.PersonalInformationModel;
import com.celerysoft.bedtime.activity.information.view.IViewPersonalInformationActivity;
import com.celerysoft.bedtime.util.FileUtil;
import com.celerysoft.bedtime.util.Util;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;

/**
 * Created by admin on 16/5/3.
 */
public class PresenterPersonalInformationActivity implements IPresenterPersonalInformationActivity {
    private IViewPersonalInformationActivity mView;
    private PersonalInformationModel mModel;

    private Context mContext;

    private AlertDialog mModifyAvatarDialog;

    public PresenterPersonalInformationActivity(IViewPersonalInformationActivity view) {
        mView = view;

        mContext = mView.getContext();

        mModel = new PersonalInformationModel(mContext);
    }

    @Override
    public String getNickname() {
        return mModel.getNickname();
    }

    @Override
    public void editNickname() {
        mView.getFloatingActionButton().show();
        mView.getTvNickname().setVisibility(View.GONE);
        mView.getEtNickname().setVisibility(View.VISIBLE);
        mView.getEtNickname().setText(mView.getTvNickname().getText());
        mView.getEtNickname().requestFocus();
    }

    @Override
    public String getAge() {
        return mModel.getAge();
    }

    @Override
    public void editAge() {
        mView.getFloatingActionButton().show();
        mView.getTvAge().setVisibility(View.GONE);
        mView.getEtAge().setVisibility(View.VISIBLE);
        mView.getEtAge().setText(mView.getTvAge().getText());
        mView.getEtAge().requestFocus();
    }

    @Override
    public String getSleepTime() {
        return mModel.getSleepTime();
    }

    @Override
    public void saveInformation() {
        mView.getFloatingActionButton().hide();

        if (mView.getTvNickname().getVisibility() == View.GONE) {
            String nickname = mView.getEtNickname().getText().toString();

            mView.getTvNickname().setVisibility(View.VISIBLE);
            mView.getTvNickname().setText(nickname);
            mView.getEtNickname().setVisibility(View.GONE);
            mModel.setNickname(nickname);
        }

        if (mView.getTvAge().getVisibility() == View.GONE) {
            String age = mView.getEtAge().getText().toString();

            mView.getTvAge().setVisibility(View.VISIBLE);
            mView.getTvAge().setText(age);
            mView.getEtAge().setVisibility(View.GONE);
            mModel.setAge(age);

            setSleepTimeByAge(age);
        }
    }

    @Override
    public void showSleepTimePickerDialog() {
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                mModel.setSleepTime(hourOfDay, minute);
                mView.getTvSleepTime().setText(getSleepTimeString(hourOfDay, minute));
            }
        }, mModel.getSleepTimeHour(), mModel.getSleepTimeMinute(), true);
        timePickerDialog.setThemeDark(false);
        timePickerDialog.vibrate(false);
        timePickerDialog.dismissOnPause(true);
        timePickerDialog.enableSeconds(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePickerDialog.setAccentColor(mContext.getResources().getColor(R.color.colorPrimary, null));
        } else {
            timePickerDialog.setAccentColor(mContext.getResources().getColor(R.color.colorPrimary));
        }

        timePickerDialog.show(mView.getFragmentManager(), "TimePickerDialog");
    }

    private String getSleepTimeString(int hour, int minute) {
        String hourString = hour > 1 ? mContext.getString(R.string.personal_information_hours_text) : mContext.getString(R.string.personal_information_hour_text);
        hourString = hour + hourString;
        String minuteString;
        if (minute == 0) {
            minuteString = "";
        } else {
            minuteString = minute > 1 ? mContext.getString(R.string.personal_information_minutes_text) : mContext.getString(R.string.personal_information_minute_text);
            minuteString = minute + minuteString;
        }

        return hourString + minuteString;
    }

    private void setSleepTimeByAge(String ageString) {
        int age = Integer.valueOf(ageString);

        int sleepHour;
        int sleepMinute;
        if (age == 0) {
            sleepHour = 20;
            sleepMinute = 0;
        } else if (age == 1) {
            sleepHour = 15;
            sleepMinute = 0;
        } else if (age == 2) {
            sleepHour = 14;
            sleepMinute = 0;
        } else if (age <= 4) {
            sleepHour = 13;
            sleepMinute = 0;
        } else if (age <= 7) {
            sleepHour = 12;
            sleepMinute = 0;
        } else if (age <= 12) {
            int minute = 10 * 60 - (age - 8) * 15;
            sleepHour = minute / 60;
            sleepMinute = minute % 60;
        } else if (age <= 18) {
            int minute = 9 * 60 - (age - 13) * 10;
            sleepHour = minute / 60;
            sleepMinute = minute % 60;
        } else if (age < 60) {
            int minute = 8 * 60 - (int) ((age - 19) * 1.5);
            sleepHour = minute / 60;
            sleepMinute = minute % 60;
        } else if (age <= 70) {
            int minute = 7 * 60 - (age - 60) * 9;
            sleepHour = minute / 60;
            sleepMinute = minute % 60;
        } else {
            sleepHour = 5;
            sleepMinute = 30;
        }

        mModel.setSleepTime(sleepHour, sleepMinute);
        mView.getTvSleepTime().setText(getSleepTimeString(sleepHour, sleepMinute));
    }

    @Override
    public void showModifyAvatarDialog() {
        ListViewCompat listView = new ListViewCompat(mContext);
        listView.setAdapter(new ModifyAvatarDialogAdapter(mContext));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case ModifyAvatarDialogAdapter.CAMERA:
                        takePhoto();
                        break;
                    case ModifyAvatarDialogAdapter.GALLERY:
                        pickPhotoFromGallery();
                        break;
                    case ModifyAvatarDialogAdapter.CANCEL:
                        break;
                    default:
                        break;
                }

                if (mModifyAvatarDialog != null && mModifyAvatarDialog.isShowing()) {
                    mModifyAvatarDialog.dismiss();
                }
            }
        });

        mModifyAvatarDialog = new AlertDialog.Builder(mContext, R.style.AppTheme_Dialog_Light_Bottom).setView(listView).create();
        mModifyAvatarDialog.getWindow().setGravity(Gravity.BOTTOM);
        mModifyAvatarDialog.setCanceledOnTouchOutside(true);
        mModifyAvatarDialog.show();
    }

    public void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (FileUtil.getInstance().isExternalStorageWritable()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(FileUtil.getInstance().getAvatarTempPath(mContext))));
        }
        mView.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    public void pickPhotoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        mView.startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }


    @Override
    public void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", Util.dip2px(mContext, 60));
        intent.putExtra("outputY", Util.dip2px(mContext, 60));
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        mView.startActivityForResult(intent, REQUEST_CODE_CROP);
    }

    @Override
    public Bitmap getAvatar() {
        return mModel.getAvatar();
    }

    @Override
    public void saveAvatar(Bitmap bitmap) {
        mModel.setAvatar(bitmap);
    }
}
