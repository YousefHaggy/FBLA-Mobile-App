package com.yousefhaggy.fblamobileapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TestBankDatabaseHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES="CREATE TABLE "+ TestBankContract.QuestionTable.TABLE_NAME+" ("
            + TestBankContract.QuestionTable._ID+" INTEGER PRIMARY KEY,"+ TestBankContract.QuestionTable.COLUMN_NAME_QUESTION+" TEXT,"
            + TestBankContract.QuestionTable.COLUMN_NAME_TEST+" TEXT,"
            + TestBankContract.QuestionTable.COLUMN_NAME_CATEGORY+" TEXT,"
            + TestBankContract.QuestionTable.COLUMN_NAME_CHOICES+" TEXT);\n" +
            "CREATE TABLE "+ TestBankContract.AnswerTable.TABLE_NAME+" ("
            + TestBankContract.AnswerTable.COLUMN_NAME_ANSWER+" TEXT,"
            + TestBankContract.AnswerTable.COLUMN_NAME_QUESTIONID+" INTEGER)";
    private  static final String SQL_DELETE_ENTRIES=
            "DROP TABLE IF EXISTS "+ TestBankContract.QuestionTable.TABLE_NAME+";\n DROP TABLE IF EXISTS "+ TestBankContract.AnswerTable.TABLE_NAME;

  public static final int DATABASE_VESION=1;
  public static final String DATABASE_NAME="TestBank.db";

    public TestBankDatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VESION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String[] queries=SQL_CREATE_ENTRIES.split(";\n");
        for(String q: queries)
            sqLiteDatabase.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String[] queries=SQL_DELETE_ENTRIES.split(";\n");
        for(String q: queries)
            sqLiteDatabase.execSQL(q);
        onCreate(sqLiteDatabase);
    }
    //TO-DO
    public boolean isDatabaseEmpty(){
        return  false;
    }
}
