package com.popogonry.notid.alarm.repository;

import com.popogonry.notid.alarm.Alarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemoryAlarmRepository implements AlarmRepository {

//    HashMap<AlarmId, Alarm>
    private static final HashMap<Long, Alarm> alarmHashMap = new HashMap<>();
//    HashMap<UserId, List<AlarmId>>
    private static final HashMap<String, List<Long>> userAlarmHashMap = new HashMap<>();

    @Override
    public void addAlarmData(Alarm alarm) {
        alarmHashMap.put(alarm.getId(), alarm);
    }

    @Override
    public Alarm getAlarmData(Long alarmId) {
        return alarmHashMap.get(alarmId);
    }

    @Override
    public boolean hasAlarmData(Long alarmId) {
        return alarmHashMap.containsKey(alarmId);
    }

    @Override
    public void removeAlarmData(Long alarmId) {
        alarmHashMap.remove(alarmId);
    }

    @Override
    public void addUserAlarmData(String userId, Long alarmId) {
        List<Long> list = userAlarmHashMap.getOrDefault(userId, new ArrayList<>());
        list.add(alarmId);
        userAlarmHashMap.put(userId, list);
    }

    @Override
    public boolean hasUserAlarmData(String userId, Long alarmId) {
        return userAlarmHashMap.getOrDefault(userId, new ArrayList<>()).contains(alarmId);
    }

    @Override
    public void removeUserAlarmData(String userId, Long alarmId) {
        userAlarmHashMap.getOrDefault(userId, new ArrayList<>()).remove(alarmId);
    }

    @Override
    public void addUserAlarmListData(String userId, List<Long> alarmList) {
        userAlarmHashMap.put(userId, alarmList);
    }

    @Override
    public List<Long> getUserAlarmListData(String userId) {
        return userAlarmHashMap.getOrDefault(userId, new ArrayList<>());
    }

    @Override
    public boolean hasUserAlarmListData(String userId) {
        return userAlarmHashMap.containsKey(userId);
    }

    @Override
    public void removeUserAlarmListData(String userId) {
        userAlarmHashMap.remove(userId);
    }


    @Override
    public void clearAll() {
        alarmHashMap.clear();
        userAlarmHashMap.clear();
    }
}
