package DataSchema;

/**
 * Created by shverm on 1/29/2016.
 */
public class User {
    public int userId;
    public String userTag;

    public User() {

    }

    public User(int userId, String userTag) {
        this.userId = userId;
        this.userTag = userTag;
    }

    public String getUserTag() {
        return userTag;
    }

    public void setUserTag(String userTag) {
        this.userTag = userTag;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
