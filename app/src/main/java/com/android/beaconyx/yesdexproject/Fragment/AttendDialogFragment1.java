package com.android.beaconyx.yesdexproject.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.beaconyx.yesdexproject.Application.ThisApplication;
import com.android.beaconyx.yesdexproject.R;

/**
 * Created by beaconyx on 2017-10-12.
 */

public class AttendDialogFragment1 extends DialogFragment {
    private static AttendDialogFragment1 dialogFragment1;
    private Point point = new Point();
    private ThisApplication mThisApplication;
    private AlertDialog.Builder builder;

    public static AttendDialogFragment1 newInstance() {
        dialogFragment1 = new AttendDialogFragment1();

        return dialogFragment1;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mThisApplication = (ThisApplication) getActivity().getApplicationContext();

        builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.fragment1_attend_dialog, null, false);
        builder.setView(dialogView);

        builder.setOnKeyListener(onKeyListener);

        point = mThisApplication.getDisplaySize();

        double minWidth = point.x / 1.2;
        double minHeight = point.y / 1.2;

        LinearLayout layout = (LinearLayout) dialogView.findViewById(R.id.beacon_reaction_popup);

        layout.setMinimumWidth((int) minWidth);
        layout.setMinimumHeight((int) minHeight);

        Button inButton = (Button) dialogView.findViewById(R.id.in_button);
        Button outButton = (Button) dialogView.findViewById(R.id.out_button);

        inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mThisApplication.setFragmentDialog1Sign(false);
                onDialog1CancelListener.onCancel(getDialog());
                callDialogFragment2();
            }
        });

        outButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDialog1CancelListener.onCancel(getDialog());
            }
        });

//        builder.setOnKeyListener(onKeyListener);

        return builder.create();
    }

    DialogInterface.OnKeyListener onKeyListener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
            dialogFragment1.onDialog1CancelListener.onCancel(getDialog());
            mThisApplication.setFragmentDialog1Sign(true);

            return true;
        }
    };


    private AttendDialogFragment2 mDialogFragment2 = AttendDialogFragment2.newInstance();

    private void callDialogFragment2() {

        mDialogFragment2.setPoint(point);
        mDialogFragment2.show(getFragmentManager(), "fragment2");

        mDialogFragment2.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialogInterface.cancel();
            }
        });
    }

    private DialogInterface.OnCancelListener onDialog1CancelListener;


    public void setOnDialogFragment1CancelListener(DialogInterface.OnCancelListener onCancelListener) {
        this.onDialog1CancelListener = onCancelListener;
    }



    public AttendDialogFragment1() {

    }


}
