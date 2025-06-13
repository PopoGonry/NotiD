package com.popogonry.notid.cli;

import com.popogonry.notid.Config;
import com.popogonry.notid.alarm.Alarm;
import com.popogonry.notid.alarm.AlarmService;
import com.popogonry.notid.alarm.repository.AlarmRepository;
import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelJoinType;
import com.popogonry.notid.channel.ChannelService;
import com.popogonry.notid.channel.ChannelUserGrade;
import com.popogonry.notid.channel.repository.ChannelRepository;
import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.notice.repository.NoticeRepository;
import com.popogonry.notid.user.User;

import java.text.SimpleDateFormat;
import java.util.*;

public class MainView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final Config config = new Config();

    private static final ChannelService channelService = config.channelService();
    private static final AlarmService alarmService = config.alarmService();

    private static final AlarmRepository alarmRepository = config.alarmRepository();
    private static final ChannelRepository channelRepository = config.channelRepository();
    private static final NoticeRepository noticeRepository = config.noticeRepository();

    public static void mainViewMain(User user) {

        if(!alarmRepository.getUserAlarmListData(user.getId()).isEmpty()) {
            System.out.println("알림이 존재합니다!");
        }


        System.out.println("--- 메인 화면 ---");

        System.out.println("1. 알람 확인");
        System.out.println("2. 내 채널 리스트");
        System.out.println("3. 내 공지 리스트");
        System.out.println("4. 새 채널 찾기");
        System.out.println("5. 새 채널 만들기");
        System.out.println("6. 로그아웃");


        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 6, value));

        Common.clear();
        switch (Integer.parseInt(value)) {
            case 1:
                checkAlarm(user);
                break;

            case 2:
                userChannelList(user);
                break;

            case 3:
                userNoticeList(user);
                break;

            case 4:
                searchChannel(user);
                break;

            case 5:
                createChannel(user);
                break;

            case 6:
                Authentication.authenticate();
                  break;
        }
    }

    public static void checkAlarm(User user) {
        System.out.println("--- " + user.getName() + " 알림 리스트 ---");

        List<Long> userAlarmList = alarmRepository.getUserAlarmListData(user.getId());

        if(userAlarmList.isEmpty()) {
            Common.clear();
            System.out.println("알림이 존재하지 않습니다.");
            mainViewMain(user);
            return;

        }

        int i = 1;
        for (Long alarmId : userAlarmList) {
            Alarm alarm = alarmRepository.getAlarmData(alarmId);
            System.out.println(i + ". " + alarm.getContent() + " (" + formatter.format(alarm.getSendDate()) + ")");
            i++;
        }

        System.out.println(i + ". 돌아가기");

        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, i, value));

        Common.clear();

        if (Integer.parseInt(value) == i) {
            mainViewMain(user);
            return;
        }

        alarmService.deleteAlarm(userAlarmList.get(Integer.parseInt(value) - 1));
        System.out.println("알림을 확인하였습니다.");
        checkAlarm(user);
    }

    public static void userChannelList(User user) {
        if (!channelRepository.hasUserChannelSetData(user.getId()) || channelRepository.getUserChannelSetData(user.getId()).isEmpty()) {
            Common.clear();
            System.out.println("가입한 채널이 없습니다.");
            mainViewMain(user);
            return;
        }

        System.out.println("--- " + user.getName() + " 채널 리스트 ---");

        ArrayList<String> userChannelList = new ArrayList<>(channelRepository.getUserChannelSetData(user.getId()));

        int i = 1;
        for (String userChannel : userChannelList) {
            System.out.println(i + ". " + userChannel);
            i++;
        }
        System.out.println(i + ". 돌아가기");

        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, i, value));

        Common.clear();

        if (Integer.parseInt(value) == i) {
            mainViewMain(user);
            return;
        }

        Channel channel = channelRepository.getChannelData(userChannelList.get(Integer.parseInt(value) - 1));
        ChannelView.channelViewMain(channel, user);
    }

    public static void userNoticeList(User user) {
        if (!channelRepository.hasUserChannelSetData(user.getId()) || channelRepository.getUserChannelSetData(user.getId()).isEmpty()) {
            Common.clear();
            System.out.println("가입한 채널이 없습니다.");
            mainViewMain(user);
            return;
        }

        System.out.println("--- " + user.getName() + " 공지 리스트 ---");

        List<Long> noticeList = new ArrayList<>();

        int i = 1;
        for (String userChannel : channelRepository.getUserChannelSetData(user.getId())) {
            for (Long channelNotice : noticeRepository.getChannelNoticeSetData(userChannel)) {
                Notice notice = noticeRepository.getNoticeData(channelNotice);
                Channel channel = channelRepository.getChannelData(userChannel);
                if(channelService.canAccessChannel(user, notice)) {
                    if((channel.getChannelUserGrade(user.getId()) == ChannelUserGrade.ADMIN || channel.getChannelUserGrade(user.getId()) == ChannelUserGrade.MANAGER)) {
                        if(notice.getScheduledTime().after(new Date())) {
                            System.out.println(i + ". " + notice.getTitle() + "(" + formatter.format(notice.getScheduledTime()) + " 공개 예정)");
                        }
                        else {
                            System.out.println(i + ". " + notice.getTitle());
                        }
                    }
                    else if(notice.getScheduledTime().before(new Date())) {
                        System.out.println(i + ". " + notice.getTitle());
                    }
                    i++;
                    noticeList.add(channelNotice);
                }
            }
        }
        if (i == 1) {
            Common.clear();
            System.out.println("공지가 존재하지 않습니다.");
            mainViewMain(user);
            return;
        }

        System.out.println(i + ". 돌아가기");

        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, i, value));

        Common.clear();
        if (Integer.parseInt(value) == i) {
            mainViewMain(user);
            return;
        }
        Notice notice = noticeRepository.getNoticeData(noticeList.get(Integer.parseInt(value) - 1));
        NoticeView.noticeViewMain(notice, user);
    }

    public static void searchChannel(User user) {
        System.out.print("채널 이름을 입력하세요: ");
        String keyword = scanner.nextLine();
        Common.clear();
        if (!channelRepository.hasChannelData(keyword)) {
            System.out.println("존재하지 않는 채널입니다.");
            mainViewMain(user);
            return;
        }

        Channel channel = channelRepository.getChannelData(keyword);

        if(channel.getChannelUserGrade(user.getId()) == null) {
            ChannelView.joinChannel(channel, user);
        }
        else {
            ChannelView.channelViewMain(channel, user);
        }
    }

    public static void createChannel(User user) {

        String name;
        do {
            System.out.print("이름: ");
            name = scanner.nextLine();
        } while (name.isEmpty());


        if (channelRepository.hasChannelData(name)) {
            System.out.println("이미 존재하는 채널 이름입니다.");
            mainViewMain(user);
            return;
        }

        String description;
        do {
            System.out.print("설명: ");
            description = scanner.nextLine();
        } while (description.isEmpty());

        System.out.print("조직(미입력 가능): ");
        String affiliation = scanner.nextLine();

        String value;
        do {
            System.out.print("가입 형식(1. 자유, 2. 수락): ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 2, value));

        ChannelJoinType channelJoinType = null;

        switch (Integer.parseInt(value)) {
            case 1:
                channelJoinType = ChannelJoinType.FREE;
                break;

            case 2:
                channelJoinType = ChannelJoinType.ACCEPT;
                break;
        }

        Common.clear();

        channelService.createChannel(name, description, affiliation, channelJoinType);

        Channel channel = channelRepository.getChannelData(name);

        channelRepository.addUserChannelData(user.getId(), channel.getName());
        channel.addChannelUserGrade(user.getId(), ChannelUserGrade.ADMIN);

        ChannelView.channelViewMain(channel, user);
    }
}