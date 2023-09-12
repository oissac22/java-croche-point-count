package com.crochepoint.entities;

import java.io.Serializable;
import java.time.Instant;
import java.time.Duration;

import com.crochepoint.exceptions.TimeError;

public class TimeStartEndPointCroche implements Serializable {

    private Instant timeStart = null;
    private Instant timeEnd = null;

    public void start() {
        if (started())
            throw new TimeError("Time started in TimeStartEndPointCroche class");
        timeStart = Instant.now();
    }

    public void end() {
        if (finished())
            throw new TimeError("Time finished in TimeStartEndPointCroche class");
        timeEnd = Instant.now();
    }

    public boolean started() {
        return timeStart != null;
    }

    public boolean finished() {
        return timeEnd != null;
    }

    public Instant getTimeStart() {
        return timeStart;
    }

    public Instant getTimeEnd() {
        return timeEnd;
    }

    public long getTimePassed() {
        if (timeStart == null) {
            return 0L;
        }
        if (timeEnd == null) {
            Instant now = Instant.now();
            Duration duration = Duration.between(timeStart, now);
            return duration.toMillis();
        }
            Duration duration = Duration.between(timeStart, timeEnd);
            return duration.toMillis();

    }
    
}
