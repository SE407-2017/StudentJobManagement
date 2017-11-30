package com.example.memodemo;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.icu.text.DateFormat;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.os.Vibrator;
import android.content.Context;

public class ClockActivity extends Activity {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        // 创建一个媒体播放器，播放在raw文件夹中存储的MP3文件
        mediaPlayer= MediaPlayer.create(this,R.raw.hahaha);
        mediaPlayer.start();
        //使手机震动
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {1000,5000,1000,5000};
        vibrator.vibrate(pattern, 0);
        //创建一个警示栏，设置关闭闹钟的按钮
        new AlertDialog.Builder(ClockActivity.this).setTitle("到点了").setMessage("快去写作业")
                .setPositiveButton("关闭闹钟",new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which){


                        mediaPlayer.stop();
                        vibrator.cancel();
                       // Intent it=new Intent(ClockActivity.this,WriteActivity.class);
                        //startActivity(it);
                        ClockActivity.this.finish();


                    }


                }).setCancelable(false).show();
    }

}
