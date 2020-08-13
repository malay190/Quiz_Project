package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.quiz.QuizContract.*;

import java.util.ArrayList;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "QuizDbHelper";
    private static final String DATABASE_NAME = "Quiz.db";
    private static final int DATABASE_VERSION = 2;

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
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        Log.d(TAG, "onCreate");
        fillQuestionsTable();

    }

    //SQLiteOpenHelper Called when the database needs to be upgraded. The implementation should use this method to drop tables, add tables,
    // or do anything else it needs to upgrade to the new schema version.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
        Log.d(TAG, "onUpgrade ");

    }

    private void fillQuestionsTable() {
        Questions q1 = new Questions("Easy: A is correct",
                "A", "B", "C", 3, Questions.DIFFICULTY_EASY);
        addQuestion(q1);
        Questions q2 = new Questions("Medium: B is correct",
                "A", "B", "C", 2, Questions.DIFFICULTY_MEDIUM);
        addQuestion(q2);
        Questions q3 = new Questions("Medium: C is correct",
                "A", "B", "C", 3, Questions.DIFFICULTY_MEDIUM);
        addQuestion(q3);
        Questions q4 = new Questions("Hard: A is correct",
                "A", "B", "C", 1, Questions.DIFFICULTY_HARD);
        addQuestion(q4);
        Questions q5 = new Questions("Hard: B is correct",
                "A", "B", "C", 2, Questions.DIFFICULTY_HARD);
        addQuestion(q5);
        Questions q6 = new Questions("Hard: C is correct",
                "A", "B", "C", 3, Questions.DIFFICULTY_HARD);
        addQuestion(q6);

    }

    private void addQuestion(Questions question) {

        //Creates an empty set of values using the default initial size
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public ArrayList<Questions> getAllQuestion() {
        ArrayList<Questions> questionsList = new ArrayList<>();
        db = getReadableDatabase();
        Log.d(TAG, "getAllQuestion: db created ");
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Questions question = new Questions();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionsList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionsList;
    }

    public ArrayList<Questions> getQuestions(String difficulty) {
        ArrayList<Questions> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ?", selectionArgs);
        if (c.moveToFirst()) {
            do {
                Questions question = new Questions();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

}
