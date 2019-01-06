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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GuestsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener{

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;
    private View hide1,hide2,hide3,hide4, hide5,hide6, hide7,hide8,show;
    private String name, numeDeschisCurent;
    private ListView listGuests;
    private int position;
    private Intent intent;
    private CustomAdapterGuest customAdapter;
    private Map<String,String> guestsMap = new HashMap<>();
    private List<User> guestList = new ArrayList<User>();
    private int numberOfItems = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listGuests = (ListView) findViewById(R.id.listviewguest);
        customAdapter = new CustomAdapterGuest(this, R.layout.guest_item, guestList);
        listGuests.setAdapter(customAdapter);
        listGuests.setOnItemClickListener(this);

        hide1 = (View) findViewById(R.id.content_todolist);
        hide2 = (View) findViewById(R.id.content_updatelistitem);
        hide3 = (View) findViewById(R.id.content_addnewchild);
        hide4 = (View) findViewById(R.id.content_updateeventitem);
        hide5 = (View) findViewById(R.id.content_addnewevent);
        hide6 = (View) findViewById(R.id.content_home);
        hide7 = (View) findViewById(R.id.content_updateguestitem);
        hide8 = (View) findViewById(R.id.content_addnewguest);
        show = (View) findViewById(R.id.content_guestsactivity);
        hide1.setVisibility(View.GONE);
        hide2.setVisibility(View.GONE);
        hide3.setVisibility(View.GONE);
        hide4.setVisibility(View.GONE);
        hide5.setVisibility(View.GONE);
        hide6.setVisibility(View.GONE);
        hide7.setVisibility(View.GONE);
        hide8.setVisibility(View.GONE);
        show.setVisibility(View.VISIBLE);

        intent = getIntent();
        name = intent.getStringExtra("name");
        position = intent.getIntExtra("position",0);
        //retrieve all guests
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                String key = it.next();
                Object value = bundle.get(key);
                if(key.contains("name")||key.contains("position")) {
                    //avem deja salvate astea
                }
                else {
                    guestsMap.put(key,"" + value);
                    User u = new User();
                    u.setPhone(value + "");
                    u.setName(key);
                    guestList.add(u);
                }
            }
            numberOfItems = guestList.size();
        }
        //avem in lista guestMap toti invitatii
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNewGuest= new Intent(GuestsActivity.this,AddNewGuest.class);
                addNewGuest.putExtra("numberOfElements",numberOfItems);
                addNewGuest.putExtra("name", name);
                startActivityForResult(addNewGuest,2);
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
        Intent updateGuestItem = new Intent(GuestsActivity.this, UpdateGuestItem.class);
        updateGuestItem.putExtra("position", position);
        User element = guestList.get(position);
        updateGuestItem.putExtra("nameGuest", element.getName());
        updateGuestItem.putExtra("phone", element.getPhone());
        updateGuestItem.putExtra("name", name);
        numeDeschisCurent = element.getName();
        startActivityForResult(updateGuestItem,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result"); // adica deleted, deci s-a sters elementul si trebuie sa facem update la lista noastra si la map
                //stergem numeDeschisCurent din lista si map
                if (result.contains("deleted")) {
                    for (User u : guestList) {
                        if (u.getName().contains(numeDeschisCurent)) {
                            guestList.remove(u);
                            break;
                        }
                    }

                    if (guestsMap.containsKey(numeDeschisCurent))
                        guestsMap.remove(numeDeschisCurent);
                    customAdapter.notifyDataSetChanged();
                    numberOfItems = guestList.size();
                }
                if(result.contains("updated"))
                {
                    String name = data.getStringExtra("nameGuest");
                    String phone = data.getStringExtra("phoneGuest");

                    for (User u : guestList) {
                        if (u.getName().contains(numeDeschisCurent)) {
                            guestList.remove(u);
                            User nou = new User();
                            nou.setName(name);
                            nou.setPhone(phone);
                            guestList.add(nou);
                            break;
                        }
                    }

                    if (guestsMap.containsKey(numeDeschisCurent)) {
                        guestsMap.remove(numeDeschisCurent);
                        guestsMap.put(name,phone);
                    }
                    customAdapter.notifyDataSetChanged();
                    numberOfItems = guestList.size();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        if ( requestCode == 2 )
        {
            if(resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result"); // daca contine inserted
                if(result.contains("inserted"))
                {
                    String name = data.getStringExtra("nameGuest");
                    String phone = data.getStringExtra("phoneGuest");

                    User u = new User();
                    u.setPhone(phone);
                    u.setName(name);
                    guestList.add(u);
                    guestsMap.put(name,phone);

                    customAdapter.notifyDataSetChanged();
                    numberOfItems = guestList.size();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

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
            Intent i1 = new Intent(GuestsActivity.this,Home.class);
            startActivity(i1);
        } else if (id == R.id.nav_todo_list) {
            Intent i2 = new Intent(GuestsActivity.this,Todo_list.class);
            startActivity(i2);
        } else if (id == R.id.nav_sign_out) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
