package mobile.intellect.commerceapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by you on 10/3/14.
 */
public class CommentModel {
    public static List<Comment> getComments() {
        List<Comment> comments = new ArrayList<Comment>();
        for (int i = 0; i < 100; i++) {
            Comment comment = new Comment();
            comment.setComment("test" + i);
            comment.setId(Integer.valueOf(i).longValue());
            comment.setCommodityId(1L);
            comment.setNickName("name" + i);
            comments.add(comment);
        }
        return comments;
    }

}
