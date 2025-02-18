package com.example.doantotnghiep_tranhuytung.Controller.User;

import com.example.doantotnghiep_tranhuytung.Entity.MenuEntity;
import com.example.doantotnghiep_tranhuytung.Entity.OrderFoodEntity;
import com.example.doantotnghiep_tranhuytung.Entity.UserEntity;
import com.example.doantotnghiep_tranhuytung.Repository.MenuRepository;
import com.example.doantotnghiep_tranhuytung.Repository.OrderFoodRepository;
import com.example.doantotnghiep_tranhuytung.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("/user/dat-mon")
public class UserOrderFoodController {
    private final OrderFoodRepository orderFoodRepository;
    private final HttpSession session;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;


    public UserOrderFoodController(OrderFoodRepository orderFoodRepository, HttpSession session, UserRepository userRepository, MenuRepository menuRepository) {
        this.orderFoodRepository = orderFoodRepository;
        this.session = session;
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
    }

    @GetMapping("/me")
    public String me(Model model) {
        try {
            String username = (String) session.getAttribute("userEmail");
            UserEntity user = userRepository.findByEmail(username).get();
            List<OrderFoodEntity> orderFoodEntities = orderFoodRepository.findByUser(user.getId());
            model.addAttribute("orderFoodEntities", orderFoodEntities);
            return "Orderfood-me";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Orderfood-me";
        }
    }
    @PostMapping("/order/{id}")
    public String orderFood(@PathVariable Long id, Model model) {
        try {
            MenuEntity menuEntity = menuRepository.findById(id).get();
            String username = (String) session.getAttribute("userEmail");
            UserEntity user = userRepository.findByEmail(username).get();
            OrderFoodEntity orderFoodEntity = new OrderFoodEntity();
            orderFoodEntity.setMenu(menuEntity);
            orderFoodEntity.setUser(user);
            orderFoodEntity.setQuantity(1);
            BigDecimal totalPrice = menuEntity.getPrice().multiply(new BigDecimal(orderFoodEntity.getQuantity()));
            orderFoodEntity.setPrice(totalPrice);
            orderFoodEntity.setStatus("Đang chờ");
            orderFoodEntity.setCreateAt(Timestamp.from(Instant.now()));
            orderFoodRepository.save(orderFoodEntity);
            return "redirect:/user/dat-mon/me";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Orderfood-me";
        }
    }
    @PostMapping("/update/quantity/up/{id}")
    public String updateQuantityUp(@PathVariable Long id, Model model) {
        try {
            OrderFoodEntity orderFoodEntity = orderFoodRepository.findById(id).get();
            orderFoodEntity.setQuantity(orderFoodEntity.getQuantity() + 1);
            BigDecimal totalPrice = orderFoodEntity.getMenu().getPrice().multiply(new BigDecimal(orderFoodEntity.getQuantity()));
            orderFoodEntity.setPrice(totalPrice);
            orderFoodRepository.save(orderFoodEntity);
            return "redirect:/user/dat-mon/me";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
   return "Orderfood-me";
        }
    }
    @PostMapping("/update/quantity/down/{id}")
    public String updateQuantityDown(@PathVariable Long id, Model model) {
        try {
            OrderFoodEntity orderFoodEntity = orderFoodRepository.findById(id).get();
            if (orderFoodEntity.getQuantity() > 1){
                orderFoodEntity.setQuantity(orderFoodEntity.getQuantity() - 1);
                BigDecimal totalPrice = orderFoodEntity.getMenu().getPrice().multiply(new BigDecimal(orderFoodEntity.getQuantity()));
                orderFoodEntity.setPrice(totalPrice);
            }
            else {
                orderFoodEntity.setQuantity(1);
            }
            orderFoodRepository.save(orderFoodEntity);
            return "redirect:/user/dat-mon/me";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Orderfood-me";
        }
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        try {
            OrderFoodEntity orderFoodEntity = orderFoodRepository.findById(id).get();
            if (orderFoodEntity.getStatus().equals("Đang thực hiện")){
                model.addAttribute("error", "Món ăn đang được thực hiện không thể xóa");
                return "redirect:/user/dat-mon/me";
            }
            else if (orderFoodEntity.getStatus().equals("Đã hoàn thành")){
                model.addAttribute("error", "Món ăn đã hoàn thành không thể xóa");
                return "redirect:/user/dat-mon/me";
            }
            else {
                orderFoodRepository.delete(orderFoodEntity);
                return "redirect:/user/dat-mon/me";
            }

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Orderfood-me";
        }
    }
}
