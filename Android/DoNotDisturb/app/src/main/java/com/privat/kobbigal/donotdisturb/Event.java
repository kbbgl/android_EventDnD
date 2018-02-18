package com.privat.kobbigal.donotdisturb;

import java.util.Date;

/**
 * Created by kobbigal on 2/18/18.
 */

public class Event {

    private String eventTitle;
    private String eventDescription;
    private Date eventStartTime;

    public Event(String eventTitle, String eventDescription, Date eventStartTime) {
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventStartTime = eventStartTime;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Date getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(Date eventStartTime) {
        this.eventStartTime = eventStartTime;
    }
}
