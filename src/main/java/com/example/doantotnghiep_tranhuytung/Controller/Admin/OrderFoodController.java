package com.example.doantotnghiep_tranhuytung.Controller.Admin;

import com.example.doantotnghiep_tranhuytung.Entity.OrderFoodEntity;
import com.example.doantotnghiep_tranhuytung.Entity.UserEntity;
import com.example.doantotnghiep_tranhuytung.Repository.OrderFoodRepository;
import com.example.doantotnghiep_tranhuytung.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin/dat-mon")
public class OrderFoodController {
    private final OrderFoodRepository orderFoodRepository;
    private final UserRepository userRepository;


    public OrderFoodController(OrderFoodRepository orderFoodRepository, UserRepository userRepository) {
        this.orderFoodRepository = orderFoodRepository;
        this.userRepository = userRepository;
    }
    @GetMapping
    public String UserOrderFood(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "20") int size,
                                Model model) {
        try {
            Page<UserEntity> userEntities = userRepository.findAll( PageRequest.of(page, size));
            model.addAttribute("userEntities", userEntities);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", Math.max(userEntities.getTotalPages(), 1));
            return "Admin/OrderFood/userOrderFood";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Admin/OrderFood/userOrderFood";
        }
    }

    @GetMapping("/user/{id}")
    public String orderFood(@PathVariable Long id, Model model) {
        try {
            List<OrderFoodEntity> orderFoodEntities = orderFoodRepository.findByUser(id);
            BigDecimal totalPrice = BigDecimal.ZERO;

            for (OrderFoodEntity orderFoodEntity : orderFoodEntities) {
                totalPrice = totalPrice.add(orderFoodEntity.getPrice());
            }

            model.addAttribute("orderFoodEntities", orderFoodEntities);
            model.addAttribute("totalPrice", totalPrice); // Thêm tổng tiền vào model
            model.addAttribute("userId", id);
            return "Admin/OrderFood/orderFood";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Admin/OrderFood/orderFood";
        }
    }


    @GetMapping("/update/{id}")
    public String orderFoodUpdate(@PathVariable Long id, Model model) {
        try {
            OrderFoodEntity foodEntity = orderFoodRepository.findById(id).get();
            model.addAttribute("foodEntity", foodEntity);
            return "Admin/OrderFood/orderFoodUpdate";
        }catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Admin/OrderFood/orderFoodUpdate";
        }
    }

    @PostMapping("/update/{id}")
    public String orderFoodUpdate(@PathVariable Long id, @ModelAttribute OrderFoodEntity orderFood, Model model) {
        try {
            OrderFoodEntity foodEntity = orderFoodRepository.findById(id).get();
            foodEntity.setStatus(orderFood.getStatus());
            orderFoodRepository.save(foodEntity);
            model.addAttribute("foodEntity", foodEntity);
            return "redirect:/admin/dan-mon";
        }catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Admin/OrderFood/orderFoodUpdate";
        }
    }
}
