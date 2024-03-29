package web.forum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.forum.model.Discussion;
import web.forum.service.DiscussionService;
import web.forum.service.PostService;
import web.forum.service.UserService;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Контроллер отвечает за сообщения в определенной ветке
 */
@Controller
public class DiscussionsControl {

     private static final Logger LOGGER = LoggerFactory.getLogger(DiscussionsControl.class);

    private final PostService postService;
    private final UserService userService;
    private final DiscussionService discussionService;

    private AtomicInteger idA = new AtomicInteger();

    public DiscussionsControl(PostService postService,
                              UserService userService,
                              DiscussionService discussionService) {
        this.postService = postService;
        this.userService = userService;
        this.discussionService = discussionService;
    }

    @GetMapping("post/discussionpost")
    public String index(Model model) {
        model.addAttribute("user", SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
        model.addAttribute("post", postService.findById(idA.intValue()));
        return "post/discussionpost";
    }

    @GetMapping("post/discussionmain")
    public String mainPost(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
        idA.set(id);
        model.addAttribute("post", postService.findById(idA.intValue()));
        return "post/discussionpost";
    }

    /**
     * discussions.jsp
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/discussions")
    public String saveDiscus(@RequestParam("id") int id, Model model) {

        var rsl = postService.findById(id).getUser();
        Discussion discussion = Discussion.of(null, rsl);
        discussion.setId(id);

        model.addAttribute("post", discussion);
        return "post/discussions";
    }

    /**
     * @param discussion
     * @return
     */
    @PostMapping("/saveUpdateDisc")
    public String saveUpdate(@ModelAttribute Discussion discussion, Model model) {
        System.out.println(discussion);
        var id = discussion.getId();
        discussion.setId(0);
        var user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var rslUI = userService.findUserByUsername(userService.contextIn(user));
        discussionService.putUser(discussion, rslUI);
        var desc = discussionService.save(discussion);
        var post = postService.findById(id);
        postService.putDiscToPost(post, desc);

        var pst = postService.updatePost(post);
        idA.set(pst.getId());
        model.addAttribute("post", pst);
        return "redirect:/post/discussionpost";
    }
}
