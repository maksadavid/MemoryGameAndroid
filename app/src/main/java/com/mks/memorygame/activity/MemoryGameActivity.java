package com.mks.memorygame.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mks.memorygame.R;
import com.mks.memorygame.database.DatabaseManager;
import com.mks.memorygame.fragment.CongratulationFragment;
import com.mks.memorygame.fragment.GameFragment;
import com.mks.memorygame.fragment.ScoreListFragment;
import com.mks.memorygame.model.Score;

public class MemoryGameActivity extends AppCompatActivity implements CongratulationFragment.OnFormCompletedListener {

    private DatabaseManager manager;

    public DatabaseManager getDatabaseManager() {
        if (manager == null) {
            manager = new DatabaseManager(this);
        }
        return manager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_memory_game, new GameFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_memory_game, new GameFragment()).commit();
                return true;
            case R.id.scores:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_memory_game, new ScoreListFragment()).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void presentCongratulationFragmentWithScore(int score) {
        Bundle bundle = new Bundle();
        bundle.putInt(CongratulationFragment.GAME_SCORE_KEY, score);
        CongratulationFragment fragment = new CongratulationFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_memory_game, fragment).commit();
    }

    //region CongratulationFragment.OnFormCompletedListener
    public void onFormCompletedWithScore(Score score) {
        getDatabaseManager().addEntry(score);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_memory_game, new ScoreListFragment()).commit();
    }
    //endregion
}
