package com.example.orarproiect;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements IDecodable{
    private ViewPager mPager;
    private SlideAdapter slideAdapter;
    private PagerAdapter pagerAdapter;

    DecoderHTML decoderHTML = new DecoderHTML(this);

    private List<Ora> list = new ArrayList<Ora>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        FloatingActionButton fab = findViewById(R.id.fab);

        decoderHTML.execute();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        mPager = (ViewPager) findViewById(R.id.pager);
        slideAdapter = new SlideAdapter(getSupportFragmentManager());

    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDecodeComplete() {
        List<Ora> luniData = decoderHTML.getLuniData();
        List<Ora> martiData = decoderHTML.getMartiData();
        List<Ora> miercuriData = decoderHTML.getMiercuriData();
        List<Ora> joiData = decoderHTML.getJoiData();
        List<Ora> vineriData = decoderHTML.getVineriData();

        slideAdapter.setList(luniData, 1);
        slideAdapter.setList(martiData, 2);
        slideAdapter.setList(miercuriData, 3);
        slideAdapter.setList(joiData, 4);
        slideAdapter.setList(vineriData, 5);

        mPager.setAdapter(slideAdapter);

    }

    @Override
    public void onDecodeProgress() {

    }
}
