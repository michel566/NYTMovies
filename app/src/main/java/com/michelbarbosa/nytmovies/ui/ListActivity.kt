package com.michelbarbosa.nytmovies.ui

import android.view.Menu
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import com.michelbarbosa.nytmovies.R

open class ListActivity : BaseActivity() {

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.clear()
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView =
            searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        setApplicationStyle(searchView)
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                findForQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                setAdapterFilter(newText)
                return false
            }
        })
        return true
    }

    private fun setApplicationStyle(searchView: SearchView) {
        val searchButton =
            searchView.findViewById<ImageView>(R.id.search_button)
        val closeButton =
            searchView.findViewById<ImageView>(R.id.search_close_btn)
        val textSearch = searchView.findViewById<TextView>(R.id.search_src_text)
        val color = resources.getColor(R.color.colorBase)
        searchButton.setColorFilter(color)
        closeButton.setColorFilter(color)
        textSearch.setTextColor(color)
    }

    protected open fun setAdapterFilter(newText: String?) {}

    protected open fun findForQuery(query: String) {}

}