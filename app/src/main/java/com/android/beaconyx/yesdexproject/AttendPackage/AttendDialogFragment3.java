package com.android.beaconyx.yesdexproject.AttendPackage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.PDFPackage.PDFViewerActivity;
import com.android.beaconyx.yesdexproject.R;

/**
 * Created by beaconyx on 2017-10-31.
 */

public class AttendDialogFragment3 extends DialogFragment {
    private static AttendDialogFragment3 dialogFragment3;
    private Point point = new Point();
    private ThisApplication mThisApplication;
    private AlertDialog.Builder builder;

    private String time;
    private String location;
    private String name;
    private int image;
    private String profile;

    public static AttendDialogFragment3 newInstance(String time, String location, String name, int image, String profile) {
        dialogFragment3 = new AttendDialogFragment3(time, location, name, image, profile);

        return dialogFragment3;
    }

    public AttendDialogFragment3(String time, String location, String name, int image, String profile) {
        this.time = time;
        this.location = location;
        this.name = name;
        this.image = image;
        this.profile = profile;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mThisApplication = (ThisApplication) getActivity().getApplicationContext();

        builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.fragment3_attend_dialog, null, false);

        builder.setView(dialogView);

        builder.setOnKeyListener(onKeyListener);

        point = mThisApplication.getDisplaySize();

        double minWidth = point.x / 1.2;
        double minHeight = point.y / 1.2;

        ImageView imageView = dialogView.findViewById(R.id.imageview);
        TextView textView1 = dialogView.findViewById(R.id.time);
        TextView textView2 = dialogView.findViewById(R.id.position);
        TextView textView3 = dialogView.findViewById(R.id.name);
        TextView textView4 = dialogView.findViewById(R.id.profile);

        imageView.setImageResource(image);
        textView1.setText(time);
        textView2.setText(location);
        textView3.setText(name);
        textView4.setText(profile);

        ImageButton closeButton  = dialogView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment3.onDialogCancelListener.onCancel(getDialog());
            }
        });


        ImageButton imageButton = dialogView.findViewById(R.id.pdf_viewer_button);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getContext(), PDFViewerActivity.class);
                startActivity(intent);

                dialogFragment3.onDialogCancelListener.onCancel(getDialog());
            }
        });

        FrameLayout layout = dialogView.findViewById(R.id.sample_popup);

        layout.setMinimumWidth((int) minWidth);
        layout.setMinimumHeight((int) minHeight);

        return builder.create();
    }


    DialogInterface.OnKeyListener onKeyListener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
            dialogFragment3.onDialogCancelListener.onCancel(getDialog());

            return true;
        }
    };


    private DialogInterface.OnCancelListener onDialogCancelListener;


    public void setOnDialogFragment3CancelListener(DialogInterface.OnCancelListener onCancelListener) {
        this.onDialogCancelListener = onCancelListener;
    }

}
