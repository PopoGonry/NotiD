package com.popogonry.notid.cli;

import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelJoinType;
import com.popogonry.notid.channel.repository.ChannelRepository;
import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.notice.repository.NoticeRepository;
import com.popogonry.notid.user.User;
import com.popogonry.notid.user.repository.UserRepositoy;

import java.util.*;

public class MainViewImpl implements MainView {
    Scanner scanner = new Scanner(System.in);
    private final UserRepositoy userRepositoy;
    private final ChannelRepository channelRepository;
    private final NoticeRepository noticeRepository;

    private final NoticeView noticeView;
    private final ChannelView channelView;

    public MainViewImpl(UserRepositoy userRepositoy, ChannelRepository channelRepository, NoticeRepository noticeRepository, NoticeView noticeView, ChannelView channelView) {
        this.userRepositoy = userRepositoy;
        this.channelRepository = channelRepository;
        this.noticeRepository = noticeRepository;
        this.noticeView = noticeView;
        this.channelView = channelView;
    }

    @Override
    public void mainViewMain(User user) {
        System.out.println("--- 메인 화면 ---");

        System.out.println("1. 알람 확인");
        System.out.println("2. 내 채널 리스트");
        System.out.println("3. 내 공지 리스트");
        System.out.println("4. 새 채널 찾기");
        System.out.println("5. 새 채널 만들기");

        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 5, value));

        int intValue = Integer.parseInt(value);
        switch (intValue) {
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
        }
    }

    @Override
    public void checkAlarm(User user) {

    }

    @Override
    public void userChannelList(User user) {
        if (!channelRepository.hasUserChannelSetData(user.getId())) {
            System.out.println("가입한 채널이 없습니다.");
            mainViewMain(user);
            return;
        }

        ArrayList<String> userChannelList = new ArrayList<>(channelRepository.getUserChannelSetData(user.getId()));

        int i = 1;
        for (String userChannel : userChannelList) {
            System.out.println(i + ". " + userChannel);
            i++;
        }

        String value;
        do {
            System.out.print("선택해주세요 (돌아가기 : " + i + "): ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, i, value));

        if (Integer.parseInt(value) == i) {
            mainViewMain(user);
            return;
        }

        Channel channel = channelRepository.getChannelData(userChannelList.get(i - 1));
        channelView.channelViewMain(channel, user);
    }

    @Override
    public void userNoticeList(User user) {
        if (!channelRepository.hasUserChannelSetData(user.getId())) {
            System.out.println("가입한 채널이 없습니다.");
            mainViewMain(user);
            return;
        }

        List<Long> noticeList = new ArrayList<>();

        int i = 1;
        for (String userChannel : channelRepository.getUserChannelSetData(user.getId())) {
            for (Long channelNotice : noticeRepository.getChannelNoticeSetData(userChannel)) {
                System.out.println(i + ". " + noticeRepository.getNoticeData(channelNotice).getTitle());
                noticeList.add(channelNotice);
                i++;
            }
        }

        String value;
        do {
            System.out.print("선택해주세요 (돌아가기 : " + i + "): ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, i, value));

        if (Integer.parseInt(value) == i) {
            mainViewMain(user);
            return;
        }

        Notice notice = noticeRepository.getNoticeData(noticeList.get(i - 1));
        noticeView.noticeViewMain(notice);
    }

    @Override
    public void searchChannel(User user) {
        System.out.print("채널 이름을 입력하세요: ");
        String keyword = scanner.nextLine();

        if (!channelRepository.hasChannelData(keyword)) {
            System.out.println("존재하지 않는 채널입니다.");
            mainViewMain(user);
            return;
        }
        Channel channel = channelRepository.getChannelData(keyword);
        channelView.channelViewMain(channel, user);
    }

    @Override
    public void createChannel(User user) {
        System.out.print("이름을 입력하세요: ");
        String name = scanner.nextLine();

        if (channelRepository.hasChannelData(name)) {
            System.out.println("이미 존재하는 채널 이름입니다.");
            mainViewMain(user);
            return;
        }

        System.out.print("설명을 입력하세요: ");
        String description = scanner.nextLine();
        System.out.print("조직을 입력하세요(미입력 가능): ");
        String affiliation = scanner.nextLine();

        String value;
        do {
            System.out.print("가입 형식을 선택하세요 (1. 자유, 2. 수락): ");
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

        Channel channel = new Channel(name, description, affiliation, channelJoinType);

        channelRepository.addChannelData(channel);
        channelView.channelViewMain(channel, user);
    }
}