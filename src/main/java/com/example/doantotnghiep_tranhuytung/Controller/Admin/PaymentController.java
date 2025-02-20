package com.example.doantotnghiep_tranhuytung.Controller.Admin;


import com.example.doantotnghiep_tranhuytung.Entity.OrderDetailsEntity;
import com.example.doantotnghiep_tranhuytung.Entity.OrderEntity;
import com.example.doantotnghiep_tranhuytung.Entity.OrderFoodEntity;
import com.example.doantotnghiep_tranhuytung.Entity.UserEntity;
import com.example.doantotnghiep_tranhuytung.Repository.OrderDetailRepository;
import com.example.doantotnghiep_tranhuytung.Repository.OrderFoodRepository;
import com.example.doantotnghiep_tranhuytung.Repository.OrderRepository;
import com.example.doantotnghiep_tranhuytung.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/admin/payment")
public class PaymentController {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailsRepository;
    private final OrderFoodRepository orderFoodRepository;
    private final UserRepository userRepository;

    public PaymentController(OrderRepository orderRepository, OrderDetailRepository orderDetailsRepository, OrderFoodRepository orderFoodRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderFoodRepository = orderFoodRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/{id}")
    public String processPayment(@PathVariable Long id, Model model) {
        try {
            // Lấy danh sách OrderFood của user
            List<OrderFoodEntity> orderFoodList = orderFoodRepository.findByUser(id);
            if(orderFoodList == null || orderFoodList.isEmpty()){
                model.addAttribute("error", "Không có món ăn nào trong giỏ hàng!");
                return "redirect:/admin/dat-mon/user/" + id;
            }

            // Tính tổng tiền
            BigDecimal totalPrice = BigDecimal.ZERO;
            for(OrderFoodEntity item : orderFoodList) {
                totalPrice = totalPrice.add(item.getPrice());
            }

            // Lấy thông tin user
            UserEntity user = userRepository.findById(id).orElse(null);
            if(user == null){
                model.addAttribute("error", "Không tìm thấy người dùng!");
                return "redirect:/admin/dat-mon";
            }

            // Tạo OrderEntity mới
            OrderEntity order = new OrderEntity();
            order.setUserId(user);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            order.setOrderDate(now);
            order.setTotalPrice(totalPrice);
            order.setOrderStatus("Đã thanh toán"); // hoặc trạng thái phù hợp theo nghiệp vụ
            order.setCreatedAt(now);
            order.setUpdatedAt(now);

            // Lưu OrderEntity
            OrderEntity savedOrder = orderRepository.save(order);

            // Với mỗi OrderFoodEntity, tạo OrderDetailsEntity
            for(OrderFoodEntity item : orderFoodList) {
                OrderDetailsEntity details = new OrderDetailsEntity();
                details.setOrderId(savedOrder);
                // Giả sử OrderFoodEntity có phương thức getMenu(), getQuantity() và getPrice()
                details.setMenuId(item.getMenu());
                details.setQuantity(item.getQuantity());
                details.setPrice(item.getPrice());
                orderDetailsRepository.save(details);
            }

            // Xóa hết các món ăn trong OrderFood sau khi lưu thành công
            orderFoodRepository.deleteAll(orderFoodList);

            model.addAttribute("message", "Thanh toán thành công!");
            // Điều hướng đến trang xác nhận hoặc trang hiển thị đơn hàng
            return "redirect:/admin/dat-mon/user/" + id;
        } catch(Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/admin/dat-mon/user/" + id;
        }
    }

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "20") int size,
            Model model) {
        Page<OrderEntity> orderList = orderRepository.findAll( PageRequest.of(page, size));
        model.addAttribute("orderList", orderList.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", Math.max(orderList.getTotalPages(), 1));
        return "Admin/OderList/List";
    }
    @GetMapping("/list/order/{id}")
    public String listOrderDetail(@PathVariable Long id,
                       Model model) {
        List<OrderDetailsEntity> orderList = orderDetailsRepository.findByOrderId( id);
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderDetailsEntity orderDetails : orderList) {
            totalPrice = totalPrice.add(orderDetails.getPrice());
        }
        model.addAttribute("orderList", orderList);
        model.addAttribute("totalPrice", totalPrice);
        return "Admin/OderList/Lisr-detail";
    }
}
