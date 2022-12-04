package com.nordesk.nightlife.UI;

import static android.content.ContentValues.TAG;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.common.util.SharedPreferencesUtils;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.nordesk.nightlife.R;
import com.nordesk.nightlife.databinding.ActivityMainBinding;
import com.nordesk.nightlife.model.Event;
import com.nordesk.nightlife.model.User;

import android.view.Menu;
import android.view.MenuItem;

import org.chromium.base.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference eventsDatabase;

    private Event currentEvent;

    public Event getCurrentEvent() {
        return currentEvent;
    }

    private ArrayList<Event> events;
    private User currentUser;

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }

    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    public Activity getActivityEx(){
        return this;
    }

    public Activity getActivitySuper(){
        return (AppCompatActivity)this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Auth
        auth = FirebaseAuth.getInstance();

        // Data
        database = FirebaseDatabase.getInstance("https://ultimate-nightlife-app-default-rtdb.europe-west1.firebasedatabase.app/");
        eventsDatabase = database.getReference("events");


        eventsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ArrayList<Event> loadedEventsList = new ArrayList<>();

                for(DataSnapshot eventSnapshot : dataSnapshot.getChildren()){
                    Event e = eventSnapshot.getValue(Event.class);
                    loadedEventsList.add(e);
                }
                events = loadedEventsList;
                Log.d(TAG, "Loaded " + loadedEventsList.size() + " events from the database.");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getBoolean("darkModeToggle", false)){
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
        }
        System.out.println("Initial settings done.");

    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void createEvent(Event e){
        String eID = eventsDatabase.push().getKey();
        e.setID(eID);
        System.out.println(" // DEBUG END DATE " + e.getStringEndingDateTime());
        eventsDatabase.child(eID).setValue(e);
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

    public void updateUI(FirebaseUser user){
        if(user != null) {
            String email = user.getEmail();
            String username = user.getDisplayName();
            android.util.Log.d(TAG, "Success! " + email + " " + username);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public void setProfile(UserProfileChangeRequest.Builder builder) {
        auth.getCurrentUser().updateProfile(builder.build());
    }

    public void deleteEvent(String eventID) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Delete?");
        alert.setMessage("Are you sure do you want to remove this event? It will not be visible anymore!");
        alert.setPositiveButton("Yes, I confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                eventsDatabase.child(eventID).removeValue();
                dialogInterface.dismiss();
                alert.setNegativeButton(null, null);
                alert.setPositiveButton(null, null);
                alert.setTitle("Deleted");
                alert.setMessage("The event was removed.");
                alert.show();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("Deletion process cancelled by the user.");
            }
        });
        alert.show();


    }
}