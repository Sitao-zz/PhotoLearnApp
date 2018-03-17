package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.pt5.photolearnapp.R;

/**
 * Created by akeelan on 3/17/2018.
 */

public class QuizTitles extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.quiz_title, container, false);
        return rootView;
    }
}
