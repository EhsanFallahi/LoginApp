/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ehsanfallahi.loginapp.util.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.ehsanfallahi.loginapp.R
import com.ehsanfallahi.loginapp.ui.MainActivity


private const val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

    val contentIntent= Intent(applicationContext, MainActivity::class.java)

    val contentPendingIntent=PendingIntent.getActivity(
            applicationContext,
            REQUEST_CODE,
            contentIntent,
              PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder=NotificationCompat.Builder(applicationContext,
            applicationContext.getString(R.string.notification_channel_id))
            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("LoginApp Notification")
            .setContentText(messageBody)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
    notify(NOTIFICATION_ID,builder.build())
}

fun NotificationManager.cancellNotification(){
    cancelAll()
}
