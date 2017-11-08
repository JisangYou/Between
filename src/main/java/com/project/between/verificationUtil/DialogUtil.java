package com.project.between.verificationUtil;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by JisangYou on 2017-11-03.
 */

public class DialogUtil {

    public static void showDialog(String msg, final Activity activity, final boolean activityFinish){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setTitle("Notice");
        dialogBuilder.setMessage(msg);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if(activityFinish)
                    activity.finish(); // 이게 무슨 뜻일까?
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}