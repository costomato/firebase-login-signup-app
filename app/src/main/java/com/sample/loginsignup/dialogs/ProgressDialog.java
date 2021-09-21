package com.sample.loginsignup.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;
import androidx.appcompat.content.res.AppCompatResources;
import com.sample.loginsignup.R;

public class ProgressDialog {
    private final Dialog dialog;

    public ProgressDialog(Context context) {
        dialog = new Dialog(context);
        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setIndeterminateTintList(AppCompatResources.getColorStateList(context, R.color.primaryBlue));
        dialog.setContentView(progressBar);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_progress);
        dialog.setCancelable(false);
    }

    public void showDialog() {
        dialog.show();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }
}