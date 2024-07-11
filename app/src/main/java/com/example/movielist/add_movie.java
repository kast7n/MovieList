package com.example.movielist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        Activity current = this;
        EditText movieTitle = findViewById(R.id.etMovieTitle);
        EditText movieDuration = findViewById(R.id.etMovieDuration);
        EditText movieAuthor = findViewById(R.id.etMovieAuthor);
        Spinner movieGenreSpinner= findViewById(R.id.spnrGenre);
        FloatingActionButton fabShare = findViewById(R.id.fabShare);
        FloatingActionButton fabDelete = findViewById(R.id.fabDelete);
        FloatingActionButton fabYoutube = findViewById(R.id.fabYoutube);


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

        fabShare.setOnClickListener(new View.OnClickListener() {

            String strMovieTitle = movieTitle.getText().toString();
            String strMovieDuration = movieDuration.getText().toString();
            String strMovieGenre = (String)movieGenreSpinner.getSelectedItem();
            String strMovieAuthor = movieAuthor.getText().toString();

            String strMessage ="Movie Title: " + strMovieTitle + "\nGenre: " + strMovieDuration +" | Author: " + strMovieGenre + " | Duration" + strMovieAuthor;
            @Override
            public void onClick(View view) {
                String mimeType = "text/plain";
                ShareCompat.IntentBuilder
                        .from(current)
                        .setType(mimeType)
                        .setChooserTitle("Share With")
                        .setText(strMessage)
                        .startChooser();
            }
        });


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
                String strMovieAuthor = movieAuthor.getText().toString();


                Movie movie = new Movie(strMovieTitle,strMovieGenre,strMovieAuthor,strMovieDuration);
                if(action == 2 ){
                    Intent editIntent = new Intent();
                    editIntent.putExtra("editedMovie",movie);
                    editIntent.putExtra("ogMovieIndex", ogMovieIndex);
                    setResult(RESULT_OK,editIntent);
                    finish();
                }else{
                    Intent addIntent = new Intent();
                    addIntent.putExtra("newMovie",movie);
                    setResult(RESULT_OK,addIntent);
                    finish();
                }
            }
        });


    }
}