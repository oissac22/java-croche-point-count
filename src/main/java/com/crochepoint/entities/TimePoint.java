package com.crochepoint.entities;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

import com.crochepoint.exceptions.TimeError;

public class TimePoint implements Serializable {
    
    private String id = null;
    private LocalDateTime timeStart = null;
    private LocalDateTime timeEnd = null;
    private boolean timeStarted = false;
    private boolean finished = false;

    public TimePoint(String id) {
        this.id = id;
    }

    public void start() {
        if (timeStarted)
            throw new TimeError("O cronometro já foi iniciado");
        timeStarted = true;
        timeStart = LocalDateTime.now();
    }

    public void end() {
        if (!timeStarted)
            throw new TimeError("O cronometro não foi iniciado");
        timeStarted = false;
        finished = true;
        timeEnd = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }

    public boolean isFinished() {
        return finished;
    }

    public String toString() {
        return "ID="+getId()+"; Started="+timeStarted+"; Finished="+isFinished();
    }

    public Long SecondsThatPassed() {
        if ((timeStart == null) || (timeEnd == null)) {
            return 0L;
        }
        Instant iStart = getTimeStart().toInstant(null);
        Instant iEnd = getTimeEnd().toInstant(null);
        return Duration.between(iStart, iEnd).toMillis();
    }

}
