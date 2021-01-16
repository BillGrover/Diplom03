package root.services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import root.model.PostVotes;
import root.model.Posts;

import java.util.List;

@Service
public class PostService {
    public static JSONObject packToJson(Page<Posts> posts) {
        JSONObject jResult = new JSONObject();
        jResult.put("count", posts.getTotalElements());

        if (posts.getTotalElements() == 0){
            jResult.put("posts", new JSONArray());
            return jResult;
        }

        JSONArray jArray = new JSONArray();
        posts.forEach(p -> {
            JSONObject jUser = new JSONObject();
            jUser.put("id", p.getUser().getId());
            jUser.put("name", p.getUser().getName());

            JSONObject jPost = new JSONObject();
            jPost.put("id", p.getId());
            jPost.put("timestamp", p.getTime().getTime() / 1000);
            jPost.put("user", jUser);
            jPost.put("title", p.getTitle());
            jPost.put("announce", removeHtmlTags(p.getText()));
            jPost.put("likeCount", countVotes(p.getVotes(), 1));
            jPost.put("dislikeCount", countVotes(p.getVotes(), -1));
            jPost.put("commentCount", p.getComments().size());
            jPost.put("viewCount", "???");
            jArray.add(jPost);
        });
        jResult.put("posts", jArray);
        return jResult;
    }

    private static int countVotes(List<PostVotes> votes, int value) {
        return (int) votes.stream().filter(v -> v.getValue() == value).count();
    }

    private static String removeHtmlTags(String htmlString) {
        String noHtmlText = Jsoup.parse(htmlString).text();
        if (noHtmlText.length() > 300)
            noHtmlText = noHtmlText.substring(0, 300) + "...";
        return noHtmlText;
    }
}
