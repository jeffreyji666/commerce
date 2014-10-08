package mobile.intellect.commerceapp.model;

public class Comment {
    private Long id = 0L;
    private String nickName;
    private Long commodityId;
    private String comment;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getNickName() {
	return nickName;
    }

    public void setNickName(String nickName) {
	this.nickName = nickName;
    }

    public Long getCommodityId() {
	return commodityId;
    }

    public void setCommodityId(Long commodityId) {
	this.commodityId = commodityId;
    }

    public String getComment() {
	return comment;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }

}
