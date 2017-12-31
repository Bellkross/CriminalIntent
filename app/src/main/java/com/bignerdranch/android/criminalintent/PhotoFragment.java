package com.bignerdranch.android.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class PhotoFragment extends DialogFragment {

    public static final String EXTRA_PHOTO =
            "com.bignerdranch.android.criminalintent.photo";

    private static final String ARG_PHOTO = "photo";

    private ImageView mPhoto;

    public static PhotoFragment newInstance(Uri address) {
        Bundle args = new Bundle();
        args.putString(ARG_PHOTO, address.toString());

        PhotoFragment photoFragment = new PhotoFragment();
        photoFragment.setArguments(args);
        return photoFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Uri address = Uri.parse(getArguments().getString(ARG_PHOTO));

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_photo, null);
        mPhoto = (ImageView) v.findViewById(R.id.ivPhoto);

        mPhoto.setImageURI(address);

        return new AlertDialog.Builder(getActivity()).setView(v).create();
    }
}
