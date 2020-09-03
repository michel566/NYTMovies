package com.michelbarbosa.nytmovies.ui;

import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.michelbarbosa.nytmovies.R;

public class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
    }

    public void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(4);
        }
        enableDisplayShow(false);
    }

    protected void setToolbarTitle(int idResourceTitle) {
        if (idResourceTitle != 0) {
            TextView titleToolbar = findViewById(R.id.titleToolbar);
            String title = getResources().getString(idResourceTitle);
            titleToolbar.setText(title);

            if (title.length() > 23) {
                titleToolbar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            } else if (title.length() > 19) {
                titleToolbar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            }
        }
    }

    public void enableDisplayShow(boolean isShow) {
        if (toolbar != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(isShow);
            getSupportActionBar().setDisplayShowTitleEnabled(isShow);
        }
    }

    public void setLayoutContent(int resourceContentView) {
        RelativeLayout dinamicContent = findViewById(R.id.content);
        View view = getLayoutInflater().inflate(resourceContentView, dinamicContent, false);
        dinamicContent.addView(view);
    }

}
