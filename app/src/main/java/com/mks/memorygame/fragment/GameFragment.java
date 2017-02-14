package com.mks.memorygame.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;

import com.mks.memorygame.R;
import com.mks.memorygame.view.CardView;
import com.mks.memorygame.activity.MemoryGameActivity;
import com.mks.memorygame.model.Card;
import com.mks.memorygame.model.Game;

/**
 * Created by davidmaksa on 18.01.17.
 */

public class GameFragment extends Fragment {

    private static final String GAME_KEY = "GAME";
    private Game game;
    private GridLayout gridLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            game = savedInstanceState.getParcelable(GAME_KEY);
        }
        else {
            game = new Game();
        }
        final String scoreString = getActivity().getString(R.string.score);
        getActivity().setTitle(scoreString + game.getScore());
        game.setOnCardStateChangeListener(new Game.OnCardStateChangeListener() {
            @Override
            public void onCardStateChange(Card card) {
                CardView cardView = (CardView) gridLayout.getChildAt(card.getId());
                cardView.setCard(card);
                getActivity().setTitle(scoreString + game.getScore());
            }

            @Override
            public void onGameFinished(int score) {
                ((MemoryGameActivity) getActivity()).presentCongratulationFragmentWithScore(score);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(GAME_KEY, game);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game, container, false);
        gridLayout = (GridLayout) v.findViewById(R.id.gridLayout);
        gridLayout.setColumnCount(Game.NUMBER_OF_CARDS_PER_COLUMN);
        gridLayout.setRowCount(Game.NUMBER_OF_CARDS_PER_ROW);
        gridLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                updateGridLayout();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    gridLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    gridLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
        return v;
    }

    private void updateGridLayout() {
        gridLayout.removeAllViews();
        for (int i=0; i<Game.NUMBER_OF_CARDS_PER_COLUMN; i++ ) {
            for (int j = 0; j < Game.NUMBER_OF_CARDS_PER_ROW; j++) {
                final Card card = game.getCards().get(i * Game.NUMBER_OF_CARDS_PER_COLUMN + j);
                CardView cardView = new CardView(getContext(), card, gridLayout);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        game.selectCard(card);
                    }
                });
                gridLayout.addView(cardView);
            }
        }
    }

}
