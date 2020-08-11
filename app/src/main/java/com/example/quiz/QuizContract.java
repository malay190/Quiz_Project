package com.example.quiz;

import android.provider.BaseColumns;


//This class is used as a container for different constant and later used for sqlite operation
public final class QuizContract {

    //private constructor used because we won't create the object of the class
    private QuizContract(){

    }


    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
    }
}
