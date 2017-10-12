package com.android.beaconyx.yesdexproject.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.beaconyx.yesdexproject.R;

/**
 * Created by beaconyx on 2017-10-12.
 */

public class AttendDialogFragment2 extends DialogFragment {

    private Context context;
    private Point point;

    public static AttendDialogFragment2 newInstance(Point point) {
        AttendDialogFragment2 dialogFragment2 = new AttendDialogFragment2(point);

        return dialogFragment2;
    }

    public AttendDialogFragment2(Point point) {
        this.point = point;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.fragment2_attend_dialog, null, false);
        builder.setView(dialogView);

        double minWidth = point.x / 1.2;
        double minHeight = point.y / 1.2;

        LinearLayout layout = (LinearLayout) dialogView.findViewById(R.id.beacon_reaction_popup2);

        layout.setMinimumWidth((int) minWidth);
        layout.setMinimumHeight((int) minHeight);

        ImageButton inButton = (ImageButton) dialogView.findViewById(R.id.in_button);
        ImageButton outButton = (ImageButton) dialogView.findViewById(R.id.out_button);

        inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "In", Toast.LENGTH_SHORT).show();
            }
        });

        outButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Out", Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }


}
