package DataSchema;

/**
 * Created by gaukumar on 30-01-2016.
 */
public class Admin {
    private String userId;

    private String userTag;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserTag() {
        return userTag;
    }

    public void setUserTag(String userTag) {
        this.userTag = userTag;
    }

    @Override
    public String toString() {
        return "ChannelInfo [userId = " + userId + ", userTag = " + userTag + "]";
    }
}
