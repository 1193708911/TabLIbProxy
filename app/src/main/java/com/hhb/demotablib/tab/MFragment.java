package com.hhb.demotablib.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hhb.demotablib.R;

public class MFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView mRootView = (TextView) inflater.inflate(R.layout.layout_activity_item, container, false);
        mRootView.setText(getArguments().getString("key"));
        return mRootView;
    }
}
