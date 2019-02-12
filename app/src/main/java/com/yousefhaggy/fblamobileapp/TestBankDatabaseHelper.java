package com.yousefhaggy.fblamobileapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class TestBankDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VESION = 1;
    private static String DB_PATH = "/data/data/com.yousefhaggy.fblamobileapp/databases/";
    private static final String DB_NAME = "TestBank.db";

    private SQLiteDatabase database;

    private final Context context;

    public TestBankDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VESION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public synchronized void close() {
        if (database != null)
            database.close();
        super.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();
        if (dbExist) {

        } else {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e) {
                throw e;
            }

        }
    }

    public boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            String pathToDB = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(pathToDB, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLException e) {

        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    public void openDatabase() throws SQLException {
        String pathToDB = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(pathToDB, null, SQLiteDatabase.OPEN_READWRITE);

    }

    public void copyDatabase() throws IOException {
        InputStream inputStream = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;

        OutputStream outputStream = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public List<String> getCategoryList() {

        Cursor cursor = database.rawQuery("SELECT DISTINCT CategoryName FROM QuestionTable", null);
        List<String> categoryNames = new ArrayList();
        while (cursor.moveToNext()) {
            String categoryName = cursor.getString(cursor.getColumnIndexOrThrow("CategoryName"));
            categoryNames.add(categoryName);
        }
        cursor.close();
        return categoryNames;
    }

    public List<String> getTests(String category) {
        Cursor cursor = database.rawQuery("SELECT DISTINCT TestName FROM QuestionTable WHERE CategoryName='" + category + "'", null);
        List<String> testList = new ArrayList();
        while (cursor.moveToNext()) {
            String test = cursor.getString(cursor.getColumnIndexOrThrow("TestName"));
            testList.add(test);
        }
        cursor.close();
        return testList;
    }

    public List<Question> getTestQuestions(String test) {
        Cursor cursor = database.rawQuery("SELECT * FROM QuestionTable WHERE TestName='" + test + "'", null);
        List<Question> questionList = new ArrayList();
        while (cursor.moveToNext()) {
            String questionText = cursor.getString(cursor.getColumnIndexOrThrow("Question"));
            String questionChoices = cursor.getString(cursor.getColumnIndexOrThrow("QuestionChoices")).toLowerCase();
            String[] choices;
            if(questionChoices.contains(";;"))
                choices = questionChoices.split(";;");
            else {
               choices = questionChoices.split(";");
            }
            String correctAnswer = cursor.getString(cursor.getColumnIndexOrThrow("CorrectAnswer")).toLowerCase();

            Question question = new Question(questionText, choices, correctAnswer);
            questionList.add(question);
        }
        cursor.close();
        return questionList;
    }

    public List<Question> getRandomQuizQuestions(String category) {
        Cursor cursor = database.rawQuery("SELECT * FROM QuestionTable WHERE CategoryName='" + category + "'", null);
        List<Question> questionList = new ArrayList();
        while (cursor.moveToNext()) {
            String questionText = cursor.getString(cursor.getColumnIndexOrThrow("Question"));
            String questionChoices = cursor.getString(cursor.getColumnIndexOrThrow("QuestionChoices")).toLowerCase();
            String[] choices;
            if(questionChoices.contains(";;"))
                choices = questionChoices.split(";;");
            else {
                choices = questionChoices.split(";");
            }            String correctAnswer = cursor.getString(cursor.getColumnIndexOrThrow("CorrectAnswer")).toLowerCase();
            Question question = new Question(questionText, choices, correctAnswer);
            questionList.add(question);
        }
        List<Question> finalQuestionList = new ArrayList<>();
        Random generator = new Random();
        for (int i = 0; i < 10; i++) {
            int randomIndex = generator.nextInt(questionList.size());
            finalQuestionList.add(questionList.get(randomIndex));
            questionList.remove(randomIndex);
        }
        cursor.close();
        return finalQuestionList;
    }

    public List<ScoreHistoryItem> getRecentCategories() {
        Cursor cursor = database.rawQuery("SELECT DISTINCT CategoryName FROM TestHistory", null);
        List<String> categoryNames = new ArrayList<>();
        while (cursor.moveToNext()) {
            categoryNames.add(cursor.getString(cursor.getColumnIndexOrThrow("CategoryName")));
        }
        cursor.close();
        List<ScoreHistoryItem> categoryHistoryList = new ArrayList<>();

        int listSize = categoryNames.size();
        List<String> finalCategoryNames = new ArrayList<>();
        if (listSize > 4) {
            finalCategoryNames = categoryNames.subList(listSize - 4, listSize);
        } else {
            finalCategoryNames = categoryNames;
        }
        for (String s : finalCategoryNames) {
            cursor = database.rawQuery("SELECT TestScore FROM TestHistory WHERE CategoryName='" + s + "'", null);
            double averageTestScore = 0;
            int testCount = 0;
            while (cursor.moveToNext()) {
                averageTestScore += cursor.getFloat(cursor.getColumnIndexOrThrow("TestScore"));
                testCount++;
            }
            cursor.close();
            averageTestScore = averageTestScore / testCount;
            categoryHistoryList.add(new ScoreHistoryItem(s, averageTestScore));
        }
        return categoryHistoryList;
    }
    public List<ScoreHistoryItem> getRecentTestScores(){
        Cursor cursor=database.rawQuery("SELECT * FROM TestHistory",null);
        List<ScoreHistoryItem> testHistoryList=new ArrayList<>();
        while(cursor.moveToNext())
        {
            testHistoryList.add(new ScoreHistoryItem(cursor.getString(cursor.getColumnIndexOrThrow("TestName")),cursor.getDouble(cursor.getColumnIndexOrThrow("TestScore"))));
        }
        int testListSize=testHistoryList.size();
        if(testListSize>4)
        {
            testHistoryList=testHistoryList.subList(testListSize-4,testListSize);
        }
        cursor.close();
        return  testHistoryList;
    }
    public void updateTestScoreHistory(String testName,String categoryName,double testScore)
    {
        database.execSQL("INSERT INTO TestHistory(TestName,CategoryName,TestScore) values('"+testName+"','"+categoryName+"',"+testScore+")");
    }
    public LevelInfo getLevelInfo()
    {
        Cursor cursor=database.rawQuery("SELECT * FROM LevelTable",null);
        cursor.moveToFirst();
        LevelInfo levelInfo=new LevelInfo(cursor.getInt(cursor.getColumnIndexOrThrow("Level")),cursor.getInt(cursor.getColumnIndexOrThrow("MaxExp")),cursor.getInt(cursor.getColumnIndexOrThrow("CurrentExp")));
        cursor.close();
        return levelInfo;
    }
    public void updateLevel(int changeInExp)
    {
        Cursor cursor=database.rawQuery("SELECT * FROM LevelTable",null);
        cursor.moveToFirst();
        int currentLevel= cursor.getInt(cursor.getColumnIndexOrThrow("Level"));
        int maxExp=cursor.getInt(cursor.getColumnIndexOrThrow("MaxExp"));
        int currentExp=cursor.getInt(cursor.getColumnIndexOrThrow("CurrentExp"));
        cursor.close();
        currentExp=changeInExp+currentExp;
        while (currentExp>=maxExp) {
            currentExp = currentExp - maxExp;
            maxExp+=100;
            currentLevel+=1;
        }
        database.execSQL("UPDATE LevelTable SET Level="+currentLevel+", CurrentExp="+currentExp+", MaxExp="+maxExp);
    }
    public Map<String,Integer> getAchievementStats(){
        Map<String,Integer> achievementStats=new HashMap<String, Integer>();
        Cursor cursor=database.rawQuery("SELECT * FROM TestHistory",null);
        int numOfTestsTaken=0;
        int highestTestScore=0;
        HashSet<String> categoryList=new HashSet<>();
        while(cursor.moveToNext()){
        int testScore=(int)Math.round(cursor.getDouble(cursor.getColumnIndexOrThrow("TestScore"))*100);
        if(testScore>highestTestScore)
        {
            highestTestScore=testScore;
        }
        String categoryName=cursor.getString(cursor.getColumnIndexOrThrow("CategoryName"));
        if(!categoryList.contains(categoryName))
        {
            categoryList.add(categoryName);
        }
        numOfTestsTaken+=1;
        }
        cursor.close();
        achievementStats.put("HighestTestScore",highestTestScore);
        achievementStats.put("NumberOfCategories",categoryList.size());
        achievementStats.put("NumberOfTestsTaken",numOfTestsTaken);
        return achievementStats;
    }

}
