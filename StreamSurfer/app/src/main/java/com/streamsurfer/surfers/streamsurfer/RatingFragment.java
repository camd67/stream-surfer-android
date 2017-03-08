package com.streamsurfer.surfers.streamsurfer;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

public class RatingFragment extends DialogFragment {


    public RatingFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_rating, null);
        RatingBar bar = (RatingBar)view.findViewById(R.id.dialog_rating_bar);
        bar.setNumStars(ListEntry.MAX_RATING);
        builder.setView(view);
        return builder.create();
    }
}
