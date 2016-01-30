package org.telegram.messenger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by lebysani on 30-Jan-16.
 */
@SuppressLint("NewApi")
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new WidgetServiceDataProvider(this.getApplicationContext(), intent));
    }

}
