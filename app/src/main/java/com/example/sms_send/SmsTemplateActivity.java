package com.example.sms_send;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SmsTemplateActivity extends AppCompatActivity {
    String[] str = {"我在吃饭，请稍后联系", "我在开会，请稍后联系",
            "我在上课，请稍后联系", "我在加班，请稍后联系","我在约会，请稍后联系"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smstemplate);
        ListView lv = (ListView) findViewById(R.id.lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.smstemplate_item, R.id.tv, str);
        // 显示数据
        lv.setAdapter(adapter);
        // 设置item点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 取出点击item的数据
                String smsContent = str[position];
                // 把smsContent返回给调用者
                Intent intent = new Intent();
                intent.putExtra("smsContent", smsContent);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
