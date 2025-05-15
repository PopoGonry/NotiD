package notid.channel;

public class Channel {
    private String name;
    private String description;
    private String affiliation;
    private ChannelJoinType joinType;

    public Channel(String name, String description, String affiliation, ChannelJoinType joinType) {
        this.name = name;
        this.description = description;
        this.affiliation = affiliation;
        this.joinType = joinType;
    }

    public Channel(String description, String name, ChannelJoinType joinType) {
        this.description = description;
        this.name = name;
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
