package com.mks.memorygame.fragment;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import com.mks.memorygame.R;
import com.mks.memorygame.activity.MemoryGameActivity;
import com.mks.memorygame.database.DatabaseManager;

/**
 * Created by maksadavid on 2017. 02. 14..
 */
public class ScoreListFragment extends ListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new SimpleCursorAdapter(getActivity(),
                R.layout.list_item_score,
                ((MemoryGameActivity) getActivity()).getDatabaseManager().getAllEntries(),
                new String[]{DatabaseManager.COLUMN_NAME, DatabaseManager.COLUMN_SCORE},
                new int[] {R.id.textView1, R.id.textView2},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER));

    }
}
