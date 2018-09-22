package com.torrestudio.countitdown.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.torrestudio.countitdown.R;
import com.torrestudio.countitdown.constants.Constant;
import com.torrestudio.countitdown.controllers.EventDataController;
import com.torrestudio.countitdown.entities.Event;
import com.torrestudio.countitdown.interfaces.EventDataSubscriber;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, EventDataSubscriber {

    // Member views of this activity
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private FloatingActionButton addFabButton;

    private EventDataController mDataController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        mDataController = EventDataController.initController(this);
        EventDataController.subscribe(this);
    }

    private void initViews() {
        // Nav Drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Toolbar
        mToolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        getSupportActionBar().setTitle(getString(R.string.category_all_events));

        // FAB
        addFabButton = findViewById(R.id.addFabButton);
        addFabButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addFabButton:
                Intent activityIntent = new Intent(this, CreateEventActivity.class);
                startActivity(activityIntent);
                break;
        }
    }

    @Override
    public void onEventCreated(Event e) {
        // Refresh the future recycler view when data is added
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // set item as selected to persist highlight
        item.setChecked(true);
        // close drawer when item is tapped
        mDrawerLayout.closeDrawers();

        String newToolbarName = "";
        switch (item.getItemId()) {
            case R.id.nav_all_events:
                newToolbarName = getString(R.string.category_all_events);
                break;
            case R.id.nav_past_events:
                newToolbarName = getString(R.string.category_past_events);
                break;
            case R.id.nav_business:
                newToolbarName = getString(R.string.category_business);
                break;
            case R.id.nav_education:
                newToolbarName = getString(R.string.category_education);
                break;
            case R.id.nav_leisure:
                newToolbarName = getString(R.string.category_leisure);
                break;
            case R.id.nav_special:
                newToolbarName = getString(R.string.category_special);
                break;
            case R.id.nav_sports:
                newToolbarName = getString(R.string.category_sports);
                break;
        }
        setToolbarTitle(newToolbarName);
        return true;
    }

    private void setToolbarTitle(String newName) {
        getSupportActionBar().setTitle(newName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_settings:
                Toast.makeText(this, "No of events: " + mDataController.getAllEvents().size(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
