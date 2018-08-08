package com.example.ptut.mm_kunyi.utils

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.app.TaskStackBuilder
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.activities.JobListActivity
import net.aungpyaephyo.mmtextview.MMFontUtils
import java.util.concurrent.ExecutionException
import android.graphics.drawable.BitmapDrawable

class NotificationUtils {
    companion object {
        private var INSTANCE:NotificationUtils?=null
        private const val NOTIFICATION_ID_NEW_MESSAGE = 2001
        const val KEY_MESSAGE = "custom_msg"
        private const val REQUEST_ID_SAVE_NEWS = 3001
        fun getInstance():NotificationUtils{
            if(INSTANCE==null){
                INSTANCE= NotificationUtils()
            }
            val i = INSTANCE
            return i!!
        }
    }
    fun notifyCustomMsg(context: Context, message: String) {
        val title = context.getString(R.string.app_name)
        val mmMessage = MMFontUtils.mmTextUnicodeOrigin(message)
        val appIcon = encodeResourceToBitmap(context, R.drawable.ic_google)
        //Message in BigText Style
        val bigTextStyle = NotificationCompat.BigTextStyle()
        bigTextStyle.bigText(mmMessage)
        val builder = NotificationCompat.Builder(context)
                .setColor(context.resources.getColor(R.color.white_transparent))
                .setSmallIcon(R.drawable.ic_google)
                .setLargeIcon(appIcon)
                .setContentTitle(title)
                .setContentText(mmMessage)
                .setAutoCancel(true)
                .setStyle(bigTextStyle)
        //Notification action to Play Songs by Artist.
        saveNewsAction(context, NOTIFICATION_ID_NEW_MESSAGE, builder)
        //Open the app when user tap on notification
        val resultIntent = JobListActivity.newIntentNotiBody(context)
        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(resultPendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NOTIFICATION_ID_NEW_MESSAGE, builder.build())
    }

    private fun encodeResourceToBitmap(context: Context, resourceId: Int): Bitmap? {
        var bitmap: Bitmap? = null
        //Encode bitmap for large notification icon
        val largeIconWidth = context.resources.getDimensionPixelSize(android.R.dimen.notification_large_icon_width)
        val largeIconHeight = context.resources.getDimensionPixelSize(android.R.dimen.notification_large_icon_height)
        try {
            var d = Glide.with(context)
                    .load(resourceId)
                    .into(largeIconWidth, largeIconHeight)
                    .get()
            bitmap = (d as BitmapDrawable).bitmap
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }

        return bitmap
    }

    private fun saveNewsAction(context: Context, notificationId: Int, builder: NotificationCompat.Builder) {
        //Intent to execute when user tap on Action Button.
        val saveNewsActionIntent = JobListActivity.newIntentSaveNews(context, notificationId)
        val playSongsByArtistActionPendingIntent = PendingIntent.getActivity(context, Companion.REQUEST_ID_SAVE_NEWS, saveNewsActionIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        //"Save News" Action Label.
        val notiActionSaveNews = "JobNews"

        //Action Button Icon.
        val actionIcon = R.drawable.ic_bookmark
        if (TextUtils.equals(Build.BRAND, AppConstants.VENDOR_XIAOMI)) {
            //actionIcon = R.drawable.ic_other_bookmark_border_24dp;
        }

        val playSongsByArtistAction = NotificationCompat.Action(actionIcon,
                notiActionSaveNews, playSongsByArtistActionPendingIntent)
        builder.addAction(playSongsByArtistAction)
    }

}
