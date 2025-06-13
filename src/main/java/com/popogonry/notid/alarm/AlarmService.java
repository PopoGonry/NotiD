package com.popogonry.notid.alarm;

import com.popogonry.notid.alarm.repository.AlarmRepository;
import com.popogonry.notid.user.User;
import com.popogonry.notid.user.repository.UserRepositoy;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepositoy userRepositoy;
    private static final AtomicLong counter = new AtomicLong(1);


    public AlarmService(AlarmRepository alarmRepository, UserRepositoy userRepositoy) {
        this.alarmRepository = alarmRepository;
        this.userRepositoy = userRepositoy;
    }

    public long createAlarm(String userId, String content) {
        long newId = counter.getAndIncrement();

        // 똑같은 id가 존재한다면,
        if(alarmRepository.hasAlarmData(newId)) return 0L;

        // userId의 User 가 존재하지 않는다면
        if(!userRepositoy.hasUserData(userId)) return 0L;


        Alarm alarm = new Alarm(newId, userRepositoy.getUserData(userId), content, new Date());
        alarmRepository.addAlarmData(alarm);
        alarmRepository.addUserAlarmData(userId, newId);

        return newId;
    }

}
