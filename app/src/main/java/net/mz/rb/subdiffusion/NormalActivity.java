package net.mz.rb.subdiffusion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.widget.ListView;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class NormalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ArrayList<VisiblePattern> products = new ArrayList();
    ListView productList;
    VisiblePatternAdapter adapter;
    FancyButton button133;
    TheBestHelperWithDB theBestHelperWithDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        theBestHelperWithDB = new TheBestHelperWithDB(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NormalActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        button133 = (FancyButton) findViewById(R.id.button133);
        button133.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFunctions();
                Intent intent = new Intent(NormalActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        placePatterns();
    }

    public void placePatterns() {
        if (products.size() == 0) {
            String[] names = theBestHelperWithDB.getAllNames();
            int[] iDs = theBestHelperWithDB.getAllIDs();
            for (int i = 0; i < theBestHelperWithDB.getTableHeight(); i++) {
                products.add(new VisiblePattern(names[i], Functions.PATTERN_ID + iDs[i], Functions.EDIT_ID + iDs[i], Functions.DELETE_ID + iDs[i]));
            }
        }
        productList = (ListView) findViewById(R.id.productList);
        adapter = new VisiblePatternAdapter(this, R.layout.list_item, products);
        productList.setAdapter(adapter);
    }

    public void onViewPatternButtonClick (View view){
        for (int i = 0; i < theBestHelperWithDB.getTableHeight(); i++) {
            if (view.getId() == products.get(i).getPatternID()) {
                Intent intent = new Intent(NormalActivity.this, Class_new.class);
                intent.putExtra("id", products.get(i).getPatternID() - Functions.PATTERN_ID);
                startActivity(intent);
                break;
            }
        }
    }

    public void onDeletePatternButtonClick (View view){
        for (int i = 0; i < theBestHelperWithDB.getTableHeight(); i++) {
            if (view.getId() == products.get(i).getDeleteID()) {
                theBestHelperWithDB.deletePost(products.get(i).getDeleteID() - Functions.DELETE_ID);
                products.remove(i);
                adapter.notifyDataSetInvalidated();
                productList.setAdapter(adapter);
                break;
            }
        }
    }

    public void onEditPatternButtonClick (View view){
        for (int i = 0; i < theBestHelperWithDB.getTableHeight(); i++) {
            if (view.getId() == products.get(i).getEditID()) {
                Intent intent = new Intent(NormalActivity.this, AddActivity.class);
                intent.putExtra("id", products.get(i).getEditID() - Functions.EDIT_ID);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.normal, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void resetFunctions(){
        Functions.points = 25;
        Functions.difSpeed = 1;
        Functions.radius = 1;
        Functions.alpha = 1.0;
        Functions.koeff = 100.0;
        Functions.sigma1 = 1.0;
        Functions.sigma2 = 0.0;
        Functions.X = 13;
        Functions.Y = 13;
    }
}