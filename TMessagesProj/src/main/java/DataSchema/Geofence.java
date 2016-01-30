package DataSchema;

/**
 * Created by shverm on 1/29/2016.
 */
public class Geofence {
    private String[] constant;

    private String[] multiple;

    private Coordinates[] coordinates;

    public String[] getConstant() {
        return constant;
    }

    public void setConstant(String[] constant) {
        this.constant = constant;
    }

    public String[] getMultiple() {
        return multiple;
    }

    public void setMultiple(String[] multiple) {
        this.multiple = multiple;
    }

    public Coordinates[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "ChannelInfo [constant = " + constant + ", multiple = " + multiple + ", coordinates = " + coordinates + "]";
    }
}
