package com.example.doantotnghiep_tranhuytung.Controller;

import com.example.doantotnghiep_tranhuytung.Entity.MenuEntity;
import com.example.doantotnghiep_tranhuytung.Entity.ReviewsEntity;
import com.example.doantotnghiep_tranhuytung.Repository.CategoryRepository;
import com.example.doantotnghiep_tranhuytung.Repository.MenuRepository;
import com.example.doantotnghiep_tranhuytung.Repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Đây là một controller xử lý các yêu cầu điều hướng của ứng dụng.
 * Nó giúp hiển thị trang chủ và trang quản trị.
 */
@Controller
public class HomeController {
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;
    /**
     * Constructor để khởi tạo controller với một repository để thao tác với dữ liệu thực đơn (menu).
     *
     * @param menuRepository Đối tượng giúp truy vấn dữ liệu thực đơn từ cơ sở dữ liệu.
     */
    public HomeController(MenuRepository menuRepository, CategoryRepository categoryRepository, ReviewRepository reviewRepository) {
        this.menuRepository = menuRepository;
        this.categoryRepository = categoryRepository;
        this.reviewRepository = reviewRepository;
    }

    /**
     * Xử lý yêu cầu GET đến "/home".
     * Hiển thị trang chủ của ứng dụng và truyền danh sách thực đơn vào giao diện.
     *
     * @param model Đối tượng giúp truyền dữ liệu từ controller sang giao diện HTML.
     * @return Trả về trang "home.html".
     */
    @GetMapping("/home")
    public String home(Model model) {
        // Lấy toàn bộ danh sách thực đơn từ cơ sở dữ liệu và thêm vào model để hiển thị lên giao diện
        model.addAttribute("menuList", menuRepository.findAllByDeleteAtIsNull());
        return "home";
    }

    @GetMapping("/thuc-don")
    public String thucDon(Model model, @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "20") int size,
                          @RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "categoryId", required = false) Long categoryId,
                          @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
                          @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
                          @RequestParam(value = "startDate", required = false) String startDateStr,
                          @RequestParam(value = "endDate", required = false) String endDateStr) {
        // Chuyển đổi dữ liệu ngày từ chuỗi sang Timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp startDate = null;
        Timestamp endDate = null;

        try {
            if (startDateStr != null && !startDateStr.isEmpty()) {
                Date parsedDate = dateFormat.parse(startDateStr);
                startDate = new Timestamp(parsedDate.getTime());
            }
            if (endDateStr != null && !endDateStr.isEmpty()) {
                Date parsedDate = dateFormat.parse(endDateStr);
                endDate = new Timestamp(parsedDate.getTime());
            }
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi chuyển đổi ngày tháng");
        }
        // Lấy toàn bộ danh sách thực đơn từ cơ sở dữ liệu và thêm vào model để hiển thị lên giao diện
        // Lấy danh sách món ăn theo tiêu chí tìm kiếm
        Page<MenuEntity> menus = menuRepository.searchMenus(name, categoryId, minPrice, maxPrice, startDate, endDate, PageRequest.of(page, size));

        model.addAttribute("menus", menus.getContent());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", Math.max(menus.getTotalPages(), 1));
        return "Thuc_don";
    }

    /**
     * Xử lý yêu cầu GET đến "/admin".
     * Điều hướng người dùng đến trang quản trị.
     *
     * @return Trả về trang "Admin/AdminHome.html".
     */
    @GetMapping("/admin")
    public String admin() {
        return "Admin/AdminHome";
    }
    @GetMapping("/thuc-don/{id}")
    public String thucDon(@PathVariable Long id, Model model) {
        MenuEntity menuEntity = menuRepository.findById(id).get();
        List<ReviewsEntity> reviewsEntities = reviewRepository.findByMenuId(id);
        model.addAttribute("menu", menuEntity);
        model.addAttribute("reviewsEntities", reviewsEntities);
        return "ChiTiet-thucdon";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "contact"; // Trả về trang contact.html
    }
}
