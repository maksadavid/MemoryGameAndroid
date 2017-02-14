package com.mks.memorygame.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.mks.memorygame.R;
import com.mks.memorygame.model.Score;

/**
 * Created by maksadavid on 2017. 02. 13..
 */
public class CongratulationFragment extends Fragment {

    public interface OnFormCompletedListener {
        void onFormCompletedWithScore(Score score);
    }

    public static final String GAME_SCORE_KEY = "GameScore";
    private int gameScore;
    private EditText nameEditText;
    private OnFormCompletedListener formCompletedListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            formCompletedListener = (OnFormCompletedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFormCompletedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        gameScore = getArguments().getInt(GAME_SCORE_KEY);
        View v = inflater.inflate(R.layout.fragment_congratulation, container, false);
        TextView titleTextView = (TextView) v.findViewById(R.id.textView1);
        titleTextView.setText(String.format(getString(R.string.congratulations), gameScore));
        nameEditText = (EditText) v.findViewById(R.id.editText);
        nameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String name = nameEditText.getText().toString();
                    if (name != null && name.length() > 0 && formCompletedListener != null) {
                        formCompletedListener.onFormCompletedWithScore(new Score(name, gameScore));
                    }
                    return true;
                }
                return false;
            }
        });

        return v;
    }





}
