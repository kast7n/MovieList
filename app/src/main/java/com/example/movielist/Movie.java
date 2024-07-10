package com.example.movielist;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Movie implements Parcelable {
    private String movieTitle;
    private String movieGenre;
    private String movieAuthor;
    private String movieDuration;

    public Movie(String movieTitle, String movieGenre, String movieAuthor, String movieDuration) {
        this.movieTitle = movieTitle;
        this.movieGenre = movieGenre;
        this.movieAuthor = movieAuthor;
        this.movieDuration = movieDuration;
    }

    protected Movie(Parcel in) {
        movieTitle = in.readString();
        movieGenre = in.readString();
        movieAuthor = in.readString();
        movieDuration = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

    public String getMovieAuthor() {
        return movieAuthor;
    }

    public void setMovieAuthor(String movieAuthor) {
        this.movieAuthor = movieAuthor;
    }

    public String getMovieDuration() {
        return movieDuration;
    }

    public void setMovieDuration(String movieDuration) {
        this.movieDuration = movieDuration;
    }

    @Override
    public String toString() {
        return "Movie Title: " + this.movieTitle + "\nGenre: " + this.movieGenre +" | Author: " + this.movieAuthor + " | Duration" + this.movieDuration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(movieTitle);
        parcel.writeString(movieGenre);
        parcel.writeString(movieAuthor);
        parcel.writeString(movieDuration);
    }
}
