package DataSchema;

/**
 * Created by shverm on 1/29/2016.
 */
public class Channel {
    public Geofence geofence;
    public User admin;
    public int channelID;

    public Channel(Geofence geofence, User admin, int channelID) {
        this.geofence = geofence;
        this.admin = admin;
        this.channelID = channelID;
    }
}
