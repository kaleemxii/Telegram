package DataSchema;

/**
 * Created by shverm on 1/29/2016.
 */
public class Geofence {
    public GeoCordinates leftBottom;
    public GeoCordinates rightBottom;
    public GeoCordinates leftTop;
    public GeoCordinates rightTop;

    public Geofence(GeoCordinates leftBottom, GeoCordinates rightBottom, GeoCordinates leftTop, GeoCordinates rightTop) {
        this.leftBottom = leftBottom;
        this.rightBottom = rightBottom;
        this.leftTop = leftTop;
        this.rightTop = rightTop;
    }
}
