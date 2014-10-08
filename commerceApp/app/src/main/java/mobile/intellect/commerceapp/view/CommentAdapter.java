package mobile.intellect.commerceapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mobile.intellect.commerceapp.R;
import mobile.intellect.commerceapp.model.Comment;

public class CommentAdapter extends BaseAdapter {
    List<Comment> comments;
    LayoutInflater inflater;

    public CommentAdapter(Context context, List<Comment> comments) {
        this.comments = comments;
        this.inflater = LayoutInflater.from(context);
    }

    public void onDateChange(List<Comment> comments) {
        this.comments = comments;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment = comments.get(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.comment_layout, null);
            holder.nickName = (TextView) convertView
                    .findViewById(R.id.nickName);
            holder.comment = (TextView) convertView
                    .findViewById(R.id.comment);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nickName.setText(comment.getNickName());
        holder.comment.setText(comment.getComment());
        return convertView;
    }

    class ViewHolder {
        TextView nickName;
        TextView comment;
    }
}
