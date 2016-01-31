package org.telegram.ui;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.R;
import org.telegram.messenger.WidgetService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Implementation of App Widget functionality.
 */
public class TelegramMessageWidget extends AppWidgetProvider {

    Timer timer;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        //timer = new Timer();
        //timer.scheduleAtFixedRate(new WidgetTimer(context, appWidgetManager, appWidgetIds), 1, 1000);
    }

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        //super.onReceive(context, intent);
//        int[] ids = intent.getExtras().getIntArray("appWidgetIds");
//        if (ids != null)
//            this.onUpdate(context, AppWidgetManager.getInstance(context), ids);
//    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @SuppressLint("NewApi")
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.telegram_message_widget);
        Intent svcIntent = new Intent(context, WidgetService.class);
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

        Intent intent  = new Intent(context, LaunchActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.widgetListView, contentIntent);
        views.setRemoteAdapter(R.id.widgetListView, svcIntent);

        Log.d("Widget", "updateAppWidget");
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

//    private class WidgetTimer extends TimerTask {
//
//        private AppWidgetManager mAppWidgetManager;
//        private RemoteViews mRemoteViews;
//        private ComponentName mThisWidget;
//        private int[] mAppWidgetIds;
//        private Context mContext;
//
//        public WidgetTimer(Context context, AppWidgetManager appWidgetManager, int[]  appWidgetIds) {
//
//            this.mAppWidgetManager = appWidgetManager;
//            this.mThisWidget = new ComponentName(context, TelegramMessageWidget.class);
//            this.mAppWidgetIds = appWidgetIds;
//            this.mContext = context;
//            this.mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.telegram_message_widget);
//        }
//
//
//        @SuppressLint("NewApi")
//        @Override
//        public void run() {
//            //int N = mAppWidgetIds.length;
//            Log.d("Widget", "timer run is called");
//            //for (int i = 0; i < N; i++) {
//
//                mAppWidgetManager.notifyAppWidgetViewDataChanged(mAppWidgetIds[0], R.id.widgetListView);
//                //updateAppWidget(mContext, mAppWidgetManager, mAppWidgetIds[i]);
//        //    }
//        }
//    }

//    @Override
//    public void onDeleted(Context context, int[] appWidgetIds) {
//        super.onDeleted(context, appWidgetIds);
//        //Log.d("Widget", "timer cancellled");
//        //timer.cancel();
//    }

    @SuppressLint("NewApi")
    public static void updateWidget(Context context, AppWidgetManager appWidgetManager) {
       int[] mAppWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, TelegramMessageWidget.class));
        for (int i = 0; i < mAppWidgetIds.length; i++) {
            appWidgetManager.notifyAppWidgetViewDataChanged(mAppWidgetIds[i], R.id.widgetListView);
        }

    }
}

