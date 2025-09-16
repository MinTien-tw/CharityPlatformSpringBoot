package model;

import java.sql.Date;
import java.sql.Time;

/**
 * 活動資料模型（對應 Events 資料表）
 * 注意：資料庫中欄位名稱為 CharitiesID（大寫），Java 這邊使用駝峰式命名為 charityId
 */
public class Event {

    // 對應資料庫欄位：EventID
    private int eventId;

    // 對應資料庫欄位：CharitiesID
    private int charityId;

    // 對應資料庫欄位：EventName
    private String eventName;

    // 對應資料庫欄位：EventDescription
    private String eventDescription;

    // 對應資料庫欄位：EventDate
    private Date eventDate;

    // 對應資料庫欄位：EventLocation
    private String Location;

    // 對應資料庫欄位：EventStart
    private Time eventStart;

    // 對應資料庫欄位：EventEnd
    private Time eventEnd;

    // === Getters & Setters ===

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getCharityId() {
        return charityId;
    }

    public void setCharityId(int charityId) {
        this.charityId = charityId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return Location;
    }

    public void setEventLocation(String Location) {
        this.Location = Location;
    }

    public Time getEventStart() {
        return eventStart;
    }

    public void setEventStart(Time eventStart) {
        this.eventStart = eventStart;
    }

    public Time getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(Time eventEnd) {
        this.eventEnd = eventEnd;
    }
}
