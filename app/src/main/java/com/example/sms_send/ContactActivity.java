package com.example.sms_send;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private List<Person> lists;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载布局
        setContentView(R.layout.activity_contact);
        lv = (ListView) findViewById(R.id.lv);
        lists = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            Person p = new Person();
            p.setName("张三");
            p.setPhone("11" + i);
            lists.add(p);
        }
        // 展示数据
        lv.setAdapter(new Myadapter());
        // 给listview设置点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // parent代表listview，view代表item，可在断点调试验证
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取点中item的数据
                String phone = lists.get(position).getPhone();
                // 把数据返回给调用者
                Intent intent = new Intent();
                intent.putExtra("phone", phone);
                // 把结果返回给调用者
                setResult(RESULT_OK, intent);
                // 关闭当前页面
                finish(); // finish后通过onActivityResult返回数据

            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    private class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                // 获取子布局
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.contact_item, parent, false);
                viewHolder = new ViewHolder();
                // 找到在item中定义的控件来显示数据
                // 一定要写view.findViewById,findViewById是有上下文的，默认是在Activity的主布局中
                viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
                viewHolder.tv_phone = (TextView) view.findViewById(R.id.tv_phone);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            // 展示数据
            viewHolder.tv_name.setText(lists.get(position).getName());
            viewHolder.tv_phone.setText(lists.get(position).getPhone());
            return view;
        }

        private class ViewHolder {
            TextView tv_name, tv_phone;
        }
    }
}
