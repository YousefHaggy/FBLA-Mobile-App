package com.yousefhaggy.fblamobileapp;

import android.provider.BaseColumns;

public final class TestBankContract {
    //  Making the constructor private prevents someone from accidentally
    // instantiating this class
    private TestBankContract(){}
    public static class QuestionTable implements BaseColumns
    {
        public static final String TABLE_NAME="QuestionTable";
        public static final String COLUMN_NAME_QUESTION="Question";
        public static final String COLUMN_NAME_TEST="TestName";
        public static final String COLUMN_NAME_CATEGORY="CategoryName";
        public static final String COLUMN_NAME_CHOICES="QuestionChoices";

    }
    public static class AnswerTable implements BaseColumns
    {
        public static final String TABLE_NAME="AnswerTable";
        public static final String COLUMN_NAME_QUESTIONID="QuestionID";
        public static final String COLUMN_NAME_ANSWER="QuestionAnswer";


    }
}
