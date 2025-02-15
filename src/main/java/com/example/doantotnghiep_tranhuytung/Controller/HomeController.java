package com.example.doantotnghiep_tranhuytung.Controller;

import com.example.doantotnghiep_tranhuytung.Repository.MenuRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Đây là một controller xử lý các yêu cầu điều hướng của ứng dụng.
 * Nó giúp hiển thị trang chủ và trang quản trị.
 */
@Controller
public class HomeController {
    private final MenuRepository menuRepository;

    /**
     * Constructor để khởi tạo controller với một repository để thao tác với dữ liệu thực đơn (menu).
     * @param menuRepository Đối tượng giúp truy vấn dữ liệu thực đơn từ cơ sở dữ liệu.
     */
    public HomeController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    /**
     * Xử lý yêu cầu GET đến "/home".
     * Hiển thị trang chủ của ứng dụng và truyền danh sách thực đơn vào giao diện.
     * @param model Đối tượng giúp truyền dữ liệu từ controller sang giao diện HTML.
     * @return Trả về trang "home.html".
     */
    @GetMapping("/home")
    public String home(Model model) {
        // Lấy toàn bộ danh sách thực đơn từ cơ sở dữ liệu và thêm vào model để hiển thị lên giao diện
        model.addAttribute("menuList", menuRepository.findAll());
        return "home";
    }

    /**
     * Xử lý yêu cầu GET đến "/admin".
     * Điều hướng người dùng đến trang quản trị.
     * @return Trả về trang "Admin/AdminHome.html".
     */
    @GetMapping("/admin")
    public String admin() {
        return "Admin/AdminHome";
    }
}
