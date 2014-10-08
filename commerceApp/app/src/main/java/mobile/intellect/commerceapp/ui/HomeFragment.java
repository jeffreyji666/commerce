package mobile.intellect.commerceapp.ui;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import mobile.intellect.commerceapp.R;
import mobile.intellect.commerceapp.model.Comment;
import mobile.intellect.commerceapp.model.CommentModel;
import mobile.intellect.commerceapp.view.CommentAdapter;
import mobile.intellect.commerceapp.view.LoadListView;

/**
 * Created by you on 10/3/14.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.home, container, false);
        return mView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
