package root.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONObject;
import root.model.ModerationStatus;
import root.model.Posts;
import root.repos.PostRepository;
import root.services.PostService;

import java.util.Date;

@Controller
@RequestMapping("/api/post")
public class ApiPostController {

    private PostRepository postRepo;

    public ApiPostController(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @GetMapping
    @ResponseBody
    public JSONObject post(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam String mode){

        Pageable pagingation = PageRequest.of(offset, limit);
        Page<Posts> filteredSortedPosts;

        switch (mode){
            case ("recent"):    //сначала новые
                filteredSortedPosts = postRepo.findRecent(pagingation);
                break;
            case ("popular"):   //по убыванию комментариев (comments.size)
                filteredSortedPosts = postRepo.findPopular(pagingation);
                break;
            case ("best"):      //по убыванию лайков (сумма значений листа votes)
                filteredSortedPosts = postRepo.findBest(pagingation);
                break;
            case ("early"):     //сначала старые
                filteredSortedPosts = postRepo.findEarly(pagingation);
                break;
            default:            //по id
                filteredSortedPosts = postRepo.findAllByIsActiveAndModerationStatusAndTimeBeforeOrderById(
                        (byte) 1, ModerationStatus.ACCEPTED, new Date(), pagingation);
        }
        return PostService.packToJson(filteredSortedPosts);
    }

    @PostMapping("/like")
    public void setLike(){
//        TODO: Ограничить приём лайков ТОЛЬКО как 1
    }

    @PostMapping("/like")
    public void setDislike(){
//        TODO: Ограничить приём дизлайков ТОЛЬКО как -1
    }
}
