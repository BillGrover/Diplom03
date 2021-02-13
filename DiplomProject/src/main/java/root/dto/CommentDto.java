package root.dto;

import root.model.PostComment;

public class CommentDto {
    private long id;
    private long timestamp;
    private String text;
    private UserDto user;

    public CommentDto(PostComment comment) {
        this.id = comment.getId();
        this.timestamp = comment.getTime().getTime()/1000;
        this.text = comment.getText();
        this.user = new UserDto(comment.getUser());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
