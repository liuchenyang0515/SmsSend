package com.example.sms_send;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText et_number;
    private EditText et_content;
    private static final int REQUESTCODE_ADD = 1;
    private static final int REQUESTCODE_INSERT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 找到控件
        et_number = (EditText) findViewById(R.id.et_number);
        et_content = (EditText) findViewById(R.id.et_content);
    }

    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                Intent intent = new Intent(this, ContactActivity.class);
                startActivityForResult(intent, REQUESTCODE_ADD);
                break;
            case R.id.btn_send:
                // 获取动态权限
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
                } else {
                    sendSms();
                }
                break;
            case R.id.btn_insert:
                Intent intent2 = new Intent(this, SmsTemplateActivity.class);
                startActivityForResult(intent2, REQUESTCODE_INSERT);
                break;
        }
    }

    private void sendSms() {
        // 获取发送短信的号码和发送的内容
        String number = et_number.getText().toString().trim();
        String content = et_content.getText().toString().trim();
        // 获取SmsManager实例
        SmsManager smsManager = SmsManager.getDefault();
        List<String> divideMessage = smsManager.divideMessage(content);
        for (String div : divideMessage) {
            smsManager.sendTextMessage(number, null, div, null, null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                    Manifest.permission.SEND_SMS)) {
                                showPermissionDialog(permissions);
                            } else {
                                Toast.makeText(this, "您已拒绝权限，请在设置手动打开权限", Toast.LENGTH_SHORT).show();
                            }
                            return;
                        }
                    }
                    sendSms();
                }
                break;
        }
    }

    private void showPermissionDialog(final String[] permissions) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("提示！");
        dialog.setMessage("这个权限关系到发送短信，如拒绝需要在设置手动打开!");
        dialog.setCancelable(false);
        dialog.setPositiveButton("授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
            }
        });
        dialog.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    // 当我们开启的activity页面关闭的时候调用此方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_ADD:
                if (resultCode == RESULT_OK) {
                    String phone = data.getStringExtra("phone");
                    et_number.setText(phone);
                }
                break;
            case REQUESTCODE_INSERT:
                if (resultCode == RESULT_OK) {
                    String smsContent = data.getStringExtra("smsContent");
                    //et_content.setText(smsContent);
                    et_content.append(smsContent);
                }
                break;
        }
    }
}