package com.yousefhaggy.fblamobileapp;

import android.provider.BaseColumns;

public final class TestBankContract {
    //  Making the constructor private prevents someone from accidentally
    // instantiating this class. This class just holds some important static
    // strings for accessing the SQLite database
    private TestBankContract(){}
    public static class QuestionTable implements BaseColumns
    {
        public static final String TABLE_NAME="QuestionTable";
        public static final String COLUMN_NAME_QUESTION="Question";
        public static final String COLUMN_NAME_TEST="TestName";
        public static final String COLUMN_NAME_CATEGORY="CategoryName";
        public static final String COLUMN_NAME_CHOICES="QuestionChoices";

    }

}
