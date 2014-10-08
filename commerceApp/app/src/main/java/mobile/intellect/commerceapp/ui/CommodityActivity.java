package mobile.intellect.commerceapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import mobile.intellect.commerceapp.R;
import mobile.intellect.commerceapp.model.Comment;
import mobile.intellect.commerceapp.model.CommentModel;
import mobile.intellect.commerceapp.view.CommentAdapter;
import mobile.intellect.commerceapp.view.LoadListView;

/**
 * Created by you on 10/5/14.
 */
public class CommodityActivity extends Activity implements LoadListView.ILoadListener {
    private List<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commodity_layout);
        comments = CommentModel.getComments();
        showListView(comments);
    }

    CommentAdapter adapter;
    LoadListView listview;

    private void showListView(List<Comment> comments) {
        if (adapter == null) {
            listview = (LoadListView) findViewById(R.id.listview);
            listview.setInterface(this);
            adapter = new CommentAdapter(this, comments);
            listview.setAdapter(adapter);
        } else {
            adapter.onDateChange(comments);
        }
    }

    @Override
    public void onLoad() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showListView(comments);
                listview.loadComplete();
            }
        }, 2000);
    }
}
