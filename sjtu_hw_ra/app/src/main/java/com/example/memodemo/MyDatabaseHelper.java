package com.example.memodemo;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper{
    public static final String CREATE_BOOK = "create table Book("
            +"id integer primary key autoincrement,"
            +"time text,"
            +"things text)";
    private Context mContext;

    public MyDatabaseHelper(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }



    /**
     * 当数据库创建时回调的函数
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
