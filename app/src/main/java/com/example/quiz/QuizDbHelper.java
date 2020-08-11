package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.quiz.QuizContract.*;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Quiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;


    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);

    }

    private void fillQuestionsTable() {
        Questions q1 = new Questions("A is correct", "A", "B", "C", 1);
        addQuestion(q1);
        Questions q2 = new Questions("B is correct", "A", "B", "C", 2);
        addQuestion(q2);
        Questions q3 = new Questions("C is correct", "A", "B", "C", 3);
        addQuestion(q3);
        Questions q4 = new Questions("A is correct again", "A", "B", "C", 1);
        addQuestion(q4);
        Questions q5 = new Questions("B is correct again", "A", "B", "C", 2);
        addQuestion(q5);
    }

    private void addQuestion(Questions question) {

        //Creates an empty set of values using the default initial size
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

}
