package com.yousefhaggy.fblamobileapp;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TestBankDatabaseHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES="CREATE TABLE "+ TestBankContract.QuestionTable.TABLE_NAME+" ("
            + TestBankContract.QuestionTable._ID+" INTEGER PRIMARY KEY,"+ TestBankContract.QuestionTable.COLUMN_NAME_QUESTION+" TEXT,"
            + TestBankContract.QuestionTable.COLUMN_NAME_TEST+" TEXT,"
            + TestBankContract.QuestionTable.COLUMN_NAME_CATEGORY+" TEXT,"
            + TestBankContract.QuestionTable.COLUMN_NAME_CHOICES+" TEXT);" +
            "CREATE TABLE "+ TestBankContract.AnswerTable.TABLE_NAME+" ("
            + TestBankContract.AnswerTable.COLUMN_NAME_ANSWER+" TEXT,"
            + TestBankContract.AnswerTable.COLUMN_NAME_QUESTIONID+" INTEGER)";
    private  static  final String SQL_DELETE_ENTRIES=
            "DROP TABLE IF EXIST "+ TestBankContract.QuestionTable.TABLE_NAME+"; DROP TABLE IF EXISTS "+ TestBankContract.AnswerTable.TABLE_NAME;
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
