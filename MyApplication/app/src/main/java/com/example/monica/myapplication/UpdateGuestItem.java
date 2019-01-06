package com.example.monica.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class UpdateGuestItem extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private View hide1,hide2,hide3,hide4,hide5,hide6,hide7,hide8,show;
    private Toolbar toolbar = null;
    private DatabaseReference databaseReference;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Intent intent;
    private EditText nameGuest,phoneGuest;
    private ListView guestlistview;
    private Map<String,String> budget;
    private int position;
    private String nameGuestString, phoneGuestString,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_guest_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        phoneGuest = (EditText) findViewById(R.id.phoneGuest);
        nameGuest = (EditText) findViewById(R.id.nameGuest);

        hide1 = (View) findViewById(R.id.content_home);
        hide2 = (View) findViewById(R.id.content_todolist);
        hide3 = (View) findViewById(R.id.content_addnewchild);
        hide4 = (View) findViewById(R.id.content_updatelistitem);
        hide5 = (View) findViewById(R.id.content_addnewevent);
        hide6 = (View) findViewById(R.id.content_guestsactivity);
        hide7 = (View) findViewById(R.id.content_updateeventitem);
        hide8 = (View) findViewById(R.id.content_addnewguest);
        show = (View) findViewById(R.id.content_updateguestitem);
        hide1.setVisibility(View.GONE);
        hide2.setVisibility(View.GONE);
        hide3.setVisibility(View.GONE);
        hide4.setVisibility(View.GONE);
        hide5.setVisibility(View.GONE);
        hide6.setVisibility(View.GONE);
        hide7.setVisibility(View.GONE);
        hide8.setVisibility(View.GONE);
        show.setVisibility(View.VISIBLE);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        intent = getIntent();
        nameGuestString = intent.getStringExtra("nameGuest");
        nameGuest.setText(nameGuestString);
        phoneGuestString = intent.getStringExtra("phone");
        phoneGuest.setText(phoneGuestString);
        name = intent.getStringExtra("name");

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

    public void clicked4(View view) {
        switch (view.getId()) {
            case R.id.cancelGuest:
                onBackPressed();
                break;
            case R.id.updateItemGuest:
                databaseReference.child("id1").child("events").child(name).child("guests").child(nameGuestString).removeValue();
                databaseReference.child("id1").child("events").child(name).child("guests").child(nameGuest.getText().toString()).setValue(phoneGuest.getText().toString());
                Intent returnIntent2 = new Intent();
                returnIntent2.putExtra("result","updated");
                returnIntent2.putExtra("nameGuest", nameGuest.getText().toString());
                returnIntent2.putExtra("phoneGuest", phoneGuest.getText().toString());
                setResult(Activity.RESULT_OK,returnIntent2);
                finish();
                onBackPressed();
                break;
            case R.id.removeGuest:
                databaseReference.child("id1").child("events").child(name).child("guests").child(nameGuestString).removeValue();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result","deleted");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                onBackPressed();
                break;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i5 = new Intent(UpdateGuestItem.this,Home.class);
            startActivity(i5);
        } else if (id == R.id.nav_todo_list) {
            Intent i6 = new Intent(UpdateGuestItem.this,Todo_list.class);
            startActivity(i6);
        } else if (id == R.id.nav_sign_out) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
