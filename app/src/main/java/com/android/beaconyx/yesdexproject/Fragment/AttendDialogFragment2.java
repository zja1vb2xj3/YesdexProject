package com.android.beaconyx.yesdexproject.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.android.beaconyx.yesdexproject.Activity.PDFViewerActivity;
import com.android.beaconyx.yesdexproject.R;

/**
 * Created by beaconyx on 2017-10-12.
 */

public class AttendDialogFragment2 extends DialogFragment {

    private Context context;
    private Point point;

    public static AttendDialogFragment2 newInstance() {
        AttendDialogFragment2 dialogFragment2 = new AttendDialogFragment2();

        return dialogFragment2;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public AttendDialogFragment2() {
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getActivity();

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.fragment2_attend_dialog, null, false);
        builder.setView(dialogView);

        double minWidth = point.x / 1.2;
        double minHeight = point.y / 1.2;

        LinearLayout layout = (LinearLayout) dialogView.findViewById(R.id.beacon_reaction_popup2);

        layout.setMinimumWidth((int) minWidth);
        layout.setMinimumHeight((int) minHeight);

        ImageButton pdfViewerButton = (ImageButton) dialogView.findViewById(R.id.pdf_viewer_button);

        pdfViewerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDialogCancelListener.onCancel(getDialog());

                Intent intent = new Intent(getActivity(), PDFViewerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }
        });

        return builder.create();
    }

    private DialogInterface.OnCancelListener onDialogCancelListener;


    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        this.onDialogCancelListener = onCancelListener;
    }

}
