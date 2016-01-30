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

    private transient HashMap<Integer, User> usersById;

    public Channel(Geofence geofence, User admin, String channelID) {
        this.geofence = geofence;
        this.admin = admin;
        this.channelID = channelID;
        usersById = new HashMap<>();
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
