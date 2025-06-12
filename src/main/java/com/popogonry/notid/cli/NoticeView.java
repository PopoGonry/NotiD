package com.popogonry.notid.cli;

import com.popogonry.notid.Config;
import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelUserGrade;
import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.notice.repository.MemoryNoticeRepository;
import com.popogonry.notid.notice.repository.NoticeRepository;
import com.popogonry.notid.reply.Reply;
import com.popogonry.notid.reply.ReplyService;
import com.popogonry.notid.reply.replyRepository.MemoryReplyRepository;
import com.popogonry.notid.reply.replyRepository.ReplyRepository;
import com.popogonry.notid.user.User;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class NoticeView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static final Config config = new Config();

    private static final NoticeRepository noticeRepository = config.noticeRepository();

    private static final ReplyService replyService = config.replyService();
    private static final ReplyRepository replyRepository = config.replyRepository();


    public static void noticeViewMain(Notice notice, User user) {
        Channel channel = notice.getChannel();

        if (!channel.hasChannelUserGrade(user.getId())) {
            System.out.println(user.getId() + " 유저는 채널에 존재하지 않습니다.");
        }

        ChannelUserGrade channelUserGrade = channel.getChannelUserGrade(user.getId());
        if (channelUserGrade == ChannelUserGrade.NORMAL) {
            noticeToMember(notice, user);
        } else if (channelUserGrade == ChannelUserGrade.MANAGER || channelUserGrade == ChannelUserGrade.ADMIN) {
            noticeToManager(notice, user);
        }
    }

    public static void noticeToManager(Notice notice, User user) {
        System.out.println("--- 공지 관리자 메뉴 ---");

        System.out.println("제목: " + notice.getTitle());
        System.out.println("채널: " + notice.getChannel().getName());

        if(notice.getScheduledTime().after(new Date())) {
            System.out.println("공지 공개 시간: " + formatter.format(notice.getScheduledTime()));
        }
        if(notice.getReplyDeadline().after(new Date())) {
            System.out.println("답장 제한 시간: " + formatter.format(notice.getReplyDeadline()));
        }

        String grade = "";
        if (notice.getUserGrade() == ChannelUserGrade.NORMAL) {
            grade = "일반 유저";
        } else if (notice.getUserGrade() == ChannelUserGrade.MANAGER) {
            grade = "채널 관리자";
        } else if (notice.getUserGrade() == ChannelUserGrade.ADMIN) {
            grade = "채널 소유자";
        }
        System.out.println("공지 접근 권한: " + grade);

        System.out.println("내용: " + notice.getContent());

        System.out.println();

        System.out.println("1. 공지 수정");
        System.out.println("2. 공지 삭제");
        System.out.println("3. 답장 리스트");
        System.out.println("4. 공지 알림 보내기");
        System.out.println("5. 돌아가기");


        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 5, value));

        switch (Integer.parseInt(value)) {
            case 1:
                updateNotice(notice, user);
                break;

            case 2:
                deleteNotice(notice, user);
                break;

            case 3:
                userReplyList(notice, user);
                break;

            case 4:
                sendNoticeAlarm(notice, user);
                break;

            case 5:
                ChannelView.channelViewMain(notice.getChannel(), user);
                break;

        }
    }

    public static void noticeToMember(Notice notice, User user) {
        System.out.println("--- 공지 메뉴 ---");

        System.out.println("제목: " + notice.getTitle());
        System.out.println("채널: " + notice.getChannel().getName());

        if(notice.getReplyDeadline().after(new Date())) {
            System.out.println("답장 제한 시간: " + formatter.format(notice.getReplyDeadline()));
        }

        System.out.println("내용: " + notice.getContent());

        System.out.println();

        boolean hasReply = false;
        Reply reply = null;
        HashSet<Long> noticeReplySetData = replyRepository.getNoticeReplySetData(notice.getId());
        for (Long replyId : noticeReplySetData) {
            Reply replyData = replyRepository.getReplyData(replyId);
            if(replyData.getAuthor().equals(user)) {
                reply = replyData;
                hasReply = true;
                break;
            }
        }

        if(hasReply) {
            System.out.println("1. 답장 보기");
        }
        else {
            System.out.println("1. 답장 작성");
        }


        System.out.println("2. 돌아가기");


        String value;
        do {
            System.out.print("선택해주세요: ");
            value = scanner.nextLine().trim();
        } while (!ValidationCheck.intSelectCheck(1, 2, value));

        switch (Integer.parseInt(value)) {
            case 1:
                if(hasReply) ReplyView.replyViewMain(reply, user);
                else createReply(notice, user);
                break;

            case 2:
                deleteNotice(notice, user);
                break;

        }
    }

    public static void updateNotice(Notice notice, User user) {

    }

    public static void userReplyList(Notice notice, User user) {

    }

    public static void deleteNotice(Notice notice, User user) {

    }

    public static void sendNoticeAlarm(Notice notice, User user) {

    }

    public static void createReply(Notice notice, User user) {
        System.out.println("--- 답장 작성 ---");
        System.out.print("제목: ");
        String title = scanner.nextLine();

        System.out.print("내용: ");
        String content = scanner.nextLine();

        long replyId = replyService.createReply(title, content, null, notice.getId(), user.getId());

        if(replyId != 0L) {
            System.out.println("답장이 작성되었습니다.");
            ReplyView.replyViewMain(replyRepository.getReplyData(replyId), user);
        }
        else {
            System.out.println("답장 작성 중 문제가 발생했습니다.");
            noticeViewMain(notice, user);
        }
    }
}
