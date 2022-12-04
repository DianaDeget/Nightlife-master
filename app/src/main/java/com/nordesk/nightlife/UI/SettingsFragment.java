package com.nordesk.nightlife.UI;

import com.google.firebase.auth.UserProfileChangeRequest.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import static android.content.ContentValues.TAG;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.core.Tag;
import com.nordesk.nightlife.R;
import com.nordesk.nightlife.databinding.ActivityMainBinding;

public class SettingsFragment extends PreferenceFragmentCompat {
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(SettingsFragment.this)
                        .navigate(R.id.action_settingsFragment_to_HomeFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    public SettingsFragment() {
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                    if (s.equals("change_username")) {
                        Log.d(TAG, "Username Changed to : " + sharedPreferences.getString("change_username", "default"));
                        ((MainActivity) getActivity()).setProfile(new Builder().setDisplayName(sharedPreferences.getString("change_username", "default")));
                    } else if (s.equals("darkModeToggle")) {
                        if (sharedPreferences.getBoolean("darkModeToggle", false)) {
                            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
                        } else {
                            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
                        }
                    }
                }
        };
    }

    @Override
    public void onCreatePreferences(Bundle savedInxstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        //SharedPreferences  preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        /*Builder builder = new Builder().setDisplayName(preferences.getString("change_username", "New username"));
        ((MainActivity) getActivity()).setProfile(builder);*/
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
    }

/*
    .fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    });*/
}