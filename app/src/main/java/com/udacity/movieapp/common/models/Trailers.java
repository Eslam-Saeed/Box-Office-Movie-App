package com.udacity.movieapp.common.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.udacity.movieapp.common.helpers.Constants;

import java.util.ArrayList;

/**
 * Created by eslam on 12/2/17.
 */

public class Trailers implements Parcelable{
    @SerializedName("results")
    private ArrayList<Trailer> listTrailers;

    protected Trailers(Parcel in) {
    }

    public static final Creator<Trailers> CREATOR = new Creator<Trailers>() {
        @Override
        public Trailers createFromParcel(Parcel in) {
            return new Trailers(in);
        }

        @Override
        public Trailers[] newArray(int size) {
            return new Trailers[size];
        }
    };

    public ArrayList<Trailer> getListTrailers() {
        return listTrailers;
    }

    public void setListTrailers(ArrayList<Trailer> listTrailers) {
        this.listTrailers = listTrailers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public class Trailer implements Parcelable{
        @SerializedName("name")
        private String trailerName;
        @SerializedName("key")
        private String trailerURL;

        private String trailerImagePath;

        protected Trailer(Parcel in) {
            trailerName = in.readString();
            trailerURL = in.readString();
            trailerImagePath = in.readString();
        }

        public final Creator<Trailer> CREATOR = new Creator<Trailer>() {
            @Override
            public Trailer createFromParcel(Parcel in) {
                return new Trailer(in);
            }

            @Override
            public Trailer[] newArray(int size) {
                return new Trailer[size];
            }
        };

        public String getTrailerName() {
            return trailerName;
        }

        public void setTrailerName(String trailerName) {
            this.trailerName = trailerName;
        }

        public String getTrailerURL() {
            return trailerURL;
        }

        public void setTrailerURL(String trailerURL) {
            this.trailerURL = trailerURL;
        }

        public String getTrailerImagePath() {
            return Constants.TRAILER_IMAGE_PRE + trailerURL + Constants.TRAILER_IMAGE_POST;
        }

        public void setTrailerImagePath(String trailerImagePath) {
            this.trailerImagePath = trailerImagePath;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(trailerName);
            parcel.writeString(trailerURL);
            parcel.writeString(trailerImagePath);
        }
    }


}
