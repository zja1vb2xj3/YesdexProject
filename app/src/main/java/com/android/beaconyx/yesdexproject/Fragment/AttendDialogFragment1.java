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

public class AttendDialogFragment1 extends DialogFragment {
    private Context context;
    private int mLayoutWidth;
    private int mLayoutHeight;

    public static AttendDialogFragment1 newInstance() {
        AttendDialogFragment1 dialogFragment1 = new AttendDialogFragment1();

        return dialogFragment1;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.fragment1_attend_dialog, null, false);
        builder.setView(dialogView);

        Point point = OnMeasureDisplay.onMeasure();

        double minWidth = point.x / 1.2;
        double minHeight = point.y / 1.2;

        LinearLayout layout = (LinearLayout) dialogView.findViewById(R.id.beacon_reaction_popup);

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


    public AttendDialogFragment1() {

    }

    OnMeasureDisplay OnMeasureDisplay;

    public void setOnMeasureDisplay(OnMeasureDisplay onMeasureDisplay) {
        this.OnMeasureDisplay = onMeasureDisplay;
    }

    public interface OnMeasureDisplay{
        Point onMeasure();
    }


}
