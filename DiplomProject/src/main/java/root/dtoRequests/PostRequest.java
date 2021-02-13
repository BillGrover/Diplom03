package root.dtoRequests;

import java.util.List;

public class PostRequest {
    private long timestamp;
    private byte active;
    private String title;
    private String text;
    private List<String> tags;

    public PostRequest(long timestamp, byte active, String title, String text, List<String> tags) {
        this.timestamp = timestamp;
        this.active = active;
        this.title = title;
        this.text = text;
        this.tags = tags;
    }

    public PostRequest() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public byte getActive() {
        return active;
    }

    public void setActive(byte active) {
        this.active = active;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
