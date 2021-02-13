package root.dtoResponses;

import root.dto.TagDto;

import java.util.List;

public class TagResponse {

    private List<TagDto> tags;

    public TagResponse(List<TagDto> tags) {
        this.tags = tags;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }
}
