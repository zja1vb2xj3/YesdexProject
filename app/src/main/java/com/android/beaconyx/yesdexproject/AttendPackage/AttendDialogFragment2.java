package com.android.beaconyx.yesdexproject.AttendPackage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.beaconyx.yesdexproject.PDFPackage.PdfViewerActivity;
import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.R;

/**
 * Created by beaconyx on 2017-10-12.
 */

public class AttendDialogFragment2 extends DialogFragment {

    private static AttendDialogFragment2 dialogFragment2;
    private Context context;
    private Point point = new Point();
    private ThisApplication mThisApplication;

    public static AttendDialogFragment2 newInstance() {
        dialogFragment2 = new AttendDialogFragment2();

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

        mThisApplication = (ThisApplication) getActivity().getApplicationContext();

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
                onDialog2CancelListener.onCancel(getDialog());

                Intent intent = new Intent(getActivity(), PdfViewerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }
        });

        builder.setOnKeyListener(onKeyListener);

        return builder.create();
    }//end onCreateDialog

    DialogInterface.OnKeyListener onKeyListener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
            Toast.makeText(getActivity(), "back버튼 클릭", Toast.LENGTH_SHORT).show();
            dialogFragment2.onDialog2CancelListener.onCancel(getDialog());
            mThisApplication.setFragmentDialog1Sign(true);

            return true;
        }
    };

    private DialogInterface.OnCancelListener onDialog2CancelListener;


    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        this.onDialog2CancelListener = onCancelListener;
    }

}
