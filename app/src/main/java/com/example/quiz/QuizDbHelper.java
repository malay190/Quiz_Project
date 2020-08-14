package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.quiz.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "QuizDbHelper";
    private static final String DATABASE_NAME = "Quiz.db";
    private static final int DATABASE_VERSION = 4;

    private SQLiteDatabase db;
    private static QuizDbHelper instance;


    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        Log.d(TAG, "onCreate");
        fillCategoriesTable();
        fillQuestionsTable();

    }

    //SQLiteOpenHelper Called when the database needs to be upgraded. The implementation should use this method to drop tables, add tables,
    // or do anything else it needs to upgrade to the new schema version.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        onCreate(db);
        Log.d(TAG, "onUpgrade ");

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable() {
        Categories c1 = new Categories("Programming");
        addCategory(c1);
        Categories c2 = new Categories("Geography");
        addCategory(c2);
        Categories c3 = new Categories("Math");
        addCategory(c3);
    }
    private void addCategory(Categories category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {
        Questions q1 = new Questions("Which of the following sorting algorithms can be used to sort a random linked list with minimum time complexity?",
                "Insertion sort", "Quick sort", "Merge sort", 3,
                Questions.DIFFICULTY_EASY, Categories.PROGRAMMING);
        addQuestion(q1);

        Questions q2 = new Questions("How many stacks are needed to implement a queue. Consider the situation where no other data structure like arrays, linked list is available to you.",
                "1", "2", "3", 2,
                Questions.DIFFICULTY_EASY, Categories.PROGRAMMING);
        addQuestion(q2);

        Questions q3 = new Questions("How many stacks are needed to implement a queue. Consider the situation where no other data structure like arrays, linked list is available to you.",
                "1", "2", "3", 2,
                Questions.DIFFICULTY_MEDIUM, Categories.PROGRAMMING);
        addQuestion(q3);

        Questions q4 = new Questions("Which of the following sorting algorithms can be used to sort a random linked list with minimum time complexity?",
                "Insertion sort", "Quick sort", "Merge sort", 3,
                Questions.DIFFICULTY_MEDIUM, Categories.PROGRAMMING);
        addQuestion(q4);

        Questions q5 = new Questions("How many stacks are needed to implement a queue. Consider the situation where no other data structure like arrays, linked list is available to you.",
                "1", "2", "3", 2,
                Questions.DIFFICULTY_HARD, Categories.PROGRAMMING);
        addQuestion(q5);

        Questions q6 = new Questions("Which of the following sorting algorithms can be used to sort a random linked list with minimum time complexity?",
                "Insertion sort", "Quick sort", "Merge sort", 3,
                Questions.DIFFICULTY_HARD, Categories.PROGRAMMING);
        addQuestion(q6);

        Questions q7 = new Questions("Capital of india",
                "Patna", "New Delhi", "chattisgarh", 2,
                Questions.DIFFICULTY_EASY, Categories.GEOGRAPHY);
        addQuestion(q7);

        Questions q8 = new Questions("Capital of Bihar",
                "Patna", "Muzaffarpur", "Darbhanga", 2,
                Questions.DIFFICULTY_EASY, Categories.GEOGRAPHY);
        addQuestion(q8);

        Questions q9 = new Questions("Which passes make way to the land route between Kailash and the Manasarovar?",
                "Mana Pass", "Rohtas pass", "Nathula Pass", 1,
                Questions.DIFFICULTY_MEDIUM, Categories.GEOGRAPHY);
        addQuestion(q9);

        Questions q10 = new Questions("Which of the following is the world’s largest peninsula?",
                "India", "South Africa", "Arbia", 3,
                Questions.DIFFICULTY_MEDIUM, Categories.GEOGRAPHY);
        addQuestion(q10);

        Questions q11 = new Questions(" Which of the following is the largest Archipelago in the world?",
                "Andaman & Nicobar Island", "Malaysia", "Maldvis", 3,
                Questions.DIFFICULTY_HARD, Categories.GEOGRAPHY);
        addQuestion(q11);

        Questions q12 = new Questions("Which of the following geographical term related with the piece of sub-continental land that is surrounded by water?",
                "Peninsula", "Gulf", "Island", 3,
                Questions.DIFFICULTY_HARD, Categories.GEOGRAPHY);
        addQuestion(q12);

        Questions q13 = new Questions("SquareRoot 0f 49",
                "7", "6", "5", 3,
                Questions.DIFFICULTY_EASY, Categories.MATH);
        addQuestion(q13);

        Questions q14 = new Questions(" 4+5",
                "9", "8", "7", 9,
                Questions.DIFFICULTY_EASY, Categories.MATH);
        addQuestion(q14);

        Questions q15 = new Questions("Which of the following is odd?",
                "6", "5", "4", 2,
                Questions.DIFFICULTY_MEDIUM, Categories.MATH);
        addQuestion(q15);

        Questions q16 = new Questions("Which of the following is even?",
                "7", "9", "6", 3,
                Questions.DIFFICULTY_MEDIUM, Categories.MATH);
        addQuestion(q16);

        Questions q17 = new Questions("Which of the following is odd?",
                "7", "4", "0", 1,
                Questions.DIFFICULTY_HARD, Categories.MATH);
        addQuestion(q17);

        Questions q18 = new Questions("Which of the following is even?",
                "7", "2", "11", 3,
                Questions.DIFFICULTY_HARD, Categories.MATH);
        addQuestion(q18);

        Questions q19 = new Questions("Non existing, Easy: A is correct",
                "A", "B", "C", 1,
                Questions.DIFFICULTY_EASY, 4);
        addQuestion(q19);
        Questions q20 = new Questions("Non existing, Medium: B is correct",
                "A", "B", "C", 2,
                Questions.DIFFICULTY_MEDIUM, 5);
        addQuestion(q20);

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
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Categories> getAllCategories() {
        List<Categories> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Categories category = new Categories();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }
        c.close();
        return categoryList;
    }

    public ArrayList<Questions> getAllQuestion() {
        ArrayList<Questions> questionsList = new ArrayList<>();
        db = getReadableDatabase();
        Log.d(TAG, "getAllQuestion: db created ");
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Questions question = new Questions();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionsList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionsList;
    }

    public ArrayList<Questions> getQuestions(int categoryID, String difficulty) {
        ArrayList<Questions> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};
        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            do {
                Questions question = new Questions();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

}
