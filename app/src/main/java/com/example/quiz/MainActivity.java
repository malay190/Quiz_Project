package com.example.quiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.quiz.QuizActivity.EXTRA_HIGH_SCORE;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_QUIZ = 1;
    public static final String DIFFICULTY_LEVEL = "difficultyLevel";

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGH_SCORE = "keyHighScore";

    TextView textViewShowHighScore;
    Spinner spinner;

    private int highScore;
    Button buttonStartQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewShowHighScore = findViewById(R.id.text_view_highscore);
        spinner = findViewById(R.id.spinner_difficulty_level);

        final String[] difficulty = Questions.getAllDifficultyLevels();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, difficulty);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        loadHighScore();

        buttonStartQuiz = findViewById(R.id.button_start_quiz);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String difficultyLevel = spinner.getSelectedItem().toString();
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                intent.putExtra(DIFFICULTY_LEVEL,difficultyLevel);
                startActivityForResult(intent, REQUEST_CODE_QUIZ);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    int score = data.getIntExtra(EXTRA_HIGH_SCORE, 0);
                    if (score > highScore) {
                        updateHighScore(score);
                    }
                }
            }
        }
    }

    private void loadHighScore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highScore = prefs.getInt(KEY_HIGH_SCORE, 0);
        textViewShowHighScore.setText("High Score: " + highScore);
    }

    private void updateHighScore(int highScoreNew) {
        highScore = highScoreNew;
        textViewShowHighScore.setText("High Score:" + highScore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGH_SCORE, highScore);
        editor.apply();

    }
}
