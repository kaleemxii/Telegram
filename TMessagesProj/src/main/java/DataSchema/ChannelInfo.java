package DataSchema;

/**
 * Created by gaukumar on 30-01-2016.
 */
public class ChannelInfo {
    private Geofence geofence;

    //private User users;

    private Admin admin;

    private String channelID;

    public Geofence getGeofence() {
        return geofence;
    }

    public void setGeofence(Geofence geofence) {
        this.geofence = geofence;
    }

//    public User getUsers ()
//    {
//        return users;
//    }
//
//    public void setUsers (User users)
//    {
//        this.users = users;
//    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    @Override
    public String toString() {
        return "ChannelInfo [geofence = " + geofence + ", admin = " + admin + ", channelID = " + channelID + "]";
    }
}
