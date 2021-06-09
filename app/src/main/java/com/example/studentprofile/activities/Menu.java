package com.example.studentprofile.activities;

import android.content.res.Configuration;
import android.os.Build;
import android.view.MenuItem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentprofile.R;

import java.util.Locale;

public class Menu extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.lang_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.select_en) {
            Locale locale = new Locale("en");
            changeLocale(locale);
        } else if (item.getItemId() == R.id.select_uk) {
            Locale locale = new Locale("uk");
            changeLocale(locale);
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void changeLocale(Locale locale)
    {
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getBaseContext().getResources()
                .updateConfiguration(configuration,
                        getBaseContext()
                                .getResources()
                                .getDisplayMetrics());

        finish();
        startActivity(getIntent());
    }

}
