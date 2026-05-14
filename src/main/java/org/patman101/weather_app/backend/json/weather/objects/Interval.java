package org.patman101.weather_app.backend.json.weather.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.patman101.weather_app.backend.json.weather.deserializers.IntervalDeserializer;

import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = IntervalDeserializer.class)
public class Interval {
    private ZonedDateTime startTime, endTime;

    public Interval(ZonedDateTime startTime, ZonedDateTime endTime) throws NullPointerException {
        this.setStartTime(startTime);
        this.setEndTime(endTime);
    }

    public void setStartTime(ZonedDateTime startTime) throws NullPointerException {
        if (startTime == null) {
            throw new NullPointerException("startTime is null");
        }
        this.startTime = startTime;
    }

    public void setEndTime(ZonedDateTime endTime) throws NullPointerException {
        if (endTime == null) {
            throw new NullPointerException("endTime is null");
        }
        this.endTime = endTime;
    }

    public ZonedDateTime getStartTime() { return this.startTime; }

    public ZonedDateTime getEndTime() { return this.endTime; }
}
