package com.mks.memorygame.model;


import android.content.Intent;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by davidmaksa on 28.01.17.
 */

public class Game implements Parcelable {

    public interface OnCardStateChangeListener {
        void onCardStateChange(Card card);
        void onGameFinished(int score);
    }

    public static final int NUMBER_OF_CARDS = 16;
    public static final int NUMBER_OF_CARDS_PER_ROW = 4;
    public static final int NUMBER_OF_CARDS_PER_COLUMN = 4;
    private OnCardStateChangeListener onCardStateChangeListener;
    private ArrayList<Card> cards;
    private Card selectedCard;
    private Boolean isReadyForNewSelection;
    private int score;

    public Game() {
        ArrayList<Integer> randomValues = new ArrayList<>();
        for (int i=0; i < NUMBER_OF_CARDS; i++){
            randomValues.add(i/2 + 1);
        }
        long seed = System.nanoTime();
        Collections.shuffle(randomValues, new Random(seed));

        cards = new ArrayList<Card>();
        for (int i=0; i < NUMBER_OF_CARDS; i++) {
            cards.add(new Card(i, randomValues.get(i)));
        }

        isReadyForNewSelection = true;
        score = 0;
    }

    protected Game(Parcel in) {
        cards = in.createTypedArrayList(Card.CREATOR);
        selectedCard = in.readParcelable(Card.class.getClassLoader());
        score = in.readInt();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(cards);
        dest.writeParcelable(selectedCard, flags);
        dest.writeInt(score);
    }

    public void setOnCardStateChangeListener(OnCardStateChangeListener onCardStateChangeListener) {
        this.onCardStateChangeListener = onCardStateChangeListener;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void selectCard(final Card card) {

        if (card.getState() != Card.CardState.FACE_DOWN || !isReadyForNewSelection) {
            return;
        }

        if (selectedCard == null) {
            card.setState(Card.CardState.FACE_UP);
            cards.set(card.getId(), card);
            selectedCard = card;
            onCardStateChangeListener.onCardStateChange(card);
            return;
        }

        if (selectedCard.getValue() == card.getValue()) {
            card.setState(Card.CardState.FACE_UP);
            cards.set(card.getId(), card);
            onCardStateChangeListener.onCardStateChange(card);
            isReadyForNewSelection = false;
            score += 2;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    card.setState(Card.CardState.HIDDEN);
                    cards.set(card.getId(), card);
                    onCardStateChangeListener.onCardStateChange(card);
                    selectedCard.setState(Card.CardState.HIDDEN);
                    cards.set(selectedCard.getId(), selectedCard);
                    onCardStateChangeListener.onCardStateChange(selectedCard);
                    selectedCard = null;
                    isReadyForNewSelection = true;
                    checkIfGameIsFinished();
                }
            }, 1000);
        }
        else {
            card.setState(Card.CardState.FACE_UP);
            cards.set(card.getId(), card);
            onCardStateChangeListener.onCardStateChange(card);
            isReadyForNewSelection = false;
            score--;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    card.setState(Card.CardState.FACE_DOWN);
                    cards.set(card.getId(), card);
                    onCardStateChangeListener.onCardStateChange(card);
                    selectedCard.setState(Card.CardState.FACE_DOWN);
                    cards.set(selectedCard.getId(), selectedCard);
                    onCardStateChangeListener.onCardStateChange(selectedCard);
                    selectedCard = null;
                    isReadyForNewSelection = true;
                }
            }, 1000);
        }
    }

    void checkIfGameIsFinished() {
        Boolean gameIsFinished = true;
        for (Card card : cards) {
            if (card.getState() != Card.CardState.HIDDEN) {
                gameIsFinished = false;
            }
        }
        if (gameIsFinished) {
            onCardStateChangeListener.onGameFinished(score);
        }
    }
}
