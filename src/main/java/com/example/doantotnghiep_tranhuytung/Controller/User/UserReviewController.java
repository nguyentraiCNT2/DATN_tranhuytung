package com.example.doantotnghiep_tranhuytung.Controller.User;


import com.example.doantotnghiep_tranhuytung.Entity.MenuEntity;
import com.example.doantotnghiep_tranhuytung.Entity.ReviewsEntity;
import com.example.doantotnghiep_tranhuytung.Entity.UserEntity;
import com.example.doantotnghiep_tranhuytung.Repository.MenuRepository;
import com.example.doantotnghiep_tranhuytung.Repository.ReviewRepository;
import com.example.doantotnghiep_tranhuytung.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.time.Instant;

@Controller
@RequestMapping("/user/comment")
public class UserReviewController {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final HttpSession session;
    private final MenuRepository menuRepository;

    public UserReviewController(ReviewRepository reviewRepository, UserRepository userRepository, HttpSession session, MenuRepository menuRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.session = session;
        this.menuRepository = menuRepository;
    }


    @PostMapping("/new")
    public String newComment(@ModelAttribute ReviewsEntity reviewDTO, Model model) {
        try {
            String userName = (String) session.getAttribute("userEmail");
            UserEntity userEntity = userRepository.findByEmail(userName).get();
            MenuEntity menuEntity = menuRepository.findById(reviewDTO.getMenuId().getId()).get();
            ReviewsEntity reviewEntity = new ReviewsEntity();
            reviewEntity.setContent(reviewDTO.getContent());
            reviewEntity.setUserId(userEntity);
            reviewEntity.setMenuId(menuEntity);
            reviewEntity.setRating(reviewDTO.getRating() != 0 ? reviewDTO.getRating() : 1);
            reviewEntity.setCreatedAt(Timestamp.from(Instant.now()));
            reviewRepository.save(reviewEntity);
            return "redirect:/thuc-don/" + reviewDTO.getMenuId().getId();
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/thuc-don/" + reviewDTO.getMenuId().getId();
        }
    }
}