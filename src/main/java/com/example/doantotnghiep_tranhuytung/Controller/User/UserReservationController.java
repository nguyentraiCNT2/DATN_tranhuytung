package com.example.doantotnghiep_tranhuytung.Controller.User;

import com.example.doantotnghiep_tranhuytung.Entity.ReservationsEntity;
import com.example.doantotnghiep_tranhuytung.Entity.UserEntity;
import com.example.doantotnghiep_tranhuytung.Repository.ReservationRepository;
import com.example.doantotnghiep_tranhuytung.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/user/dat-ban")
public class UserReservationController {
    private final ReservationRepository reservationRepository;
    private final HttpSession httpSession; // Đối tượng lưu trữ thông tin đăng nhập của người dùng
    private final UserRepository userRepository;

    public UserReservationController(ReservationRepository reservationRepository, HttpSession httpSession, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.httpSession = httpSession;
        this.userRepository = userRepository;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new UserEntity());
        return "create";
    }
    @PostMapping("/create")
    public String editMenu(@ModelAttribute ReservationsEntity reservation, Model model) {
        try {
            String userEmail = (String) httpSession.getAttribute("userEmail");
            UserEntity user = userRepository.findByEmail(userEmail).orElse(null);
            if (user == null) {
                return "redirect:/dangnhap";
            }

            // Chuyển đổi ngày từ String sang LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            reservation.setReservationDate(LocalDateTime.parse(reservation.getReservationDate().toString(), formatter));
            reservation.setUserId(user);
            reservation.setStatus("Đang xử lý");
            reservation.setCreatedAt(Timestamp.from(Instant.now()));

            reservationRepository.save(reservation);
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "create";
        }
    }

    /**
     * Xử lý cập nhật thông tin món ăn.
     */
    @GetMapping("/edit/{id}")
    public String editMenu(@PathVariable Long id, @ModelAttribute ReservationsEntity reservationsEntity, Model model) {
        try {
            ReservationsEntity reservations = reservationRepository.findById(id).get();
            reservations.setStatus("Hủy");
            reservationRepository.save(reservations);
            return "redirect:/admin/thuc-don/list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Admin/Menu/edit";
        }
    }
}
