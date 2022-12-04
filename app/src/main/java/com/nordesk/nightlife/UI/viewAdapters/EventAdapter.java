package com.nordesk.nightlife.UI.viewAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.nordesk.nightlife.R;
import com.nordesk.nightlife.UI.MainActivity;
import com.nordesk.nightlife.model.Event;
import com.nordesk.nightlife.model.EventDate;
import com.nordesk.nightlife.model.EventStatus;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

    private String lat;
    private String lng;
    private String markerLat;
    private String markerLng;
    private ArrayList<Event> eventsList;
    private FragmentActivity root;
    private MainActivity activity;
    private FirebaseAuth auth;


    public EventAdapter(MainActivity activityReference, FragmentActivity root) {
        activity = activityReference;
        eventsList = activity.getEvents();
        auth = FirebaseAuth.getInstance();
        this.root = root;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_event, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        eventsList = activity.getEvents();
        Event event = eventsList.get(position);
        event.processStatus();
        lat = event.getLatitude();
        lng = event.getLongitude();
        markerLat = event.getLatitude();
        markerLng = event.getLongitude();
        holder.addressView.setText(String.format("Where? Lat: %s, Lng: %s", lat, lng));
        holder.dateTimeView.setText(String.format("Starting: %s, ending: %s", event.getStringDateTime(), event.getStringEndingDateTime()));
        holder.descriptionView.setText(String.format("Description: %s", event.getDescription()));
        TextView textView = holder.nameTextView;
        textView.setText(event.getTitle());
        Button button = holder.messageButton;
        if(event.getHostID().equals(auth.getUid())) {
            holder.deleteEventButton.setVisibility(View.VISIBLE);
        } else {
            holder.deleteEventButton.setVisibility(View.INVISIBLE);
        }
        button.setText(event.getStatus().getStatus());
        button.setEnabled(event.getStatus().equals(EventStatus.Live));
        /*SupportMapFragment mapFragment = (SupportMapFragment) root.getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);*/
        ImageView thisMap = holder.imageView;
        if (holder.imageView != null)
        {
            Glide
                    .with(root)
                    .load("https://maps.googleapis.com/maps/api/staticmap?center=" +
                            lat +
                            "," +
                            lng +
                            "&zoom=12&size=400x400" +
                            "&markers=color:red%7Clabel:S%7C" +
                            markerLat +
                            "," +
                            markerLng +
                            "&key=AIzaSyDNMwXkSOMS6xaVXAmyzDn_1wg-7fN2WNo")
                    .override(Target.SIZE_ORIGINAL, 200)
                    .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_text_dark)
                    .centerCrop()
                    .into(holder.imageView);

        }
        holder.deleteEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.deleteEvent(event.getID());
            }
        });
        /*if(thisMap != null){
            LatLng latLng = new LatLng(55, 9);
            float zoom = 1;
            thisMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        }*/
    }

    @Override
    public int getItemCount() {
        return activity.getEvents().size();
    }


    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.imageView.setImageResource(com.google.android.gms.base.R.drawable.common_google_signin_btn_text_light_normal);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView nameTextView;
            public Button messageButton;
            public ImageView imageView;
            public TextView addressView;
            public TextView dateTimeView;
            public TextView descriptionView;
            public Button deleteEventButton;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.mapView);
                nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
                messageButton = (Button) itemView.findViewById(R.id.message_button);
                addressView = (TextView) itemView.findViewById(R.id.address);
                dateTimeView = (TextView) itemView.findViewById(R.id.date_time);
                descriptionView = (TextView) itemView.findViewById(R.id.description);
                deleteEventButton = (Button) itemView.findViewById(R.id.delete_event_button);
            }
    }
}
