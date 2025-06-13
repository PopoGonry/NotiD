package com.popogonry.notid.alarm;

import com.popogonry.notid.alarm.repository.AlarmRepository;
import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelService;
import com.popogonry.notid.channel.ChannelUserGrade;
import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.user.repository.UserRepositoy;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepositoy userRepositoy;
    private final ChannelService channelService;

    public AlarmService(AlarmRepository alarmRepository, UserRepositoy userRepositoy, ChannelService channelService) {
        this.alarmRepository = alarmRepository;
        this.userRepositoy = userRepositoy;
        this.channelService = channelService;
    }

    private static final AtomicLong counter = new AtomicLong(1);

    public void createAlarm(String userId, String message) {
        long newId = counter.getAndIncrement();

        // 똑같은 id가 존재한다면,
        if(alarmRepository.hasAlarmData(newId)) return;

        // userId의 User 가 존재하지 않는다면
        if(!userRepositoy.hasUserData(userId)) return;


        Alarm alarm = new Alarm(newId, userRepositoy.getUserData(userId), message, new Date());
        alarmRepository.addAlarmData(alarm);
        alarmRepository.addUserAlarmData(userId, newId);

        return;
    }

    public void deleteAlarm(long alarmId) {
        // id가 존재하지 않는다면
        if(!alarmRepository.hasAlarmData(alarmId)) return;

        Alarm alarm = alarmRepository.getAlarmData(alarmId);

        alarmRepository.removeAlarmData(alarm.getId());
        alarmRepository.removeUserAlarmData(alarm.getUser().getId(), alarm.getId());

        return;
    }

    public void sendAlarmNoticeToChannelUsers(Notice notice, String message) {

        Set<String> userSet = notice.getChannel().getChannelUserGradeHashMap().keySet();
        for (String userId : userSet) {
            if(channelService.canAccessChannel(userRepositoy.getUserData(userId), notice) && notice.getScheduledTime().before(new Date())) {
                createAlarm(userId, message);
            }
        }
    }

    public void sendAlarmToChannelManagers(Channel channel, String message) {
        for (String userId : channel.getChannelUserGradeHashMap().keySet()) {
            if(channel.getChannelUserGrade(userId) == ChannelUserGrade.MANAGER
                    || channel.getChannelUserGrade(userId) == ChannelUserGrade.ADMIN) {
                createAlarm(userId, message);
            }
        }
    }
}
