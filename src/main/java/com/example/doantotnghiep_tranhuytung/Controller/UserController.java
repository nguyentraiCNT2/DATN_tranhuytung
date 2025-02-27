package com.example.doantotnghiep_tranhuytung.Controller;

import com.example.doantotnghiep_tranhuytung.Entity.UserEntity;
import com.example.doantotnghiep_tranhuytung.Repository.UserRepository;
import com.example.doantotnghiep_tranhuytung.Request.ChangePasswordRequest;
import com.example.doantotnghiep_tranhuytung.Request.ResetPasswordRequest;
import com.example.doantotnghiep_tranhuytung.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller chịu trách nhiệm xử lý các chức năng liên quan đến người dùng
 * như đăng nhập, đăng ký.
 */
@Controller("/user") // Định nghĩa đây là một Controller với đường dẫn gốc là "/user"
public class UserController {
    private final UserService userService; // Dịch vụ xử lý logic liên quan đến User
    private final HttpSession httpSession; // Đối tượng lưu trữ thông tin đăng nhập của người dùng
    private final UserRepository userRepository;

    /**
     * Constructor để khởi tạo UserController với các dependencies cần thiết.
     */
    public UserController(UserService userService, HttpSession httpSession, UserRepository userRepository) {
        this.userService = userService;
        this.httpSession = httpSession;
        this.userRepository = userRepository;
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
            httpSession.setAttribute("userName", user.getFullName());
            httpSession.setAttribute("userId", user.getId());
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

    // Xử lý logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/dangnhap";
    }

    @GetMapping("/user/profile")
    public String profile(Model model) {
        try {
            String username = (String) httpSession.getAttribute("userEmail");
            UserEntity user = userRepository.findByEmail(username).get();
            model.addAttribute("user", user);
            return "Profile/detail";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/user/change-image")
    public String profile(@RequestParam("imageFile") MultipartFile imageFile,  Model model) {
        try {
            String username = (String) httpSession.getAttribute("userEmail");
            UserEntity user = userRepository.findByEmail(username).get();
            user.setAvatar(imageFile.getBytes());
            userRepository.save(user);
            model.addAttribute("user", user);
            return "redirect:/user/profile";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/user/profile/update")
    public String updateProfile(Model model) {
        String username = (String) httpSession.getAttribute("userEmail");
        UserEntity user = userRepository.findByEmail(username).get();
        model.addAttribute("user", user);
        return "Profile/update_prodile";
    }
    @PostMapping("/user/profile/update")
    public String updateProfile(@ModelAttribute UserEntity userEntity, Model model) {
        try {
            String username = (String) httpSession.getAttribute("userEmail");
            UserEntity user = userRepository.findByEmail(username).get();
            user.setFullName(userEntity.getFullName());
            user.setPhone(userEntity.getPhone());
            userRepository.save(user);
            return "redirect:/user/profile";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/user/change-password")
    public String changePassword(Model model) {
        return "Profile/profile_changePassword";
    }
    @PostMapping("/user/change-password")
    public String changePassword(@ModelAttribute ChangePasswordRequest changePasswordRequest, Model model) {
        try {
            String username = (String) httpSession.getAttribute("userEmail");
            UserEntity user = userRepository.findByEmail(username).get();
            if (user.getPassword().equals(changePasswordRequest.getOldPassword())){
                model.addAttribute("error", "Mật khẩu hiện tại không chính xác");
                return "Profile/profile_changePassword";

            }
            if (changePasswordRequest.getNewPassword().equals(changePasswordRequest.getOldPassword())){
                model.addAttribute("error", "Mật khẩu mới không thế trùng với mật khẩu hiện tại");
                return "Profile/profile_changePassword";

            }
            if (changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())){
                model.addAttribute("error", "Mật khẩu không trùng khớp");
                return "Profile/profile_changePassword";

            }
            if (changePasswordRequest.getNewPassword().length() < 8){
                model.addAttribute("error", "Mật khẩu tối thiểu 8 ký tự");
                return "Profile/profile_changePassword";

            }
            if (changePasswordRequest.getNewPassword().contains(" ") ){
                model.addAttribute("error", "Mật khẩu không thể chứa khoản trắng");
                return "Profile/profile_changePassword";
            }
            user.setPassword(changePasswordRequest.getNewPassword());
            userRepository.save(user);
            return "redirect:/user/profile";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    @GetMapping("/for-get-password")
    public String forgetPassword(){
        return "ForgetPassword";
    }
    @PostMapping("/for-get-password")
    public String forgetPassword(@RequestParam("email") String email, Model model) {
        try {
            UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
            httpSession.setAttribute("forgetPasswordEmail", userEntity.getEmail());
            return "redirect:/re-set-password";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "ForgetPassword";
        }
    }
    @GetMapping("/re-set-password")
    public String reSetPassword(){
        return "ResetPassword";
    }
    @PostMapping("/re-set-password")
    public String reSetPassword(@ModelAttribute ResetPasswordRequest resetPasswordRequest, Model model) {
        try {
            if (!resetPasswordRequest.getNewPassword().equals( resetPasswordRequest.getConfirmPassword())) {
                throw new RuntimeException("Mật khẩu không trùng khớp");
            }
            // Kiểm tra mật khẩu có hợp lệ không
            if (resetPasswordRequest.getNewPassword().length() < 8) {
                throw new RuntimeException("Mật khẩu cần tối thiểu 8 ký tự");
            }
            if (resetPasswordRequest.getNewPassword().contains(" ")) {
                throw new RuntimeException("Mật khẩu không thể chứa khoảng trắng");
            }
            UserEntity userEntity = userRepository.findByEmail(resetPasswordRequest.getEmail()).orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
            userEntity.setPassword(resetPasswordRequest.getNewPassword());
            userRepository.save(userEntity);
            return "redirect:/dangnhap";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "ResetPassword";
        }
    }
}
