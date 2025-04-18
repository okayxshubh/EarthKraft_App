package com.dit.hp.hospitalapp.Presentation;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dit.hp.hospitalapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CustomBottomSheet {

    public void showBottomSheet(final Activity activity, String message) {
        if (activity != null) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
            View view = activity.getLayoutInflater().inflate(R.layout.dialog_bottom_sheet, null);
            bottomSheetDialog.setContentView(view);

            // ✅ Make background transparent
            if (bottomSheetDialog.getWindow() != null) {
                bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            FrameLayout bottomSheet = bottomSheetDialog.findViewById(R.layout.dialog_bottom_sheet);
            if (bottomSheet != null) {
                bottomSheet.setBackgroundResource(R.drawable.round_corner_roundtop_only);
            }

            TextView textView = view.findViewById(R.id.bottom_sheet_text);
            textView.setText(message);

            bottomSheetDialog.show();
        }
    }


}
