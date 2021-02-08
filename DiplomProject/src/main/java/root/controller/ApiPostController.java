package root.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import root.dtoResponses.PostResponse;
import root.services.PostService;

@Controller
@RequestMapping("/api/post")
public class ApiPostController {

    private PostService postService;

    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(defaultValue = "0") String offset,
            @RequestParam(defaultValue = "10") String limit,
            @RequestParam String mode){
        return postService.getPosts(offset, limit, mode);
    }

    @GetMapping("/search")
    public ResponseEntity<PostResponse> getPostsByQuery(
            @RequestParam(defaultValue = "0") String offset,
            @RequestParam(defaultValue = "10") String limit,
            @RequestParam String query){
        return postService.getByQuery(offset, limit, query);
    }

    @GetMapping("/byDate")
    @ResponseBody
    public ResponseEntity<PostResponse> getPostsByDate(
            @RequestParam(defaultValue = "0") String offset,
            @RequestParam(defaultValue = "10") String limit,
            @RequestParam (defaultValue = "1970-01-01")String date) {
        return postService.getByDate(offset, limit, date);
    }

    @GetMapping("/byTag")
    @ResponseBody
    public ResponseEntity<PostResponse> byTag(
            @RequestParam(defaultValue = "0") String offset,
            @RequestParam(defaultValue = "10") String limit,
            @RequestParam String tag) {
        return postService.getByTag(offset, limit, tag);
    }

    @GetMapping("/moderation")
    @ResponseBody
    public ResponseEntity<PostResponse> moderation(
            @RequestParam(defaultValue = "0") String offset,
            @RequestParam(defaultValue = "10") String limit,
            @RequestParam String status) {
        return postService.getModeration(offset, limit, status);
    }


    @PostMapping("/like")
    public void setLike(){
//        TODO: Ограничить приём лайков ТОЛЬКО как 1
    }

    @PostMapping("/dislike")
    public void setDislike(){
//        TODO: Ограничить приём дизлайков ТОЛЬКО как -1
    }
}
