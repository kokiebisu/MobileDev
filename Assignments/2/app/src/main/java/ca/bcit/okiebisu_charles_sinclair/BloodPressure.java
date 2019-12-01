package ca.bcit.okiebisu_charles_sinclair;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class BloodPressure {
    String firebaseId;
    String userId;
    String readingDate;
    String readingTime;
    Float systolicReading;
    Float diastolicReading;
    String condition;

    public BloodPressure() {}

    public BloodPressure(String firebaseId, String userId, String readingDate, String readingTime, float systolicReading, float diastolicReading, String condition) {
        this.firebaseId = firebaseId;
        this.userId = userId;
        this.readingDate = readingDate;
        this.readingTime = readingTime;
        this.systolicReading = systolicReading;
        this.diastolicReading = diastolicReading;
        this.condition = condition;
    }

    public String getFirebaseId() {return this.firebaseId; }

    public String getUserId() {
        return this.userId;
    }

    public String getReadingDate() {
        return this.readingDate;
    }

    public String getReadingTime() {
        return this.readingTime;
    }

    public Float getSystolicReading() {
        return this.systolicReading;
    }

    public Float getDiastolicReading() {
        return this.diastolicReading;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setFirebaseId(String firebaseId) {this.firebaseId = firebaseId; }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setReadingDate(String readingDate) {
        this.readingDate = readingDate;
    }

    public void setReadingTime(String readingTime) {
        this.readingTime = readingTime;
    }

    public void setSystolicReading(Float systolicReading) {
        this.systolicReading = systolicReading;
    }

    public void setDiastolicReading(Float diastolicReading) {
        this.diastolicReading = diastolicReading;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

}
