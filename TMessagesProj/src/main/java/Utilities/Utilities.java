package Utilities;

import java.util.ArrayList;
import java.util.List;

import DataSchema.Channel;

/**
 * Created by shverm on 1/29/2016.
 */
public class Utilities {

    public List<Channel> buildDummyData() {
//        GeoCordinates microsoftLeftBottom = new GeoCordinates(17.42726335237322, 78.34141373634338);
//        GeoCordinates microsoftLeftTop = new GeoCordinates(17.429023997842013, 78.33863496780396);
//        GeoCordinates microsoftRightTop = new GeoCordinates(17.43590263591793, 78.34269046783447);
//        GeoCordinates microsoftRightBottom = new GeoCordinates(17.43414205681682, 78.34575891494751);
//
//        GeoCordinates mprLeftTop = new GeoCordinates(17.429554363369927, 78.34092993289232);
//        GeoCordinates mprLeftBottom = new GeoCordinates(17.42954476690242, 78.34095273166895);
//        GeoCordinates mprRightTop = new GeoCordinates(17.429711745365164, 78.34103118628263);
//        GeoCordinates mprRightBottom = new GeoCordinates(17.429697670558106, 78.34106404334307);
//
//
//
//        Geofence microsoftGeofence = new Geofence(microsoftLeftBottom, microsoftLeftTop, microsoftRightTop, microsoftRightBottom);
//        Geofence mprGeofence = new Geofence(mprLeftBottom, mprLeftTop, mprRightTop, mprRightBottom);
//
//        User microsoftAdmin = new User(1, "admin");
//        User mprAdmin = new User(2, "admin");
//
//        Channel microsoftChannel = new Channel(microsoftGeofence, microsoftAdmin, 1);
//        Channel mprChannel = new Channel(mprGeofence, mprAdmin, 2);
//
        List<Channel> channelList = new ArrayList<Channel>();
//        channelList.add(microsoftChannel);
//        channelList.add(mprChannel);
        return channelList;
    }
}
