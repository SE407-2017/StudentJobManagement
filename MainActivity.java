package com.example.memodemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;


public class MainActivity extends Activity {
    private MyDatabaseHelper dbHelper;
	private ListView listViewBasic;
	private String things;
	private InnerAdapter adapter;
	private ArrayList<String> list = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
        PublicWay.activityList.add(this);

		listViewBasic = (ListView) super.findViewById(R.id.listview);
		dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 1);

		list.clear();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query("Book", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				//遍历CURSOR对象，取出数据
				String time = cursor.getString(cursor.getColumnIndex("time"));
				//Log.d("onClick: =========",time);

				things = cursor.getString(cursor.getColumnIndex("things"));
				list.add(time + '\n' + things);
				adapter = new InnerAdapter(MainActivity.this, list); // 加载listiew
				listViewBasic.setAdapter(adapter);
			} while (cursor.moveToNext());
		}



		//点击一个按钮退出程序
		//打开的activity都会加入到一个list里面，点击退出，全部activity结束退出
		Button exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                for(int i=0;i<PublicWay.activityList.size();i++){
                    if (null != PublicWay.activityList.get(i)) {
                        PublicWay.activityList.get(i).finish();
                    }
                }
            }
        });


	}

	//不让手机返回键返回
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return  true;
		}
		return  super.onKeyDown(keyCode, event);

	}

	public void doClick(View v) {
		//给跳转页面一个值然后判断
		int a = 1;
		dbHelper.getWritableDatabase();
		Intent intent = new Intent(MainActivity.this, WriteActivity.class);
		intent.putExtra("judge", a);
		startActivity(intent);

	}

}
