package com.example.doantotnghiep_tranhuytung.Controller;

import com.example.doantotnghiep_tranhuytung.Entity.UserEntity;
import com.example.doantotnghiep_tranhuytung.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller chịu trách nhiệm xử lý các chức năng liên quan đến người dùng
 * như đăng nhập, đăng ký.
 */
@Controller("/user") // Định nghĩa đây là một Controller với đường dẫn gốc là "/user"
public class UserController {
    private final UserService userService; // Dịch vụ xử lý logic liên quan đến User
    private final HttpSession httpSession; // Đối tượng lưu trữ thông tin đăng nhập của người dùng

    /**
     * Constructor để khởi tạo UserController với các dependencies cần thiết.
     */
    public UserController(UserService userService, HttpSession httpSession) {
        this.userService = userService;
        this.httpSession = httpSession;
    }

    /**
     * Hiển thị trang đăng nhập.
     * @return Trả về tên file HTML của trang đăng nhập.
     */
    @GetMapping("/dangnhap")
    public String dangnhap() {
        return "dangnhap";
    }

    /**
     * Xử lý đăng nhập người dùng.
     * @param email Email của người dùng.
     * @param password Mật khẩu của người dùng.
     * @param model Đối tượng Model để truyền dữ liệu sang giao diện.
     * @return Chuyển hướng đến trang chủ nếu đăng nhập thành công, nếu thất bại quay lại trang đăng nhập.
     */
    @PostMapping("/dangnhap")
    public String postDangnhap(@RequestParam String email, @RequestParam String password, Model model) {
        try {
            // Kiểm tra thông tin đăng nhập
            UserEntity user = userService.dangnhap(email, password);

            // Lưu thông tin người dùng vào session để sử dụng trong các phiên sau
            httpSession.setAttribute("userRole", user.getRole()); // Lưu quyền của user vào session
            httpSession.setAttribute("userEmail", user.getEmail()); // Lưu email vào session
            model.addAttribute("user", user);

            // Chuyển hướng về trang chủ sau khi đăng nhập thành công
            return "redirect:/home";
        } catch (Exception e) {
            // Nếu có lỗi (tài khoản/mật khẩu sai), hiển thị lỗi trên giao diện
            model.addAttribute("error", e.getMessage());
            return "dangnhap"; // Quay lại trang đăng nhập
        }
    }

    /**
     * Hiển thị trang đăng ký.
     * @return Trả về tên file HTML của trang đăng ký.
     */
    @GetMapping("/dangky")
    public String dangky() {
        return "dangky"; // Trả về trang đăng ký
    }

    /**
     * Xử lý đăng ký người dùng mới.
     * @param userEntity Đối tượng chứa thông tin người dùng cần đăng ký.
     * @param model Đối tượng Model để truyền dữ liệu sang giao diện.
     * @return Chuyển hướng về trang đăng nhập nếu đăng ký thành công, nếu thất bại quay lại trang đăng ký.
     */
    @PostMapping("/dangky")
    public String postDangky(@ModelAttribute UserEntity userEntity, Model model) {
        try {
            // Gọi service để xử lý đăng ký người dùng mới
            userService.dangky(userEntity);

            // Thông báo thành công và chuyển hướng sang trang đăng nhập
            model.addAttribute("success", "Đăng ký thành công!");
            return "redirect:/dangnhap";
        } catch (Exception e) {
            // Nếu có lỗi, hiển thị lỗi trên giao diện
            model.addAttribute("error", e.getMessage());
            return "dangky"; // Quay lại trang đăng ký nếu có lỗi
        }
    }
}
