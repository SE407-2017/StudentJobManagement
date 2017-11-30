package com.example.memodemo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.AlarmManager;
import android.app.PendingIntent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WriteActivity extends Activity {
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private EditText editText1;
	private MyDatabaseHelper dbHelper;
	private AlarmManager alarmManager;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.writeup);

		PublicWay.activityList.add(this);

		editText1 = (EditText) findViewById(R.id.writedown);
		Intent intent1 = getIntent();
		Bundle bundle = intent1.getExtras();
		String rewrite = bundle.getString("str");
//确保修改作业里的时间时将原来设定的闹钟删除
		if (rewrite != null) {
			String[] str3 = rewrite.split("\n");
			String str4 = str3[1];
			editText1.setText(str4);
			String str20000=str3[0].trim();
			String str10000="";
			if (str20000!=null && !"".equals(str20000)){
				for (int bala=4;bala<str20000.length();bala++){
					if ((str20000.charAt(bala)>=48) && (str20000.charAt(bala)<=57)){
						str10000 += str20000.charAt(bala);
					}
				}
			}
			int myId=Integer.parseInt(str10000);
			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			Intent intent100 = new Intent(this, ClockActivity.class);
			PendingIntent pi = PendingIntent.getActivity(this, myId, intent100, 0);
			alarmManager.cancel(pi);
		}
		//new一个新对象，要不然下面会出现空指针
		dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 1);
//时间选择器的使用
		DatePicker datePicker = (DatePicker)
				findViewById(R.id.datePicker);
		TimePicker timePicker = (TimePicker)
				findViewById(R.id.timePicker);
		// 获取当前的年、月、日、小时、分钟
		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR);
		minute = c.get(Calendar.MINUTE);
		// 初始化DatePicker组件，初始化时指定监听器
		datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker arg0, int year
					, int month, int day) {
				WriteActivity.this.year = year;
				WriteActivity.this.month = month;
				WriteActivity.this.day = day;
				// 显示当前日期、时间
				//showDate(year, month, day, hour, minute);
				Toast.makeText(WriteActivity.this, "您选择的日期：" + year + "年  "
						+ month + "月  " + day + "日", Toast.LENGTH_SHORT).show();
			}
		});
		// 为TimePicker指定监听器
		timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view
					, int hourOfDay, int minute) {
				WriteActivity.this.hour = hourOfDay;
				WriteActivity.this.minute = minute;
				// 显示当前日期、时间
				Toast.makeText(WriteActivity.this, "您选择的时间：" + hourOfDay + "时  "
						+ minute + "分", Toast.LENGTH_SHORT).show();
//
			}
		});


		// 定义在EditText中显示当前日期、时间的方法

		//保存时间并跳转

		Button saveData = (Button) findViewById(R.id.savedata);
//点击保存按钮会存储作业内容及时间

		saveData.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
				Date curDate = new Date(System.currentTimeMillis());//获取当前时间
				String str = Integer.toString(year) + "年" + Integer.toString(month + 1) + "月" + Integer.toString(day) + "日" + Integer.toString(hour) + "时" + Integer.toString(minute) + "分";
				String str1 = editText1.getText().toString();

				//作业内容不能为空
				if (editText1.getText().toString().length() == 0) {
					CharSequence html1 = Html.fromHtml("<font color='red'>作业不可能是空的吧</font>"); //任意长度内容均可
					editText1.setError(html1);


				} else {
					str1 = str1.replace('\n', '\r');
					SQLiteDatabase db = dbHelper.getWritableDatabase();
					ContentValues values = new ContentValues();
					//插入数据

					values.put("things", str1 + " ");
					values.put("time", String.valueOf(str));
					db.insert("Book", null, values);
					values.clear();

					Toast.makeText(WriteActivity.this, "作业添加成功了哟", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(WriteActivity.this, MainActivity.class);
					startActivity(intent);

					alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
					//启动clockactivity
					Intent intent100 = new Intent(WriteActivity.this, ClockActivity.class);
					String myID = Integer.toString(month + 1) + Integer.toString(day) + Integer.toString(hour) + Integer.toString(minute);
					int alarmCount = Integer.parseInt(myID);

					PendingIntent pi = PendingIntent.getActivity(WriteActivity.this, alarmCount, intent100, 0);
					Calendar ci = Calendar.getInstance();
					ci.setTimeInMillis(System.currentTimeMillis());
					//根据用户选择的时间设置calendar对象
					ci.set(Calendar.YEAR, year);
					ci.set(Calendar.MONTH, month);
					ci.set(Calendar.DAY_OF_MONTH, day);

					ci.set(Calendar.HOUR_OF_DAY, hour);
					ci.set(Calendar.MINUTE, minute);
					ci.set(Calendar.SECOND, 0);


					//定时启动
					alarmManager.set(AlarmManager.RTC_WAKEUP, ci.getTimeInMillis(), pi);
					//添加一个toast表示闹钟设置完毕
					Toast.makeText(WriteActivity.this, "搞定了", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		});
	}


	public void reClick(View view){
		//首页传过来一个值判断是添加还是修改
		//添加提示没有内容
		//修改会显示前面内容
		Intent intent2 = getIntent();
		Bundle bundle2 = intent2.getExtras();
		int judge = bundle2.getInt("judge");
		if(judge == 1){
			Toast.makeText(WriteActivity.this,"没有内容",Toast.LENGTH_SHORT).show();

		}else{
			Intent intent1=getIntent();
			Bundle bundle=intent1.getExtras();
			String rewrite=bundle.getString("str");
			editText1.setText(rewrite);
		}
	}


	//不让手机返回键返回
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return  true;
		}
		return  super.onKeyDown(keyCode, event);

	}
}
