package se2.fit.tut08.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se2.fit.tut08.model.Post;
import se2.fit.tut08.model.User;
import se2.fit.tut08.repository.PostRepository;
import se2.fit.tut08.repository.UserRepository;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/post")
public class MemberController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @GetMapping("/list")
    public String listPosts(Model model, @RequestParam(defaultValue = "1") int p) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        PageRequest pageRequest = PageRequest.of(p - 1, 10); // 10 posts per page
        Page<Post> posts = postRepository.findByUserUsername(username, pageRequest);
        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", p);
        return "member/post-list";
    }

    @GetMapping("/add")
    public String addPost(Model model) {
        model.addAttribute("post", new Post());
        return "member/post-add";
    }

    @PostMapping("/add")
    public String addPostHandle(@Valid @ModelAttribute Post post, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "member/post-add";
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        post.setUser(user);
        post.setSlug(generateSlug(post.getTitle()));
        postRepository.save(post);
        return "redirect:/member/post/list";
    }

    @GetMapping("/del/{id}")
    public String deletePost(@PathVariable Integer id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Post> post = postRepository.findByIdAndUserUsername(id, username);
        post.ifPresent(postRepository::delete);
        return "redirect:/member/post/list";
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Integer id, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Post> post = postRepository.findByIdAndUserUsername(id, username);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            return "member/post-edit";
        }
        return "redirect:/member/post/list";
    }

    @PostMapping("/edit/{id}")
    public String editPostHandle(@PathVariable Integer id, @Valid @ModelAttribute Post post,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "member/post-edit";
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Post> existingPost = postRepository.findByIdAndUserUsername(id, username);
        if (existingPost.isPresent()) {
            Post updatedPost = existingPost.get();
            updatedPost.setTitle(post.getTitle());
            updatedPost.setSlug(generateSlug(post.getTitle()));
            updatedPost.setExcerpt(post.getExcerpt());
            updatedPost.setContent(post.getContent());
            postRepository.save(updatedPost);
        }
        return "redirect:/member/post/list";
    }

    private String generateSlug(String title) {
        return title.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
    }
}