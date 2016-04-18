package com.ogaga.flash.acitivies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaga.flash.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class TimeLineActivityFragment extends Fragment {

    public TimeLineActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time_line, container, false);
    }
}
