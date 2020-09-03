package com.michelbarbosa.nytmovies.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import com.michelbarbosa.nytmovies.R;

public class ListActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        setApplicationStyle(searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setAdapterFilter(newText);
                return false;
            }
        });
        return true;
    }

    private void setApplicationStyle(SearchView searchView) {
        ImageView searchButton = searchView.findViewById(R.id.search_button);
        ImageView closeButton = searchView.findViewById(R.id.search_close_btn);
        TextView textSearch = searchView.findViewById(R.id.search_src_text);
        int color = getResources().getColor(R.color.colorBase);
        searchButton.setColorFilter(color);
        closeButton.setColorFilter(color);
        textSearch.setTextColor(color);
    }

    protected void setAdapterFilter(String newText) {
    }

}
