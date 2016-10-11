package com.celerysoft.bedtime.activity.information.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ListViewCompat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.information.adapter.ModifyAvatarDialogAdapter;
import com.celerysoft.bedtime.activity.information.model.PersonalInformationModel;
import com.celerysoft.bedtime.activity.information.view.IViewPersonalInformationActivity;
import com.celerysoft.bedtime.util.BitmapUtil;
import com.celerysoft.bedtime.util.Const;
import com.celerysoft.bedtime.util.FileUtil;
import com.celerysoft.bedtime.util.InitViewUtil;
import com.celerysoft.bedtime.util.Util;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.util.List;

/**
 * Created by admin on 16/5/3.
 * Presenter for PersonalInformationActivity
 */
public class PresenterPersonalInformationActivity implements IPresenterPersonalInformationActivity {
    private IViewPersonalInformationActivity mView;
    private PersonalInformationModel mModel;

    private Context mContext;

    private AlertDialog mModifyAvatarDialog;

    private boolean mIsInEditMode = false;

    public PresenterPersonalInformationActivity(IViewPersonalInformationActivity view) {
        mView = view;

        mContext = mView.getContext();

        mModel = new PersonalInformationModel(mContext);
    }

    @Override
    public boolean isInEditMode() {
        return mIsInEditMode;
    }

    @Override
    public void cancelEditMode() {
        mIsInEditMode = false;

        mView.getFloatingActionButton().hide();

        if (mView.getTvNickname().getVisibility() == View.GONE) {
            mView.getTvNickname().setVisibility(View.VISIBLE);
            mView.getEtNickname().setVisibility(View.GONE);
            mView.getBtnNickname().setClickable(true);
        }

        if (mView.getTvAge().getVisibility() == View.GONE) {
            mView.getTvAge().setVisibility(View.VISIBLE);
            mView.getEtAge().setVisibility(View.GONE);
            mView.getBtnAge().setClickable(true);
        }
    }

    @Override
    public String getNickname() {
        return mModel.getNickname();
    }

    @Override
    public void editNickname() {
        mIsInEditMode = true;

        mView.getFloatingActionButton().show();
        mView.getBtnNickname().setClickable(false);
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
        mIsInEditMode = true;

        mView.getFloatingActionButton().show();
        mView.getBtnAge().setClickable(false);
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
        mIsInEditMode = false;

        mView.getFloatingActionButton().hide();

        if (mView.getTvNickname().getVisibility() == View.GONE) {
            String nickname = mView.getEtNickname().getText().toString();

            mView.getBtnNickname().setClickable(false);
            mView.getTvNickname().setVisibility(View.VISIBLE);
            mView.getTvNickname().setText(nickname);
            mView.getEtNickname().setVisibility(View.GONE);
            mModel.setNickname(nickname);
        }

        if (mView.getTvAge().getVisibility() == View.GONE) {
            String age = mView.getEtAge().getText().toString();

            mView.getBtnNickname().setClickable(false);
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
            //noinspection deprecation
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
        InitViewUtil.getInstance().initListView(listView);

        mModifyAvatarDialog = new AlertDialog.Builder(mContext, R.style.AppTheme_Dialog_Light_Bottom).setView(listView).create();
        mModifyAvatarDialog.getWindow().setGravity(Gravity.BOTTOM);
        mModifyAvatarDialog.setCanceledOnTouchOutside(true);
        mModifyAvatarDialog.show();

        mModifyAvatarDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        Uri uri = FileProvider.getUriForFile(mContext, Const.FILE_PROVIDER_AUTHORITIES, new File(FileUtil.getInstance().getAvatarTempPath(mContext)));

        PackageManager packageManager = mContext.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        for (ResolveInfo activity : activities) {
            mContext.grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

        if (FileUtil.getInstance().isExternalStorageMounted()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            // TODO add External Storage not Mounted hint
            return;
        }

        mView.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    private void pickPhotoFromGallery() {
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
        intent.putExtra("outputX", Util.dp2px(mContext, 60));
        intent.putExtra("outputY", Util.dp2px(mContext, 60));
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri

        // 赋予其他App处理这个Uri的权限
        PackageManager packageManager = mContext.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        for (ResolveInfo activity : activities) {
            mContext.grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

        mView.startActivityForResult(intent, REQUEST_CODE_CROP);
    }

    @Override
    public void deriveBitmapAndStore(Intent intent) {
        try {
            Bitmap bitmap = intent.getParcelableExtra("data");
            if (bitmap == null) {
                bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), intent.getData());
            }

            bitmap = BitmapUtil.getInstance().autoCropSquareBitmap(bitmap);

            cropBitmapAndStore(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cropBitmapAndStore(Bitmap bitmap) {
        File tempFile = FileUtil.getInstance().getAvatarTempFile(mContext);
        if (tempFile != null && tempFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            tempFile.delete();
        }

        if (bitmap != null && !bitmap.isRecycled()) {
            saveAvatar(bitmap);
            mView.getIvAvatar().setImageBitmap(bitmap);
            Snackbar.make(mView.getFloatingActionButton(), mContext.getString(R.string.personal_information_avatar_modify_success), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean isTempAvatarFileExists() {
        File file = FileUtil.getInstance().getAvatarTempFile(mContext);
        if (file != null) {
            if (file.exists()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Bitmap deriveTempAvatar() {
        String tempAvatarFilePath = FileUtil.getInstance().getAvatarTempPath(mContext);
        int bitmapLength = Util.dp2px(mContext, 72);

        return BitmapUtil.getInstance().deriveSquareBitmap(tempAvatarFilePath, bitmapLength);
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
