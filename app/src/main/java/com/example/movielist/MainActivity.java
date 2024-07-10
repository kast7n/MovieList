package com.example.movielist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Movie> movieList;
    private ArrayList<String> movieInfoList;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Activity current = this;
        movieList = new ArrayList<>();
        movieInfoList = new ArrayList<>();
        if(savedInstanceState != null){
            movieInfoList = savedInstanceState.getStringArrayList("moveListInfo");
            movieList = savedInstanceState.getParcelableArrayList("movieList");
        }
        adapter = new ArrayAdapter<>(current, android.R.layout.simple_list_item_1,movieInfoList);
        ListView lvMovies = findViewById(R.id.listMovie);
        lvMovies.setAdapter(adapter);

        FloatingActionButton btnAddMovie = findViewById(R.id.btnAddMovie);

        btnAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(current, add_movie.class);

                intent.putExtra("action",1);
                startActivityForResult(intent,1);
            }
        });

        SearchView svSearchTask = findViewById(R.id.searchMovie);
        svSearchTask.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return true;
            }
        });

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie currMovie = movieList.get(i);
                Intent intentEdit = new Intent(current, add_movie.class);
                intentEdit.putExtra("selectedMovie",currMovie);
                intentEdit.putExtra("ogTaskIndex",i);
                intentEdit.putExtra("action",2);
                startActivityForResult(intentEdit,2);
            }


        });



    }
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("moveListInfo",movieInfoList);
        outState.putParcelableArrayList("movieList",movieList);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Movie newMovie = data.getParcelableExtra("newMovie");
                movieList.add(newMovie);
                movieInfoList.add(newMovie.toString());
                adapter.notifyDataSetChanged();
            }
        }
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                Movie editedMovie = data.getParcelableExtra("editedMovie");
                int ogMovieIndex = data.getIntExtra("ogMovieIndex",0);
                movieList.remove(ogMovieIndex);
                movieList.add(editedMovie);

                movieInfoList.remove(ogMovieIndex);
                movieInfoList.add(editedMovie.toString());
                adapter.notifyDataSetChanged();
            }
        }
    }

}