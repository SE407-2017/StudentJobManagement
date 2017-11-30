package com.example.memodemo;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.app.AlarmManager;

public class InnerAdapter extends BaseAdapter {

    private MyDatabaseHelper dbHelper;
    private Context mContext;
    private List<String> mList = new ArrayList<>();

    public InnerAdapter(Context context, List<String> list) {
        this.mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        int a=i;
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
            viewHolder.mTextView = (TextView) view.findViewById(R.id.writedown);
            viewHolder.mButton = (ImageView) view.findViewById(R.id.delete);
            viewHolder.wButton = (ImageView) view.findViewById(R.id.write_down);
            view.setTag(viewHolder);
        } if(true) {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTextView.setText(mList.get(i));
        final View finalView = view;
        //删除
        viewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                /*.setTitle("Oldnote")
                .setIcon(R.drawable.logo5)*/
                        .setMessage("您确定删除该条信息么？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dbHelper = new MyDatabaseHelper(mContext, "BookStore.db", null, 1);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                TextView textView = (TextView) finalView.findViewById(R.id.writedown);
                                String str = (String) textView.getText();

                                //java截取字符串  截取\n前后字符串，前面为str1[0],后面为str1[2];
                                String[] str1 = str.split("\n");
                                String str2 = str1[1];
                                //Log.d("onClick: ======", String.valueOf(str2));
                                db.delete("Book","things = ?", new String[]{String.valueOf(str2)});
                                Toast.makeText(mContext,"delete successed",Toast.LENGTH_SHORT).show();

                                String str20000=str1[0].trim();
                                String str10000="";
                                if (str20000!=null && !"".equals(str20000)){
                                    for (int bala=4;bala<str20000.length();bala++){
                                        if ((str20000.charAt(bala)>=48) && (str20000.charAt(bala)<=57)){
                                            str10000 += str20000.charAt(bala);
                                        }
                                    }
                                }
                                int myId=Integer.parseInt(str10000);

                                Intent intent100 = new Intent(mContext,ClockActivity.class);
                                PendingIntent pi = PendingIntent.getActivity(mContext,myId,intent100,0);
                                AlarmManager alarmManager=(AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);
                                alarmManager.cancel(pi);


                                Intent intent = new Intent(mContext,MainActivity.class);
                                mContext.startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                       // Intent intent = new Intent(mContext,MainActivity.class);
                       //mContext.startActivity(intent);
                            }
                        })
                        .show();
            }
        });
        //修改
        viewHolder.wButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                /*.setTitle("Oldnote")
                .setIcon(R.drawable.logo5)*/
                        .setMessage("您确定修改这个作业么？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dbHelper = new MyDatabaseHelper(mContext, "BookStore.db", null, 1);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                TextView textView = (TextView) finalView.findViewById(R.id.writedown);
                                String str = (String) textView.getText();

                                //java截取字符串  截取\n前后字符串，前面为str1[0],后面为str1[2];
                                String[] str1 = str.split("\n");
                                String str2 = str1[1];
                                //Log.d("onClick: ======", String.valueOf(str2));
                                db.delete("Book","things = ?", new String[]{String.valueOf(str2)});
                                Toast.makeText(mContext,"请开始修改作业吧",Toast.LENGTH_SHORT).show();
                                //TextView textView = (TextView) finalView.findViewById(R.id.writedown);
                                String rewrite = (String) textView.getText();
                                Intent intent = new Intent(mContext,WriteActivity.class);
                                intent.putExtra("str", rewrite);
                                mContext.startActivity(intent);

                                //Toast.makeText(mContext,"删除作业成功啦",Toast.LENGTH_SHORT).show();

                                // Intent intent = new Intent(mContext,MainActivity.class);
                                //mContext.startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intent = new Intent(mContext,MainActivity.class);
//                        mContext.startActivity(intent);
                            }
                        })
                        .show();


            }
        });
        return view;
    }

    class ViewHolder {

        TextView mTextView;
        ImageView mButton;
        ImageView wButton;
    }
}

