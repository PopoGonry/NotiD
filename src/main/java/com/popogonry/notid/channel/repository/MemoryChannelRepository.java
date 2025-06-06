package com.popogonry.notid.channel.repository;

import com.popogonry.notid.channel.Channel;

import java.util.HashMap;
import java.util.HashSet;

public class MemoryChannelRepository implements ChannelRepository {
    // HashMap <ChannelName, Channel>
    private static final HashMap<String, Channel> channelHashMap = new HashMap<>();

    // HashMap <UserID, HashSet<ChannelName>>
    private static final HashMap<String, HashSet<String>> userChannelHashMap = new HashMap<>();

    @Override
    public void addChannelData(Channel channel) {
        channelHashMap.put(channel.getName(), channel);
    }

    @Override
    public Channel getChannelData(String channelName) {
        return channelHashMap.get(channelName);
    }

    @Override
    public boolean hasChannelData(String channelName) {
        return channelHashMap.containsKey(channelName);
    }

    @Override
    public void removeChannelData(String channelName) {
        channelHashMap.remove(channelName);
    }

    @Override
    public void addUserChannelData(String userId, String channelName) {
        HashSet<String> set = userChannelHashMap.getOrDefault(userId, new HashSet<>());
        set.add(channelName);
        userChannelHashMap.put(userId, set);
    }

    @Override
    public boolean hasUserChannelData(String userId, String channelName) {
        return userChannelHashMap.getOrDefault(userId, new HashSet<>()).contains(channelName);
    }

    @Override
    public void removeUserChannelData(String userId, String channelName) {
        userChannelHashMap.getOrDefault(userId, new HashSet<>()).remove(channelName);
    }

    @Override
    public void addUserChannelSetData(String userId, HashSet<String> channelSet) {
        userChannelHashMap.put(userId, channelSet);
    }

    @Override
    public HashSet<String> getUserChannelSetData(String userId) {
        return userChannelHashMap.getOrDefault(userId, new HashSet<>());
    }

    @Override
    public boolean hasUserChannelSetData(String userId) {
        return userChannelHashMap.containsKey(userId);
    }

    @Override
    public void removeUserChannelSetData(String userId) {
        userChannelHashMap.remove(userId);
    }

    @Override
    public void clearAll() {
        channelHashMap.clear();
        userChannelHashMap.clear();
    }
}
