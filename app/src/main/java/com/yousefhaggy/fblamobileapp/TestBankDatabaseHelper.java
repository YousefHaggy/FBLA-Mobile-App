package com.yousefhaggy.fblamobileapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class TestBankDatabaseHelper extends SQLiteOpenHelper {

  private static final int DATABASE_VESION=1;
  private static String DB_PATH="/data/data/com.yousefhaggy.fblamobileapp/databases/";
  private static final String DB_NAME ="TestBank.db";

  private SQLiteDatabase database;

  private final Context context;

    public TestBankDatabaseHelper(Context context) {
        super(context, DB_NAME,null,DATABASE_VESION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }
    @Override
    public synchronized void close(){
        if(database!=null)
            database.close();
        super.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void createDatabase() throws IOException{
        boolean dbExist=checkDatabase();
        if(dbExist){

        }else {
            this.getReadableDatabase();
            try{
                copyDatabase();
            }
            catch (IOException e){
                throw e;
            }

        }
    }

    public boolean checkDatabase(){
        SQLiteDatabase checkDB=null;
        try{
            String pathToDB=DB_PATH+ DB_NAME;
            checkDB=SQLiteDatabase.openDatabase(pathToDB,null,SQLiteDatabase.OPEN_READONLY);

        }catch(SQLException e){

        }
        if(checkDB!=null) {
            checkDB.close();
        }
        return checkDB!=null ? true :false;
    }
    public void openDatabase() throws SQLException
    {
        String pathToDB=DB_PATH+ DB_NAME;
        database=SQLiteDatabase.openDatabase(pathToDB,null,SQLiteDatabase.OPEN_READONLY);

    }
    public void copyDatabase() throws  IOException{
        InputStream inputStream=context.getAssets().open(DB_NAME);
        String outFileName=DB_PATH+DB_NAME;

        OutputStream outputStream=new FileOutputStream(outFileName);

        byte[] buffer= new byte[1024];
        int length;
        while((length=inputStream.read(buffer))>0)
        {
            outputStream.write(buffer,0,length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
   public List<String> getCategoryList(){

       Cursor cursor=database.rawQuery("SELECT DISTINCT CategoryName FROM QuestionTable",null);
       List<String> categoryNames=new ArrayList();
       while(cursor.moveToNext())
       {
           String categoryName=cursor.getString(cursor.getColumnIndexOrThrow("CategoryName"));
           categoryNames.add(categoryName);
       }
       cursor.close();
       return categoryNames;
   }
   public List<String> getTests(String category)
   {
       Cursor cursor=database.rawQuery("SELECT DISTINCT TestName FROM QuestionTable WHERE CategoryName='"+category+"'",null);
       List<String> testList=new ArrayList();
       while (cursor.moveToNext())
       {
           String test=cursor.getString(cursor.getColumnIndexOrThrow("TestName"));
           testList.add(test);
       }
       return testList;
   }
   public List<Question> getTestQuestions(String test)
   {
       Cursor cursor=database.rawQuery("SELECT * FROM QuestionTable WHERE TestName='"+test+"'",null);
       List<Question> questionList=new ArrayList();
       while(cursor.moveToNext())
       {
           String questionText=cursor.getString(cursor.getColumnIndexOrThrow("Question"));
           String questionChoices=cursor.getString(cursor.getColumnIndexOrThrow("QuestionChoices")).toLowerCase();
           String[] choices=questionChoices.split(";");
           String correctAnswer=cursor.getString(cursor.getColumnIndexOrThrow("CorrectAnswer")).toLowerCase();

           Question question=new Question(questionText,choices,correctAnswer);
           questionList.add(question);
       }
       return questionList;
   }
}
