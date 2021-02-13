package root.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import root.dto.TagDto;
import root.dtoResponses.TagResponse;
import root.model.Tag;
import root.repositories.PostRepository;
import root.repositories.TagRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagService {
    private final TagRepository tagRepo;
    private final PostRepository postRepo;
    private double maxWeight;

    public TagService(TagRepository tagRepo, PostRepository postRepo) {
        this.tagRepo = tagRepo;
        this.postRepo = postRepo;
        maxWeight = 0;
    }

    //Подсчёт абсолютного веса каждого тега
    private Double weight(String title) {
        int postTotalAmount = postRepo.amountTotal();
        int postWithTheTagAmount = postRepo.amountWithTag(title);
        double weight = (double) postWithTheTagAmount / postTotalAmount;
        if (weight > maxWeight)
            maxWeight = weight;
        return weight;
    }

    public ResponseEntity<TagResponse> getTags(String query) {

        TagResponse response;

        List<Tag> tagList;
        if (query == null)
            tagList = tagRepo.findAll();
        else
            tagList = tagRepo.findAllByTitleStartingWith(query);

        if (tagList.isEmpty()){
            return new ResponseEntity<>(new TagResponse(new ArrayList<>()), HttpStatus.OK);
        }

        HashMap<String, Double> tagWithWeight = new HashMap<>();
        tagList.forEach(t -> tagWithWeight.put(t.getTitle(), weight(t.getTitle())));

        List<TagDto> tags = new ArrayList<>();
        for (Map.Entry<String, Double> entry : tagWithWeight.entrySet()) {
            tags.add(
                    new TagDto(
                            entry.getKey(),
                            entry.getValue() / maxWeight
                    ));
        }

        return new ResponseEntity<>(new TagResponse(tags), HttpStatus.OK);
    }
}
