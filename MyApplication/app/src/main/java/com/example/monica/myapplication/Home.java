package com.example.monica.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;
    private View hide1,hide2,hide3,hide4, hide5,hide6,hide7,hide8, show;
    private ListView listviewevent;
    private CustomAdaptorEvent customAdapter;
    private static List<EventElement> elementsEvent = new ArrayList<>();
    private DatabaseReference databaseReference;
    private int numberOfItems = 0;
    private Intent intent;
    private LoginRemember user = LoginRemember.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        listviewevent = (ListView) findViewById(R.id.listviewevent);
        customAdapter = new CustomAdaptorEvent(this, R.layout.event_item, elementsEvent);
        listviewevent.setAdapter(customAdapter);
        listviewevent.setOnItemClickListener(this);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            createDBListener();

            hide1 = (View) findViewById(R.id.content_todolist);
            hide2 = (View) findViewById(R.id.content_updatelistitem);
            hide3 = (View) findViewById(R.id.content_addnewchild);
            hide4 = (View) findViewById(R.id.content_updateeventitem);
            hide5 = (View) findViewById(R.id.content_addnewevent);
            hide6 = (View) findViewById(R.id.content_guestsactivity);
            hide7 = (View) findViewById(R.id.content_updateguestitem);
            hide8 = (View) findViewById(R.id.content_addnewguest);
            show = (View) findViewById(R.id.content_home);
            hide1.setVisibility(View.GONE);
            hide2.setVisibility(View.GONE);
            hide3.setVisibility(View.GONE);
            hide4.setVisibility(View.GONE);
            hide5.setVisibility(View.GONE);
            hide6.setVisibility(View.GONE);
            hide7.setVisibility(View.GONE);
            hide8.setVisibility(View.GONE);
            show.setVisibility(View.VISIBLE);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addNewEvent = new Intent(Home.this, AddNewEvent.class);
                    addNewEvent.putExtra("numberOfElements", numberOfItems);
                    addNewEvent.putExtra("userr",user.getUser());
                    startActivity(addNewEvent);
                }
            });

            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            intent = getIntent();
            setUser(intent.getStringExtra("userr"));
    }


    @Override
    protected void onResume() {
        super.onResume();
        createDBListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        createDBListener();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        createDBListener();
    }

    public void setUser(String user){
        this.user.setUser(user);
    }


    public void createDBListener() {

        databaseReference.child("id1").child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                elementsEvent.clear();
                for (DataSnapshot entrySnaphot : dataSnapshot.getChildren()) {
                    EventElement event = new EventElement();
                    event.setDate((String) entrySnaphot.child("date").getValue());
                    event.setLocation((String) entrySnaphot.child("location").getValue());
                    event.setTitle((String) entrySnaphot.child("title").getValue());
                    event.setName(entrySnaphot.getKey());

                    Map<String, String> budgetItem = (Map<String, String>) entrySnaphot.child("budget").getValue();
                    Map<String, String> guestItem = (Map<String, String>) entrySnaphot.child("guests").getValue();
                    event.setBudget(budgetItem);
                    event.setGuests(guestItem);

                    elementsEvent.add(event);
                    customAdapter.notifyDataSetChanged();
                }
                numberOfItems = elementsEvent.size();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }

    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long id) {

            Intent updateEventItem = new Intent(Home.this, UpdateEventItem.class);
            updateEventItem.putExtra("position", position);
            EventElement element = elementsEvent.get(position);
            updateEventItem.putExtra("name", element.getName());
            updateEventItem.putExtra("title", element.getTitle());
            updateEventItem.putExtra("location", element.getLocation());
            updateEventItem.putExtra("date", element.getDate());
            updateEventItem.putExtra("budgetCount", element.getBudget().size());
            updateEventItem.putExtra("budgetTotal", element.getBudgetTotal());
            startActivity(updateEventItem);

        /* int i = 0;
        for( String key: element.getBudget().keySet())
        {
            String value = String.valueOf(element.getBudget().get(key));
            updateEventItem.putExtra("budgetkey" + i, key);
            updateEventItem.putExtra("budgetvalue" + i,value);
            i++;
        }
        */

            //daca mai am timp, sa incarc bugetul, e tot un listview, nimic nou

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

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i1 = new Intent(Home.this,Home.class);
            startActivity(i1);
        } else if (id == R.id.nav_todo_list) {
            Intent i2 = new Intent(Home.this,Todo_list.class);
            startActivity(i2);
        } else if (id == R.id.nav_sign_out) {
            setUser("");
            startActivity(new Intent(Home.this,SignupActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
