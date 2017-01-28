package com.example.goodbox.capstone_stage2.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.goodbox.capstone_stage2.R;
import com.example.goodbox.capstone_stage2.ui.QuizActivity;

/**
 * Created by Goodbox on 28-01-2017.
 */

public class QuizWidgetProvider extends AppWidgetProvider {

    public static final String INTENT_ACTION = "com.example.goodbox.capstone_stage2.widget.QuizWidgetProvider.INTENT_ACTION";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            /**Intent intent = new Intent(context, QuizWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));**/
            Intent mIntent = new Intent(context, QuizActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_quiz);
            //rv.setRemoteAdapter(R.id.quiz_widget,intent);
            //rv.setEmptyView(R.id.quiz_widget,R.id.empty_widget);
            rv.setTextViewText(R.id.wQuestion,"When was the movie Kuch Kuch Hota Hai released?");


            //mIntent.setAction(QuizWidgetProvider.INTENT_ACTION);
            //mIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            //mIntent.setData(Uri.parse(mIntent.toUri(Intent.URI_INTENT_SCHEME)));

            rv.setOnClickPendingIntent(R.id.quiz_widget, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
