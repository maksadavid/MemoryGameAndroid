package com.mks.memorygame.view;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mks.memorygame.R;
import com.mks.memorygame.model.Card;
import com.mks.memorygame.model.Game;

/**
 * Created by maksadavid on 2017. 02. 11..
 */
public class CardView extends RelativeLayout {

    private ImageView imageView;
    private ImageView backImageView;

    public CardView(Context context, Card card, GridLayout parent) {
        super(context);
        inflate(context,R.layout.list_item_card_view, this);
        imageView = (ImageView) findViewById(R.id.imageView);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        imageView.setImageResource(card.getDrawable());
        backImageView.setImageResource(R.drawable.colour0);
        int width = parent.getWidth()/ Game.NUMBER_OF_CARDS_PER_ROW;
        int height = parent.getHeight()/ Game.NUMBER_OF_CARDS_PER_COLUMN;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        imageView.setLayoutParams(params);
        backImageView.setLayoutParams(params);
        if (card.getState() == Card.CardState.FACE_UP) {
            backImageView.setAlpha(0f);
        }
        else if (card.getState() == Card.CardState.HIDDEN) {
            this.setAlpha(0);
        }
    }

    public void setCard(Card card) {
        AnimatorSet hideAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_hide);
        AnimatorSet showAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_show);
        switch (card.getState()) {
            case FACE_UP:
                hideAnimation.setTarget(backImageView);
                hideAnimation.start();
                showAnimation.setTarget(imageView);
                showAnimation.start();
                break;
            case FACE_DOWN:
                hideAnimation.setTarget(imageView);
                hideAnimation.start();
                showAnimation.setTarget(backImageView);
                showAnimation.start();
                break;
            case HIDDEN:
                this.setAlpha(0);
                break;
        }
    }
}
