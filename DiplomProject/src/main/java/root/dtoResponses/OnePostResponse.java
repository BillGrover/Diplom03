package root.dtoResponses;

import com.fasterxml.jackson.annotation.JsonProperty;
import root.dto.CommentDto;
import root.dto.UserDto;
import root.model.Post;

import java.util.List;
import java.util.stream.Collectors;

public class OnePostResponse {
    private long id;
    private long timestamp;
    private boolean active;
    private UserDto user;
    private String title;
    private String text;
    @JsonProperty("likeCount")
    private long likeCount;
    @JsonProperty("dislikeCount")
    private long dislikeCount;
    @JsonProperty("viewCount")
    private int viewCount;
    private List<CommentDto> comments;
    private List<String> tags;

    public OnePostResponse(Post post) {
        this.id = post.getId();
        this.timestamp = post.getTime().getTime()/1000;
        this.active = post.getIsActive() == 1;
        this.user = new UserDto(post.getUser());
        this.title = post.getTitle();
        this.text = post.getText();
        this.likeCount = post.getVotes().stream().filter(v -> v.getValue() == 1).count();
        this.dislikeCount = post.getVotes().stream().filter(v -> v.getValue() == -1).count();
        this.viewCount = post.getViewCount();
        this.comments = post.getComments().stream().map(CommentDto::new).collect(Collectors.toList());
        this.tags = post.getTag2Post().stream().map((tag2Post) -> tag2Post.getTag().getTitle()).collect(Collectors.toList());
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(long dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
