package com.crochepoint.entities;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.Duration;

import com.crochepoint.db.ConnectionSQLite;
import com.crochepoint.exceptions.DBException;
import com.crochepoint.exceptions.TimeError;

public class TimeStartEndPointCroche implements Serializable {

    private String id = null;
    private Instant timeStart = null;
    private Instant timeEnd = null;
    private String list_id = null;

    static public TimeStartEndPointCroche newTime(String list_id) {
        TimeStartEndPointCroche obj = new TimeStartEndPointCroche(RandomKey.generateRandonKeyOnHex(10),null, null, list_id);
        PreparedStatement ps = ConnectionSQLite.prepareStatement("insert into simple_time (id, listTime_id) values (?,?)");
        try {
            ps.setString(1, obj.getId());
            ps.setString(2, list_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return obj;
    }
    
    static public TimeStartEndPointCroche loadTime(String id) {
        PreparedStatement ps = ConnectionSQLite.prepareStatement("select id, timeStart, timeEnd, listTime_id from simple_time where id=?");
        try {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Instant timeStart = Instant.parse(rs.getString("timeStart"));
                Instant timeEnd = Instant.parse(rs.getString("timeStart"));
                String list_id = rs.getString("list_id");
                TimeStartEndPointCroche obj = new TimeStartEndPointCroche(id, timeStart, timeEnd, list_id);
                return obj;
            }
            throw new DBException("Nenhum registro encontrado");
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public TimeStartEndPointCroche(String id, Instant timeStart, Instant timeEnd, String list_id) {
        if (id == null || id == "") {
            id = RandomKey.generateRandonKeyOnHex(10);
        }
        this.id = id;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.list_id = list_id;
    }

    public void start() {
        if (started())
            throw new TimeError("Time started in TimeStartEndPointCroche class");
        timeStart = Instant.now();
        ConnectionSQLite.execQuery("update simple_time set timeStart='"+timeStart+"' where id='"+id+"'");
    }

    public void end() {
        if (finished())
            throw new TimeError("Time finished in TimeStartEndPointCroche class");
        timeEnd = Instant.now();
        ConnectionSQLite.execQuery("update simple_time set timeEnd='"+timeEnd+"' where id='"+id+"'");
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

    public String getId() {
        return id;
    }

    public String getList_id() {
        return list_id;
    }
    
}
