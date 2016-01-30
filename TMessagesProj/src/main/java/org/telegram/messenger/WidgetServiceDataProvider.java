package org.telegram.messenger;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.telegram.tgnet.TLRPC;

import java.util.ArrayList;

/**
 * Created by lebysani on 30-Jan-16.
 */
@SuppressLint("NewApi")
public class WidgetServiceDataProvider implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<String> arrayList = new ArrayList<String>();
    private Context context = null;
    private int appWidgetId;

    public WidgetServiceDataProvider(Context applicationContext, Intent intent) {
        this.context = applicationContext;
        this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    public ArrayList<TLRPC.Dialog> getDialogs() {
        return MessagesController.getInstance().dialogs;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return getDialogs().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        String packageName = context.getPackageName();
        RemoteViews remoteView = new RemoteViews(packageName, android.R.layout.two_line_list_item);
        TLRPC.Dialog currentDialog = getDialogs().get(position);
        TLRPC.User user = MessagesController.getInstance().getUser((int)currentDialog.id);
        String userName = UserObject.getUserName(user);

        String message = MessagesController.getInstance().dialogMessage.get(currentDialog.id).messageText.toString();

        remoteView.setTextViewText(android.R.id.text1, userName);
        remoteView.setTextViewText(android.R.id.text2, message);
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        RemoteViews remoteViews = new RemoteViews(this.context.getPackageName(), android.R.layout.simple_list_item_1);
        remoteViews.setTextViewText(android.R.id.text1, "LLLLLL");
        return remoteViews;
    }


    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
