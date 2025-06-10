package com.popogonry.notid.channel;

import java.util.HashMap;
import java.util.HashSet;

public class Channel {
    private String name;
    private String description;
    private String affiliation;
    private ChannelJoinType joinType;

    private final HashMap<String, ChannelUserGrade> channelUserGradeHashMap = new HashMap<>();
    private final HashSet<String> channnelJoiningUserSet = new HashSet<>();

    public Channel(String name, String description, String affiliation, ChannelJoinType joinType) {
        this.name = name;
        this.description = description;
        this.affiliation = affiliation;
        this.joinType = joinType;
    }

    public Channel(String name, String description, ChannelJoinType joinType) {
        this.name = name;
        this.description = description;
        this.affiliation = "";
        this.joinType = joinType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public ChannelJoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(ChannelJoinType joinType) {
        this.joinType = joinType;
    }

    public HashMap<String, ChannelUserGrade> getChannelUserGradeHashMap() {
        return channelUserGradeHashMap;
    }

    public ChannelUserGrade getChannelUserGrade(String userId) {
        return channelUserGradeHashMap.get(userId);
    }

    public void setChannelUserGrade(String userId, ChannelUserGrade channelUserGrade) {
        channelUserGradeHashMap.put(userId, channelUserGrade);
    }

    public boolean hasChannelUserGrade(String userId) {
        return channelUserGradeHashMap.containsKey(userId);
    }

    public void addChannelUserGrade(String userId, ChannelUserGrade channelUserGrade) {
        channelUserGradeHashMap.put(userId, channelUserGrade);
    }

    public void removeChannelUserGrade(String userId) {
        channelUserGradeHashMap.remove(userId);
    }

    public HashSet<String> getChannnelJoiningUserSet() {
        return channnelJoiningUserSet;
    }

    public void addChannnelJoiningUser(String userId) {
        channnelJoiningUserSet.add(userId);
    }

    public void removeChannnelJoiningUser(String userId) {
        channnelJoiningUserSet.remove(userId);
    }

    @Override
    public String toString() {
        return "Channel{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", joinType=" + joinType +
                '}';
    }
}
