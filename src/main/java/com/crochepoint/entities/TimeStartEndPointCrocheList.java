package com.crochepoint.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.crochepoint.exceptions.TimeError;


public class TimeStartEndPointCrocheList implements Serializable {

    static private List<TimeStartEndPointCroche> listTimes = new ArrayList<>();
    static private TimeStartEndPointCroche lastTime = null;
    static private boolean started = false;

    public List<TimeStartEndPointCroche> getListTimes() {
        return listTimes;
    }
    
    public boolean getStarted() {
        return started;
    }

    public TimeStartEndPointCroche start() {
        if (started) {
            throw new TimeError("Time started");
        }
        lastTime = new TimeStartEndPointCroche();
        lastTime.start();
        listTimes.add(lastTime);
        started = true;
        return lastTime;
    }

    public TimeStartEndPointCroche end() {
        if (!started) {
            throw new TimeError("Time no started");
        }
        lastTime.end();
        started = false;
        return lastTime;
    }
    
}
