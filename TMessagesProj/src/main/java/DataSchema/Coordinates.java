package DataSchema;

/**
 * Created by gaukumar on 30-01-2016.
 */
public class Coordinates {
    private String longitude;

    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "ChannelInfo [longitude = " + longitude + ", latitude = " + latitude + "]";
    }
}
