package root.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jsoup.Jsoup;
import root.dto.UserDto;
import root.model.Post;

public class PostDto {
    private int id;
    private long timestamp;
    private UserDto user;
    private String title;
    private String announce;
    @JsonProperty("likeCount")
    private long likeCount;
    @JsonProperty("dislikeCount")
    private long dislikeCount;
    @JsonProperty("commentCount")
    private int commentCount;
    @JsonProperty("viewCount")
    private int viewCount;
    @JsonIgnore
    private String preAnnounce;
    private final int ANNOUNCE_LENGTH = 300;

    public PostDto(int id, long timestamp, UserDto user, String title,
                   String announce, int likeCount, int dislikeCount, int commentCount, int viewCount) {
        this.id = id;
        this.timestamp = timestamp;
        this.user = user;
        this.title = title;
        this.announce = announce;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
    }

    public PostDto() {
    }

    public PostDto(Post post) {
        this.id = post.getId();
        this.timestamp = post.getTime().getTime()/1000;
        this.user = new UserDto(post.getUser());
        this.title = post.getTitle();
        this.likeCount = post.getVotes().stream().filter(v -> v.getValue() == 1).count();
        this.dislikeCount = post.getVotes().stream().filter(v -> v.getValue() == -1).count();
        this.commentCount = post.getComments().size();
        this.viewCount = post.getViewCount();
        this.preAnnounce = Jsoup.parse(post.getText()).text();
        this.announce = preAnnounce.length() < ANNOUNCE_LENGTH+1 ? preAnnounce : preAnnounce.substring(0, ANNOUNCE_LENGTH) + "...";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public long getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
