package com.example.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class add_movie extends AppCompatActivity {

    private Integer ogMovieIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText movieTitle = findViewById(R.id.etMovieTitle);
        EditText movieDuration = findViewById(R.id.etMovieDuration);
        EditText movieAuthor = findViewById(R.id.etMovieAuthor);
        Spinner movieGenreSpinner= findViewById(R.id.spnrGenre);


        Button btnCancel = findViewById(R.id.btnCancelMovie);
        Button btnSave = findViewById(R.id.btnSaveMovie);

        Movie recievedMovie;
        Intent recInt = getIntent();
        int action = recInt.getIntExtra("action",0);
        if(action == 2){
            recievedMovie = recInt.getParcelableExtra("selectedMovie");
            ogMovieIndex = recInt.getIntExtra("ogMovieIndex",0);
            if(recievedMovie != null){
                movieTitle.setText(recievedMovie.getMovieTitle());
                movieDuration.setText(recievedMovie.getMovieDuration());
                movieAuthor.setText(recievedMovie.getMovieAuthor());
                movieGenreSpinner.setSelection(((ArrayAdapter)movieGenreSpinner.getAdapter()).getPosition(recievedMovie.getMovieGenre()));
            }
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strMovieTitle = movieTitle.getText().toString();
                String strMovieDuration = movieDuration.getText().toString();
                String strMovieGenre = (String)movieGenreSpinner.getSelectedItem();
                String strMoveAuthour = movieAuthor.getText().toString();


                Task task = new Task(strTaskTitle,intTaskDuration,strTaskDescription,strTaskDueDate,strTaskCategory);
                if(action == 2 ){
                    Intent editIntent = new Intent();
                    editIntent.putExtra("editedTask",task);
                    editIntent.putExtra("ogTaskIndex", ogTaskIndex);
                    setResult(RESULT_OK,editIntent);
                    finish();
                }else{
                    Intent addIntent = new Intent();
                    addIntent.putExtra("newTask",task);
                    setResult(RESULT_OK,addIntent);
                    finish();
                }
            }
        });


    }
}