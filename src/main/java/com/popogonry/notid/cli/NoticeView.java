package com.popogonry.notid.cli;

import com.popogonry.notid.notice.Notice;

public interface NoticeView {

    void noticeViewMain(Notice notice);

    void noticeToManager();

    void noticeToMember();

    void updateNotice();

    void userReplyList();

    void deleteNotice();

    void sendNoticeAlarm();

    void createReply();

}
