package notid.channel.repository;

import notid.channel.Channel;

import java.util.HashMap;
import java.util.HashSet;

public class MemoryChannelRepository implements ChannelRepository {
    // HashMap <UserID, Channel>
    private final HashMap<String, Channel> channelHashMap = new HashMap<>();

    // HashMap <UserID, HashSet<ChannelName>>
    private final HashMap<String, HashSet<String>> userChannelHashMap = new HashMap<>();

    @Override
    public void addChannelData(Channel channel) {
    }

    @Override
    public Channel getChannelData(String channelName) {
        return null;
    }

    @Override
    public boolean hasChannelData(String channelName) {
        return false;
    }

    @Override
    public void removeChannelData(String channelName) {

    }

    @Override
    public void addUserChannelData(String userId, Channel channel) {

    }

    @Override
    public boolean hasUserChannelData(String userId, String channelName) {
        return false;
    }

    @Override
    public void removeUserChannelData(String userId, String channelName) {

    }

    @Override
    public void addUserChannelSetData(String userId, HashSet<Channel> channelSet) {

    }

    @Override
    public HashSet<String> getUserChannelSetData(String userId) {
        return null;
    }

    @Override
    public boolean hasUserChannelSetData(String userId) {
        return false;
    }

    @Override
    public void removeUserChannelSetData(String userId) {

    }
}
