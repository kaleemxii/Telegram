package DataSchema;

/**
 * Created by shverm on 1/29/2016.
 */
public class User {
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
