package com.mks.memorygame.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.mks.memorygame.R;

/**
 * Created by davidmaksa on 28.01.17.
 */


public class Card implements Parcelable {

    public enum CardState {
        FACE_DOWN,
        FACE_UP,
        HIDDEN
    }

    private int id;
    private int value;
    private CardState state;

    public Card(int id, int value) {
        this.id = id;
        this.value = value;
        this.state = CardState.FACE_DOWN;
    }

    protected Card(Parcel in) {
        id = in.readInt();
        value = in.readInt();
        state = CardState.valueOf(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(value);
        dest.writeString(state.name());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public CardState getState() {
        return state;
    }

    public void setState(CardState state) {
        this.state = state;
    }

    public int getDrawable() {
        int drawable;
        switch (value) {
            case 1:
                drawable = R.drawable.colour1;
                break;
            case 2:
                drawable = R.drawable.colour2;
                break;
            case 3:
                drawable = R.drawable.colour3;
                break;
            case 4:
                drawable = R.drawable.colour4;
                break;
            case 5:
                drawable = R.drawable.colour5;
                break;
            case 6:
                drawable = R.drawable.colour6;
                break;
            case 7:
                drawable = R.drawable.colour7;
                break;
            case 8:
                drawable = R.drawable.colour8;
                break;
            default:
                drawable = R.drawable.colour0;
                break;
        }

        return drawable;
    }
}
