package com.example.monica.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
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

public class AddNewGuest extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private View hide1,hide2,hide3,hide4,hide5,hide6, hide7,hide8,show;
    private Toolbar toolbar = null;
    private DatabaseReference databaseReference;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Intent intent;
    private int numberOfElements = 0;
    private EditText phoneGuest, nameGuest;
    private String phoneGuestString, nameGuestString, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_guest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        hide1 = (View) findViewById(R.id.content_home);
        hide2 = (View) findViewById(R.id.content_todolist);
        hide3 = (View) findViewById(R.id.content_updatelistitem);
        hide4 = (View) findViewById(R.id.content_updateeventitem);
        hide5 = (View) findViewById(R.id.content_addnewchild);
        hide6 = (View) findViewById(R.id.content_guestsactivity);
        hide7 = (View) findViewById(R.id.content_updateguestitem);
        hide8 = (View) findViewById(R.id.content_addnewevent);
        show = (View) findViewById(R.id.content_addnewguest);
        hide1.setVisibility(View.GONE);
        hide2.setVisibility(View.GONE);
        hide3.setVisibility(View.GONE);
        hide4.setVisibility(View.GONE);
        hide5.setVisibility(View.GONE);
        hide6.setVisibility(View.GONE);
        hide7.setVisibility(View.GONE);
        hide8.setVisibility(View.GONE);
        show.setVisibility(View.VISIBLE);

        phoneGuest = (EditText) findViewById(R.id.phoneGuestAdd);
        phoneGuest.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().contains("qwertyuiopasdfghjklzxcvbnm"))
                    Toast.makeText(AddNewGuest.this, "Phone number incorrect!", Toast.LENGTH_SHORT).show();
                else
                phoneGuestString = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                    phoneGuestString = s.toString();
            }

        });

        nameGuest = (EditText) findViewById(R.id.nameGuestAdd);
        nameGuest.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameGuestString = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                nameGuestString = s.toString();
            }

        });

        intent = getIntent();
        numberOfElements = intent.getIntExtra("numberOfElements", 0);
        name = intent.getStringExtra("name");
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

    public void clicked5 (View view) {
        switch (view.getId()) {
            case R.id.cancelGuestAdd:
                onBackPressed();
                break;
            case R.id.saveGuestAdd:
                //in nameGuesttring si phoneGuestString se afla datele care trebuie introduse in DB si le intoarcem inapoi in intent ca se le punem si in adaptor
                if(phoneGuestString.contains("qwertyuiopasdfghjklzxcvbnm"))
                {
                    Toast.makeText(this, "Incorect credentials!", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("id1").child("events").child(name).child("guests").child(nameGuestString).setValue(phoneGuestString);
                    Intent returnIntent3 = new Intent();
                    returnIntent3.putExtra("result", "inserted");
                    returnIntent3.putExtra("nameGuest", nameGuestString);
                    returnIntent3.putExtra("phoneGuest", phoneGuestString);
                    setResult(Activity.RESULT_OK, returnIntent3);
                    finish();
                    onBackPressed();
                    break;
                }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i5 = new Intent(AddNewGuest.this,Home.class);
            startActivity(i5);
        } else if (id == R.id.nav_todo_list) {
            Intent i6 = new Intent(AddNewGuest.this,Todo_list.class);
            startActivity(i6);
        } else if (id == R.id.nav_sign_out) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
