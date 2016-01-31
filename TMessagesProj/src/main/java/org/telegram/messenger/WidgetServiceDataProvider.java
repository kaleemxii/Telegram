package org.telegram.messenger;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import org.telegram.tgnet.TLRPC;
import org.telegram.ui.LaunchActivity;

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
        RemoteViews remoteView = new RemoteViews(packageName, R.layout.widge_list_item);
        TLRPC.Dialog currentDialog = getDialogs().get(position);
        int lowerId = (int)currentDialog.id;
        Intent fillIntent = new Intent();
        fillIntent.setAction("com.tmessages.openchat" + Math.random() + Integer.MAX_VALUE);
        fillIntent.setFlags(32768);

        if (lowerId < 0) {
            TLRPC.Chat chat = MessagesController.getInstance().getChat(-lowerId);
            String chatName = chat.title;
            remoteView.setTextViewText(android.R.id.text1, chatName);
            MessageObject messageObject = MessagesController.getInstance().dialogMessage.get(-lowerId);
            //int chat_id = messageObject.messageOwner.to_id.chat_id != 0 ? messageObject.messageOwner.to_id.chat_id : messageObject.messageOwner.to_id.channel_id;
            fillIntent.putExtra("chatId", chat.id);
        } else {
            TLRPC.User user = MessagesController.getInstance().getUser(lowerId);
            String userName = UserObject.getUserName(user);
            remoteView.setTextViewText(android.R.id.text1, userName);
            int user_id = user.id;
            fillIntent.putExtra("userId", user_id);
        }

        String message = MessagesController.getInstance().dialogMessage.get(currentDialog.id).messageText.toString();


        remoteView.setTextViewText(android.R.id.text2, message);
        //Toast.makeText(context, "why the hell this is not getting called"+position, Toast.LENGTH_SHORT).show();
        Log.d("WidgetUpdate", "times this is called " + position);

       remoteView.setOnClickFillInIntent(android.R.id.text2, fillIntent);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        RemoteViews remoteViews = new RemoteViews(this.context.getPackageName(), android.R.layout.simple_list_item_1);
        remoteViews.setTextViewText(android.R.id.text1, "");
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
