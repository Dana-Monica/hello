package com.example.monica.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddNewEvent extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private View hide1,hide2,hide3,hide4,hide5,show;
    private Toolbar toolbar = null;
    private DatabaseReference databaseReference;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private String titleString, locationString, dateString, budgetString;
    private Intent intent;
    private int numberOfElements = 0;
    private EditText titleEventAdd, locationEventAdd, dateEventAdd, budgetEventAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        hide1 = (View) findViewById(R.id.content_home);
        hide2 = (View) findViewById(R.id.content_todolist);
        hide3 = (View) findViewById(R.id.content_updatelistitem);
        hide4 = (View) findViewById(R.id.content_updateeventitem);
        hide5 = (View) findViewById(R.id.content_addnewchild);
        show = (View) findViewById(R.id.content_addnewevent);
        hide1.setVisibility(View.GONE);
        hide2.setVisibility(View.GONE);
        hide3.setVisibility(View.GONE);
        hide4.setVisibility(View.GONE);
        hide5.setVisibility(View.GONE);
        show.setVisibility(View.VISIBLE);

        titleEventAdd = (EditText) findViewById(R.id.titleEventAdd);
        titleEventAdd.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                titleString = titleEventAdd.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        locationEventAdd = (EditText) findViewById(R.id.locationEventAdd);
        locationEventAdd.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                locationString = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                locationString = s.toString();
            }

        });

        dateEventAdd = (EditText) findViewById(R.id.dateEventAdd);
        dateEventAdd.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dateString = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                dateString = s.toString();
            }

        });

        budgetEventAdd = (EditText) findViewById(R.id.budgetEventAdd);
        budgetEventAdd.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                budgetString = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                budgetString = s.toString();
            }

        });

        intent = getIntent();
        numberOfElements = intent.getIntExtra("numberOfElements", 0);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void clicked3 (View view) {
        switch (view.getId()) {
            case R.id.cancelEventAdd:
                onBackPressed();
                break;
            case R.id.saveEventAdd:
                int pos = numberOfElements + 1;
                String name = "event" + pos;
                Map<String,Object > mapB = new HashMap<>();
                Map<String,String > mapC = new HashMap<>();
                mapB.put("date",dateString);
                mapB.put("location",locationString);
                mapB.put("title",titleString);
                mapC.put("cheltuieli",budgetString);
                mapB.put("budget",mapC);
                // mai am de adaugat guests
                if (budgetString.contains("qwertyuioplkjhgfdsazxcvbnm")) // verificam daca budget e int
                {
                    //nu e bine
                    Toast.makeText(AddNewEvent.this, "Incorrect budget value!\nDigits only!" , Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("id1").child("events").child(name).setValue(mapB);
                    onBackPressed();
                }
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i5 = new Intent(AddNewEvent.this,Home.class);
            startActivity(i5);
        } else if (id == R.id.nav_todo_list) {
            Intent i6 = new Intent(AddNewEvent.this,Todo_list.class);
            startActivity(i6);
        } else if (id == R.id.nav_sign_out) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
