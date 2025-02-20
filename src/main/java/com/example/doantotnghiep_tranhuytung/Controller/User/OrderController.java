package com.example.doantotnghiep_tranhuytung.Controller.User;

import com.example.doantotnghiep_tranhuytung.Entity.OrderDetailsEntity;
import com.example.doantotnghiep_tranhuytung.Entity.OrderEntity;
import com.example.doantotnghiep_tranhuytung.Entity.UserEntity;
import com.example.doantotnghiep_tranhuytung.Repository.OrderDetailRepository;
import com.example.doantotnghiep_tranhuytung.Repository.OrderRepository;
import com.example.doantotnghiep_tranhuytung.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping ("/user/order")
public class OrderController {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final HttpSession session;

    public OrderController(OrderRepository orderRepository, UserRepository userRepository, OrderDetailRepository orderDetailRepository, HttpSession session) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.session = session;
    }

    @GetMapping("/me")
    public String me(Model model,
                     @RequestParam(defaultValue = "0") int page,
                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        String userName = (String) session.getAttribute("userEmail");
        UserEntity userEntity = userRepository.findByEmail(userName).get();
        Page<OrderEntity> orderEntities = orderRepository.findByUserIdDesc(userEntity.getId(),pageable);
        model.addAttribute("orderEntities", orderEntities.getContent());
        model.addAttribute("totalPages", orderEntities.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        return "Profile/order-list";
    }
    @GetMapping("/me/detail/{id}")
    public String me(@PathVariable Long id, Model model) {
        List<OrderDetailsEntity> orderDetailsEntities = orderDetailRepository.findByOrderId(id);
        model.addAttribute("orderDetailsEntities", orderDetailsEntities);
        return "Profile/detail-order";
    }
}
