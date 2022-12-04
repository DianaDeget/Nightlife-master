package com.nordesk.nightlife.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Event {
    private String invitationLink;
    private String eID;
    private String hostID;

    public String getHostID() {
        return hostID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }

    private String title;
    private String description;
    private String latitude;
    private String longitude;
    private String startingDateTime;
    private String endingDateTime;

    public String getStartingDateTime() {
        return startingDateTime;
    }

    public void setStartingDateTime(String startingDateTime) {
        this.startingDateTime = startingDateTime;
    }

    public String getEndingDateTime() {
        return endingDateTime;
    }

    public void setEndingDateTime(String endingDateTime) {
        this.endingDateTime = endingDateTime;
    }

    public Event(){

    }

    public Event(String uid, String title, String address, String time, String date, String latitude, String longitude){
        this.title = title;
        this.hostID = uid;
        invitationLink = String
                .valueOf(
                        (
                                uid +
                                        String
                                                .valueOf(
                                                        Math
                                                                .random() * 0x64
                                                )
                                        + " "
                        )
                .hashCode());
        description = "Description of a nice event!";
        this.latitude = latitude;
        this.longitude = longitude;
        // latitude = String.valueOf(Testing.randomDouble(8, 10));
        // longitude = String.valueOf(Testing.randomDouble(55, 57));
        startingDateTime = LocalDateTime
                .of(2000, 6, 5, 23, 15)
                .toString();
        endingDateTime = LocalDateTime
                .parse(startingDateTime)
                .plus(Duration.of(1,ChronoUnit.DAYS))
                .toString();
    }


    public Event(String title, EventStatus status) {
        this.title = title+String.valueOf(Testing.randomInt(53942, 253435));
        this.status = status;
        invitationLink = "link";
        description = "description";
        latitude = String.valueOf(Testing.randomDouble(8, 10));
        longitude = String.valueOf(Testing.randomDouble(55, 57));
        startingDateTime = LocalDateTime
                .of(2000, 6, 5, 23, 15)
                .toString();
        endingDateTime = LocalDateTime
                .of(2000, 6, 6, 6, 15)
                .toString();
    }



    public Event(String title) {
        this.title = title+String.valueOf(Testing.randomInt(53942, 253435));
        invitationLink = "www.google.com";
        description = "Description of a nice event!";
        latitude = String.valueOf(Testing.randomDouble(8, 10));
        longitude = String.valueOf(
                Testing
                        .randomDouble(55, 57)
        );
        startingDateTime = LocalDateTime
                .of(2000, 6, 5, 23, 15)
                .toString();
        endingDateTime = LocalDateTime
                .of(2000, 6, 6, 6, 15)
                .toString();
    }

    /*@Deprecated
    public static ArrayList<Event> createEvents(int i) {
        Random rand = new Random(i);
        ArrayList<Event> futureEvents = new ArrayList<>();
        for(int j = 1; j <= i; j++){
            rand.setSeed((long) j *j*35);
            EventStatus futureEventStatus;
            switch (rand.nextInt(5)){
                case 1: {
                    futureEventStatus = EventStatus.Live; break;
                }
                case 2: {
                    futureEventStatus = EventStatus.Over; break;
                }
                case 3: {
                    futureEventStatus = EventStatus.Planning; break;
                }
                default: {
                    futureEventStatus = EventStatus.Preparing; break;
                }
            }
            Event futureEvent = Event.generateAndGetEvent("Test Event " + (j * j * 3 - 5)/j, "Kamtjatka 13", "15:30", "05/06/2022", "53", "9");
            futureEvent.setLatitude(String.valueOf(rand.nextDouble() + 56.24068));
            futureEvent.setLongitude(String.valueOf(rand.nextDouble() + 8.487707));
            futureEvents.add(futureEvent);
        }
        return futureEvents;
    }*/

    public static Event generateAndGetEvent(String uid, String title, String address, String time, String date, String latitude, String longitude) {
        return new Event(uid, title, address, time, date, latitude, longitude);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    private boolean deleted;

    private EventStatus status;

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    private ArrayList<Attendance> attendances;
    private ArrayList<User> invitations;

    public String getInvitationLink() {
        return invitationLink;
    }

    public void setInvitationLink(String invitationLink) {
        this.invitationLink = invitationLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public EventStatus processStatus() {
        EventStatus futureEventStatus;
        LocalDateTime now = LocalDateTime.now();

        if(now.isBefore(toLocalDateTime(startingDateTime).minus(Duration.of(1, ChronoUnit.HOURS)))){
            if(now.isBefore(toLocalDateTime(startingDateTime).minus(Duration.of(1, ChronoUnit.DAYS)))){
                futureEventStatus = EventStatus.Planning;
            } else {
                futureEventStatus = EventStatus.Preparing;
            }
        } else {
            if(now.isBefore(toLocalDateTime(endingDateTime))){
                futureEventStatus = EventStatus.Live;
            } else {
                futureEventStatus = EventStatus.Over;
            }
        }


        this.status = futureEventStatus;
        return futureEventStatus;
    }

    public void setID(String eID) {
        this.eID = eID;
    }

    public String getID() {
        return eID;
    }

    private LocalDateTime toLocalDateTime(String date){
        return LocalDateTime.parse(date);
    }

    public String getStringDateTime() {
        return startingDateTime;
    }

    public String getStringEndingDateTime() {
        return endingDateTime;
    }
}
