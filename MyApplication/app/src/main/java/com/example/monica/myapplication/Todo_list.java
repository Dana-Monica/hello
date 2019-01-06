package com.example.monica.myapplication;

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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Todo_list extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    Toolbar toolbar = null;
    NavigationView navigationView;
    DrawerLayout drawer;
    private DatabaseReference databaseReference;
    private EditText title,content;
    private View hide1,hide2,hide3,hide4, hide5,hide6,hide7,hide8, show;
    private ListView listview;
    private int numberOfItems = 0;
    private CustomAdapter customAdapter;
    private List<Lista> elements = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        createNewDBListener();

        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        hide1 = (View) findViewById(R.id.content_home);
        hide2 = (View) findViewById(R.id.content_updatelistitem);
        hide3 = (View) findViewById(R.id.content_addnewchild);
        hide4 = (View) findViewById(R.id.content_updateeventitem);
        hide5 = (View) findViewById(R.id.content_addnewevent);
        hide6 = (View) findViewById(R.id.content_guestsactivity);
        hide7 = (View) findViewById(R.id.content_updateguestitem);
        hide8 = (View) findViewById(R.id.content_addnewguest);
        show = (View) findViewById(R.id.content_todolist);
        hide1.setVisibility(View.GONE);
        hide2.setVisibility(View.GONE);
        hide3.setVisibility(View.GONE);
        hide4.setVisibility(View.GONE);
        hide5.setVisibility(View.GONE);
        hide6.setVisibility(View.GONE);
        hide7.setVisibility(View.GONE);
        hide8.setVisibility(View.GONE);
        show.setVisibility(View.VISIBLE);

        listview = (ListView) findViewById(R.id.listview);
        customAdapter = new CustomAdapter(this, R.layout.todolist_item, elements);
        listview.setAdapter(customAdapter);
        listview.setOnItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNewChild = new Intent(Todo_list.this,AddNewChild.class);
                addNewChild.putExtra("numberOfElements",numberOfItems);
                startActivity(addNewChild);
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

    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long id) {
        Intent updateListItem = new Intent(Todo_list.this,UpdateListItem.class);
        updateListItem.putExtra("position",position);
        Lista element = elements.get(position);
        updateListItem.putExtra("name",element.getName());
        updateListItem.putExtra("title",element.getTitle());
        updateListItem.putExtra("content",element.getContent());
        startActivity(updateListItem);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createNewDBListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        createNewDBListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        createNewDBListener();
    }

    private void createNewDBListener() {

        databaseReference.child("id1").child("todolists").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                elements.clear();
                for (DataSnapshot entrySnaphot : dataSnapshot.getChildren()) {
                    Lista list= entrySnaphot.getValue(Lista.class);
                    list.setName(entrySnaphot.getKey());

                    elements.add(list);
                    customAdapter.notifyDataSetChanged();
                }
                numberOfItems = elements.size();
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
