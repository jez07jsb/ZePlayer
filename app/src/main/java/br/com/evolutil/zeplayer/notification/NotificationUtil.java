package br.com.evolutil.zeplayer.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Fornece metodos com utilidades para criar notificacoes.
 * <p style="font-family:Lucida Sans;font-style:italic;font-weight:bold;color:green;">
 *      Created by Jez on 24/09/2015.
 * </p>
 */
public class NotificationUtil {
    // Cria a PendingIntent para abrir a activity da intent
    private static PendingIntent getPendingIntent(Context context, Intent intent, int id) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Activity pai permanece na pilha de activities
        stackBuilder.addParentStack(intent.getComponent());
        // configura a intent que vai abrir ao clicar na notificacao
        stackBuilder.addNextIntent(intent);
        // cria a PendingIntent ou atualiza caso exista uma com o mesmo id
        PendingIntent p = stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);
        return p;
    }

    public static void create(Context context, Intent intent, int resourceIcon, String contentTitle, String contentText, int id) {
        // Cria a PendingIntent (Contem a intent original)
        PendingIntent p = getPendingIntent(context, intent, id);
        // Cria a notificacao
        NotificationCompat.Builder b = new NotificationCompat.Builder(context);
        b.setDefaults(Notification.DEFAULT_ALL); // Ativa configuracoes padroes
        b.setSmallIcon(resourceIcon); //Icone
        b.setContentTitle(contentTitle); // Titulo
        b.setContentText(contentText); // Mensagem
        b.setContentIntent(p); // Intent que sera chamada ao clicar na notificacao
        b.setAutoCancel(true); // Autocancela a notificacao ao clicar nela

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        // Neste caso a notificacao sera cancelada automaticamente quando o usuario clicar
        // Mas o id serve para cancela-la manualmente se necesario
        nm.notify(id, b.build());
    }



    public static void create(Context context, Intent intent, int resourceIcon, String contentTitle, String contentText, int id, boolean flag_is_default) {
        // Cria a PendingIntent (Contem a intent original)
        PendingIntent p = getPendingIntent(context, intent, id);
        // Cria a notificacao
        NotificationCompat.Builder b = new NotificationCompat.Builder(context);
        if (flag_is_default) {
            b.setDefaults(Notification.FLAG_NO_CLEAR); // Ativa configuracoes padroes
        }
        b.setSmallIcon(resourceIcon); //Icone
        b.setContentTitle(contentTitle); // Titulo
        b.setContentText(contentText); // Mensagem
        b.setContentIntent(p); // Intent que sera chamada ao clicar na notificacao
        b.setAutoCancel(true); // Autocancela a notificacao ao clicar nela

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        // Neste caso a notificacao sera cancelada automaticamente quando o usuario clicar
        // Mas o id serve para cancela-la manualmente se necesario
        nm.notify(id, b.build());
    }

    public static void create(Context context, Intent intent, int resourceIcon, String contentTitle, String contentText, String contentSubText, int id) {
        // Cria a PendingIntent (Contem a intent original)
        PendingIntent p = getPendingIntent(context, intent, id);
        // Cria a notificacao
        NotificationCompat.Builder b = new NotificationCompat.Builder(context);
        b.setDefaults(Notification.DEFAULT_ALL); // Ativa configuracoes padroes
        b.setSmallIcon(resourceIcon); //Icone
        b.setContentTitle(contentTitle); // Titulo
        b.setContentText(contentText); // Mensagem
        b.setSubText(contentSubText); // Sub mensagem
        b.setContentIntent(p); // Intent que sera chamada ao clicar na notificacao
        b.setAutoCancel(true); // Autocancela a notificacao ao clicar nela

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        // Neste caso a notificacao sera cancelada automaticamente quando o usuario clicar
        // Mas o id serve para cancela-la manualmente se necesario
        nm.notify(id, b.build());
    }

    public static void create(Context context, int resourceIcon, String contentTitle, String contentText, String contentSubText, int id) {
        // Cria a notificacao
        NotificationCompat.Builder b = new NotificationCompat.Builder(context);
        b.setDefaults(Notification.DEFAULT_ALL); // Ativa configuracoes padroes
        b.setSmallIcon(resourceIcon); //Icone
        b.setContentTitle(contentTitle); // Titulo
        b.setContentText(contentText); // Mensagem
        b.setSubText(contentSubText);
        b.setAutoCancel(true); // Autocancela a notificacao ao clicar nela

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        // Neste caso a notificacao sera cancelada automaticamente quando o usuario clicar
        // Mas o id serve para cancela-la manualmente se necesario
        nm.notify(id, b.build());
    }

    public static void cancel(Context context, int id) {
        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.cancel(id);
    }

    public  static void cancelAll(Context context) {
        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.cancelAll();
    }
}