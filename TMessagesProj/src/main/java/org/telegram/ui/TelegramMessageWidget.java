package org.telegram.ui;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.R;
import org.telegram.messenger.WidgetService;

/**
 * Implementation of App Widget functionality.
 */
public class TelegramMessageWidget extends AppWidgetProvider implements NotificationsController.WidgetUpdateDelegate {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        if (NotificationsController.getInstance().widgetUpdateDelegate == null) {
            NotificationsController.getInstance().setUpdateWidgetDelegate(context,this, appWidgetIds);
        }
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
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

        views.setRemoteAdapter(R.id.widgetListView, svcIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void updateWidget(Context context, int[] appWidgetIds) {
        this.onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);
    }
}

