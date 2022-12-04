package com.nordesk.nightlife.UI;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nordesk.nightlife.R;
import com.nordesk.nightlife.databinding.FragmentLoginBinding;
import com.nordesk.nightlife.model.Event;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    private FirebaseAuth mAuth;

    private String invitationKey;

    private Event e;

    public String getInvitationKey() {
        return invitationKey;
    }

    public void setInvitationKey(String invitationKey) {
        this.invitationKey = invitationKey;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Task<AuthResult> resultTask = mAuth.signInWithEmailAndPassword(binding.editTextTextEmailAddress.getText().toString(), binding.editTextTextPassword2.getText().toString());
                mAuth.signInWithEmailAndPassword(binding.editTextTextEmailAddress.getText().toString(), binding.editTextTextPassword2.getText().toString()).addOnCompleteListener();
                if(resultTask.isSuccessful()){
                    updateUI(mAuth.getCurrentUser());
                    NavHostFragment.findNavController(LoginFragment.this)
                            .navigate(R.id.action_LoginFragment_to_HomeFragment);
                }else{
                    Log.w(TAG, "FAILED AUTH! " + resultTask.getResult().toString());
                }
                mAuth.signInWithEmailAndPassword(binding.editTextTextEmailAddress.getText().toString(), binding.editTextTextPassword2.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    ((MainActivity) getActivity()).updateUI(user);
                                    NavHostFragment.findNavController(LoginFragment.this)
                                            .navigate(R.id.action_LoginFragment_to_HomeFragment);
                                } else {
                                    Log.w(TAG, "FAILED AUTH! " + task.getResult().toString());
                                    Toast.makeText(LoginFragment.super.getContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    ((MainActivity) getActivity()).updateUI(null);
                                }

                                // ...
                            }
                        });
                */
                // DEBUG

//                String user = "", pass = "";
//                if(binding.editTextTextEmailAddress.getText().toString().isEmpty()){
//                    user = "iusgrig@icloud.com";
//                    pass = "Asdfghjkl";
//                } else {
//                    user = "diana.deget@yahoo.com";
//                    pass = "Diana123";
//                }

                // mAuth.signInWithEmailAndPassword(user,pass)
                mAuth.signInWithEmailAndPassword(binding.editTextTextEmailAddress.getText().toString(), binding.editTextTextPassword2.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    ((MainActivity) getActivity()).updateUI(user);
                                    try {
                                        NavHostFragment.findNavController(LoginFragment.this)
                                                .navigate(R.id.action_LoginFragment_to_HomeFragment);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Log.w(TAG, "FAILED AUTH! " + task.getResult().toString());
                                    Toast.makeText(LoginFragment.super.getContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    ((MainActivity) getActivity()).updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_LoginFragment_to_registerFragment);
            }
        });

        binding.loginAnon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.eventLinkText.getText().toString().isEmpty()){
                    if(isEventLink(binding.eventLinkText.getText().toString())){
                        mAuth.signInAnonymously();
                        setInvitationKey(binding.eventLinkText.getText().toString());
                        try {
                            NavHostFragment.findNavController(LoginFragment.this)
                                    .navigate(R.id.action_LoginFragment_to_eventViewFragment);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(LoginFragment.super.getContext(), "No event with that invitation link.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private boolean isEventLink(String toString) {
        for(Event e : ((MainActivity)getContext()).getEvents()){
            if(e.getInvitationLink().equals(toString)){
                this.e = e;
                ((MainActivity)getContext()).setCurrentEvent(e);
                return true;
            }
        }
        return false;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}