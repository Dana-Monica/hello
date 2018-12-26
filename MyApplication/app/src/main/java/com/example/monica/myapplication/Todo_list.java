package com.example.monica.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Todo_list extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    Toolbar toolbar = null;
    NavigationView navigationView;
    DrawerLayout drawer;
    private DatabaseReference databaseReference;
    private EditText title,content;
    private View hide1,hide2,show;
    private ListView listview;
    private CustomAdapter customAdapter;
    private List<Lista> elements = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        createNewDBListener();

        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        hide1 = (View) findViewById(R.id.content_home);
        hide2 = (View) findViewById(R.id.content_updatelistitem);
        show = (View) findViewById(R.id.content_todolist);
        hide1.setVisibility(View.GONE);
        hide2.setVisibility(View.GONE);
        show.setVisibility(View.VISIBLE);

        listview = (ListView) findViewById(R.id.listview);
        customAdapter = new CustomAdapter(this, R.layout.todolist_item, elements);
        listview.setAdapter(customAdapter);
        listview.setOnItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void createNewDBChildListener() {
        databaseReference.child("id1").child("todolists").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        /* Retrieve lists of items or listen for additions to a list of items.
        This callback is triggered once for each existing child and then again every time
        a new child is added to the specified path. The DataSnapshot passed to the listener
        contains the new child's data. */
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        /* Listen for changes to the items in a list. This event fired any time a child
        node is modified, including any modifications to descendants of the child node.
        The DataSnapshot passed to the event listener contains the updated data for the child.
        */
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
        /* Listen for items being removed from a list. The DataSnapshot passed to the event
         callback contains the data for the removed child. */
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        /* Listen for changes to the order of items in an ordered list. This event is
        triggered whenever the onChildChanged() callback is triggered by an update that
        causes reordering of the child. It is used with data that is ordered with
        orderByChild or orderByValue. */
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long id) {
        Intent updateListItem = new Intent(Todo_list.this,UpdateListItem.class);
        updateListItem.putExtra("position",position);
        Lista element = elements.get(position);
        updateListItem.putExtra("title",element.getTitle());
        updateListItem.putExtra("content",element.getContent());
        startActivity(updateListItem);
    }

    private void createNewDBListener() {

        databaseReference.child("id1").child("todolists").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot entrySnaphot : dataSnapshot.getChildren()) {
                    Lista list= entrySnaphot.getValue(Lista.class);
                    elements.add(list);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
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
            Intent i3 = new Intent(Todo_list.this,Home.class);
            startActivity(i3);
        } else if (id == R.id.nav_todo_list) {
            Intent i4 = new Intent(Todo_list.this,Todo_list.class);
            startActivity(i4);
        } else if (id == R.id.nav_sign_out) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
