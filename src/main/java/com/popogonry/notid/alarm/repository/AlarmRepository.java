package com.popogonry.notid.alarm.repository;

import com.popogonry.notid.alarm.Alarm;

import java.util.HashSet;
import java.util.List;

public interface AlarmRepository {

    void addAlarmData(Alarm alarm);
    Alarm getAlarmData(Long alarmId);
    boolean hasAlarmData (Long alarmId);
    void removeAlarmData(Long alarmId);

    void addUserAlarmData(String userId, Long alarmId);
    boolean hasUserAlarmData(String userId, Long alarmId);
    void removeUserAlarmData(String userId, Long alarmId);

    void addUserAlarmListData(String userId, List<Long> alarmList);
    List<Long> getUserAlarmListData(String userId);
    boolean hasUserAlarmListData(String userId);
    void removeUserAlarmListData(String userId);

    void clearAll();

}
