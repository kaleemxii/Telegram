package DataSchema;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by shverm on 1/29/2016.
 */
public class Channel {
    public Geofence geofence;
    public User admin;
    public String channelID;
    public String channelTag;
    private transient HashMap<Integer, User> usersById;

    public Channel(Geofence geofence, User admin, String channelID) {
        this.geofence = geofence;
        this.admin = admin;
        this.channelID = channelID;
        usersById = new HashMap<>();
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Geofence getGeofence() {
        return geofence;
    }

    public void setGeofence(Geofence geofence) {
        this.geofence = geofence;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getChannelTag() {
        return channelTag;
    }

    public void setChannelTag(String channelTag) {
        this.channelTag = channelTag;
    }

    public Collection<User> getUsers() {
        return usersById.values();
    }

    public void addUser(User user) {
        usersById.put(user.userId, user);
    }

    public void removeUser(String userId) {
        usersById.remove(userId);
    }
}
