package com.crochepoint.entities;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.crochepoint.db.ConnectionSQLite;
import com.crochepoint.exceptions.DBException;
import com.crochepoint.exceptions.TimeError;


public class TimeStartEndPointCrocheList implements Serializable {

    private String id = null;
    private List<TimeStartEndPointCroche> listTimes = new ArrayList<>();
    private TimeStartEndPointCroche lastTime = null;
    private boolean started = false;
    private Instant createdDate = null;
    private boolean databaseLoadedList = false;

    static public TimeStartEndPointCrocheList newTime() {
        TimeStartEndPointCrocheList obj = new TimeStartEndPointCrocheList(RandomKey.generateRandonKeyOnHex(10));
        PreparedStatement ps = ConnectionSQLite.prepareStatement("insert into list_times (id, created) values (?, ?)");
        Instant now = Instant.now();
        try {
            ps.setString(1, obj.getId());
            ps.setString(2, now.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return obj;
    }

    static public TimeStartEndPointCrocheList loadTime(String id) {
        TimeStartEndPointCrocheList obj = new TimeStartEndPointCrocheList(id);
        PreparedStatement ps = ConnectionSQLite.prepareStatement("select id, created from list_times where id=?");
        try {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj.id = id;
                obj.createdDate = Instant.parse(rs.getString("created"));
                return obj;
            }
            throw new DBException("Nenhum registro encontrado");
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    static public TimeStartEndPointCrocheList loadLastTime() {
        TimeStartEndPointCrocheList obj = new TimeStartEndPointCrocheList(null);
        PreparedStatement ps = ConnectionSQLite.prepareStatement("select id, created from list_times order by created desc limit 1");
        try {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj.id = rs.getString("id");
                obj.createdDate = Instant.parse(rs.getString("created"));
                return obj;
            }
            return newTime();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    private void loadList() {
        if (databaseLoadedList) {
            return;
        }
        listTimes.clear();
        PreparedStatement ps = ConnectionSQLite.prepareStatement("select id, timeStart, timeEnd, listTime_id from simple_time where listTime_id=?");
        try {
            ps.setString(1, getId());
            ResultSet rs = ps.executeQuery();
            boolean verifyStarted = false;
            while(rs.next()) {
                String idObj = rs.getString("id");
                String _ts = rs.getString("timeStart");
                String _te = rs.getString("timeEnd");
                Instant timeStart = _ts == null ? null : Instant.parse(_ts);
                Instant timeEnd = _te == null ? null :Instant.parse(_te);
                TimeStartEndPointCroche tsepc = new TimeStartEndPointCroche(idObj, timeStart, timeEnd, id);
                lastTime = tsepc;
                listTimes.add(tsepc);
                verifyStarted = (timeStart != null) && (timeEnd == null);
            }
            started = verifyStarted;
            databaseLoadedList = true;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }

    }

    public TimeStartEndPointCrocheList(String id) {
        this.id = id;
    }

    public List<TimeStartEndPointCroche> getListTimes() {
        loadList();
        return listTimes;
    }
    
    public boolean getStarted() {
        return started;
    }

    public TimeStartEndPointCroche start() {
        if (started) {
            throw new TimeError("Time started");
        }
        lastTime = TimeStartEndPointCroche.newTime(id);
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

    public long getTotalPassedTime() {
        long total = 0;
        for (TimeStartEndPointCroche t:listTimes) {
            total += t.getTimePassed();
        }
        return total;
    }

    public String getId() {
        return id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }
    
}
