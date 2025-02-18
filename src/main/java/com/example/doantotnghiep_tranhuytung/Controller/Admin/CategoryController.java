package com.example.doantotnghiep_tranhuytung.Controller.Admin;

import com.example.doantotnghiep_tranhuytung.Entity.CategoryEntity;
import com.example.doantotnghiep_tranhuytung.Service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // Đánh dấu đây là một Controller trong Spring MVC
@RequestMapping("/admin/the-loai") // Định nghĩa đường dẫn gốc cho tất cả các API trong controller này
public class CategoryController {
    private final CategoryService categoryService; // Khai báo service để xử lý logic

    // Khởi tạo CategoryController và inject CategoryService vào (Dependency Injection)
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Lấy danh sách tất cả các danh mục (the-loai) và hiển thị lên giao diện.
     * Có hỗ trợ tìm kiếm theo tên danh mục.
     * Hỗ trợ phân trang với tham số page (trang) và size (số danh mục trên một trang).
     */
    @GetMapping("/list")
    public String getAllCategory(Model model, @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "20") int size) {
        try {
            Page<CategoryEntity> categoryEntities;
            // Nếu có tham số tìm kiếm name, thì tìm theo tên
            if (name != null ) {
                categoryEntities = categoryService.getByName(name, PageRequest.of(page, size));
            } else {
                // Nếu không có tham số tìm kiếm, lấy toàn bộ danh mục
                categoryEntities = categoryService.getAllCategory(PageRequest.of(page, size));
            }

            // Đưa danh sách danh mục và thông tin phân trang vào Model để hiển thị lên giao diện
            model.addAttribute("categories", categoryEntities.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", categoryEntities.getTotalPages() > 0 ? categoryEntities.getTotalPages() : 1);
            return "Admin/Category/list"; // Trả về trang danh sách danh mục
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Admin/Category/list";
        }
    }

    /**
     * Trả về giao diện thêm mới danh mục
     */
    @GetMapping("/add")
    public String getAddCategory(Model model) {
        try {
            return "Admin/Category/add"; // Trả về trang thêm mới danh mục
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Admin/Category/add";
        }
    }

    /**
     * Xử lý thêm mới danh mục khi người dùng gửi dữ liệu từ form.
     * Sau khi thêm thành công, chuyển hướng về danh sách danh mục.
     */
    @PostMapping("/add")
    public String addCategory(@ModelAttribute("category") CategoryEntity category, Model model) {
        try {
            categoryService.saveCategory(category); // Lưu danh mục mới vào database
            return "redirect:/admin/the-loai/list"; // Chuyển hướng về danh sách danh mục
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Admin/Category/add";
        }
    }

    /**
     * Trả về giao diện chỉnh sửa danh mục theo ID
     */
    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model) {
        try {
            CategoryEntity category = categoryService.getCategoryById(id); // Lấy danh mục từ ID
            model.addAttribute("category", category); // Đưa vào Model để hiển thị trên giao diện
            return "Admin/Category/edit";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Xử lý cập nhật danh mục sau khi người dùng chỉnh sửa.
     * Sau khi cập nhật thành công, chuyển hướng về danh sách danh mục.
     */
    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, @ModelAttribute("category") CategoryEntity category, Model model) {
        try {
            category.setId(id); // Đảm bảo ID không bị thay đổi
            categoryService.updateCategory(category); // Cập nhật danh mục vào database
            return "redirect:/admin/the-loai/list"; // Chuyển hướng về danh sách danh mục
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Admin/Category/edit";
        }
    }

    /**
     * Xóa danh mục theo ID
     */
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, Model model) {
        try {
            categoryService.deleteCategory(id); // Gọi service để xóa danh mục
            return "redirect:/admin/the-loai/list"; // Chuyển hướng về danh sách danh mục
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/admin/the-loai/list";
        }
    }
}
