package com.udacity.movieapp.common.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by eslam on 12/2/17.
 */

public class Reviews implements Parcelable {
    @SerializedName("results")
    private ArrayList<Review> listReviews;

    protected Reviews(Parcel in) {
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };

    public ArrayList<Review> getListReviews() {
        return listReviews;
    }

    public void setListReviews(ArrayList<Review> listReviews) {
        this.listReviews = listReviews;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public class Review implements Parcelable {
        @SerializedName("author")
        private String reviewAuthor;
        @SerializedName("content")
        private String reviewContent;

        protected Review(Parcel in) {
            reviewAuthor = in.readString();
            reviewContent = in.readString();
        }

        public final Creator<Review> CREATOR = new Creator<Review>() {
            @Override
            public Review createFromParcel(Parcel in) {
                return new Review(in);
            }

            @Override
            public Review[] newArray(int size) {
                return new Review[size];
            }
        };

        public String getReviewAuthor() {
            return reviewAuthor;
        }

        public void setReviewAuthor(String reviewAuthor) {
            this.reviewAuthor = reviewAuthor;
        }

        public String getReviewContent() {
            return reviewContent;
        }

        public void setReviewContent(String reviewContent) {
            this.reviewContent = reviewContent;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(reviewAuthor);
            parcel.writeString(reviewContent);
        }
    }

}
