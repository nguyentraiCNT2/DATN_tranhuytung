package com.example.doantotnghiep_tranhuytung.Controller.Admin;

import com.example.doantotnghiep_tranhuytung.Entity.CategoryEntity;
import com.example.doantotnghiep_tranhuytung.Entity.MenuEntity;
import com.example.doantotnghiep_tranhuytung.Repository.CategoryRepository;
import com.example.doantotnghiep_tranhuytung.Repository.MenuRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Controller này chịu trách nhiệm quản lý thực đơn của nhà hàng.
 * Bao gồm các chức năng: xem danh sách, thêm, sửa, xóa món ăn.
 */
@Controller
@RequestMapping("/admin/thuc-don")
public class MenuController {
    private final MenuRepository MenuRepository;
    private final CategoryRepository categoryRepository;

    /**
     * Constructor để khởi tạo repository cho thực đơn và danh mục món ăn.
     */
    public MenuController(MenuRepository menuRepository, CategoryRepository categoryRepository) {
        this.MenuRepository = menuRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Hiển thị danh sách thực đơn với các bộ lọc tìm kiếm.
     * @param page Trang hiện tại.
     * @param size Số lượng món ăn trên mỗi trang.
     * @param name Tên món ăn cần tìm.
     * @param categoryId ID danh mục của món ăn.
     * @param minPrice Giá tối thiểu.
     * @param maxPrice Giá tối đa.
     * @param startDateStr Ngày bắt đầu tìm kiếm.
     * @param endDateStr Ngày kết thúc tìm kiếm.
     * @param model Đối tượng chứa dữ liệu để hiển thị trên giao diện.
     * @return Trả về trang danh sách thực đơn.
     */
    @GetMapping("/list")
    public String listMenus(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "20") int size,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "categoryId", required = false) Long categoryId,
                            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
                            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
                            @RequestParam(value = "startDate", required = false) String startDateStr,
                            @RequestParam(value = "endDate", required = false) String endDateStr,
                            Model model) {

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

        // Lấy danh sách món ăn theo tiêu chí tìm kiếm
        Page<MenuEntity> menus = MenuRepository.searchMenus(name, categoryId, minPrice, maxPrice, startDate, endDate, PageRequest.of(page, size));

        model.addAttribute("menus", menus.getContent());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", Math.max(menus.getTotalPages(), 1));

        return "Admin/Menu/list";
    }

    /**
     * Hiển thị trang thêm món ăn mới.
     */
    @GetMapping("/add")
    public String addMenu(Model model) {
        List<CategoryEntity> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "Admin/Menu/add";
    }

    /**
     * Xử lý thêm món ăn mới vào danh sách.
     */
    @PostMapping("/add")
    public String addMenu(@ModelAttribute MenuEntity menu, MultipartFile imageFile, Model model) {
        try {
            CategoryEntity categoryEntity = categoryRepository.findById(menu.getCategoryId().getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));

            // Nếu có ảnh được tải lên, lưu ảnh vào database
            if (imageFile != null && !imageFile.isEmpty()) {
                menu.setImage(imageFile.getBytes());
            }

            menu.setCategoryId(categoryEntity);
            menu.setCreatedAt(Timestamp.from(Instant.now()));
            menu.setUpdatedAt(Timestamp.from(Instant.now()));

            // Lưu món ăn vào database
            MenuEntity savedMenu = MenuRepository.save(menu);
            savedMenu.setImageUrl("/image/menu/" + savedMenu.getId());
            MenuRepository.save(savedMenu);

            return "redirect:/admin/thuc-don/list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Admin/Menu/add";
        }
    }

    /**
     * Hiển thị trang chỉnh sửa món ăn.
     */
    @GetMapping("/edit/{id}")
    public String editMenu(@PathVariable Long id, Model model) {
        Optional<MenuEntity> menuEntity = MenuRepository.findById(id);
        model.addAttribute("menu", menuEntity.orElse(null));
        model.addAttribute("categories", categoryRepository.findAll());
        return "Admin/Menu/edit";
    }

    /**
     * Xử lý cập nhật thông tin món ăn.
     */
    @PostMapping("/edit/{id}")
    public String editMenu(@PathVariable Long id, @ModelAttribute MenuEntity menu, MultipartFile imageFile, Model model) {
        try {
            Optional<CategoryEntity> categoryEntity = categoryRepository.findById(menu.getCategoryId().getId());

            // Nếu có ảnh mới tải lên thì cập nhật, nếu không thì giữ ảnh cũ
            if (imageFile != null && !imageFile.isEmpty()) {
                menu.setImage(imageFile.getBytes());
                menu.setImageUrl("/image/menu/" + id);
            } else {
                MenuEntity existingMenu = MenuRepository.findById(id).orElseThrow();
                menu.setImage(existingMenu.getImage());
                menu.setImageUrl(existingMenu.getImageUrl());
            }

            menu.setId(id);
            menu.setCategoryId(categoryEntity.orElseThrow());
            menu.setUpdatedAt(Timestamp.from(Instant.now()));

            MenuRepository.save(menu);
            return "redirect:/admin/thuc-don/list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Admin/Menu/edit";
        }
    }

    /**
     * Xóa món ăn (thực chất là đặt thời gian xóa mà không xóa khỏi database).
     */
    @GetMapping("/delete/{id}")
    public String deleteMenu(@PathVariable Long id, Model model) {
        try {
            MenuEntity menuEntity = MenuRepository.findById(id).orElseThrow();
            menuEntity.setDeleteAt(Timestamp.from(Instant.now()));
            MenuRepository.save(menuEntity);
            return "redirect:/admin/thuc-don/list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Admin/Menu/list";
        }
    }

    /**
     * Trả về ảnh của món ăn theo ID.
     */
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Optional<MenuEntity> menuOpt = MenuRepository.findById(id);

        // Nếu món ăn có ảnh, trả về ảnh đó dưới dạng dữ liệu nhị phân
        if (menuOpt.isPresent() && menuOpt.get().getImage() != null) {
            byte[] imageBytes = menuOpt.get().getImage();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        }

        // Nếu không tìm thấy ảnh, trả về lỗi 404
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
