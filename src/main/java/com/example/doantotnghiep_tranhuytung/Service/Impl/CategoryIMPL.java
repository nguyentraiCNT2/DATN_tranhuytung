package com.example.doantotnghiep_tranhuytung.Service.Impl;

import com.example.doantotnghiep_tranhuytung.Entity.CategoryEntity;
import com.example.doantotnghiep_tranhuytung.Entity.MenuEntity;
import com.example.doantotnghiep_tranhuytung.Repository.CategoryRepository;
import com.example.doantotnghiep_tranhuytung.Repository.MenuRepository;
import com.example.doantotnghiep_tranhuytung.Service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation cho CategoryService, xử lý logic liên quan đến danh mục thực đơn (Category).
 */
@Service
public class CategoryIMPL implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final MenuRepository menuRepository;

    /**
     * Constructor để inject các repository cần thiết.
     */
    public CategoryIMPL(CategoryRepository categoryRepository, MenuRepository menuRepository) {
        this.categoryRepository = categoryRepository;
        this.menuRepository = menuRepository;
    }

    /**
     * Lấy danh sách danh mục dựa trên tên có phân trang.
     * @param categoryName Tên danh mục cần tìm.
     * @param pageable Thông tin phân trang.
     * @return Page chứa danh sách danh mục tìm được.
     */
    @Override
    public Page<CategoryEntity> getByName(String categoryName, Pageable pageable) {
        try {
            Page<CategoryEntity> list = categoryRepository.findByName(categoryName, pageable);
            if (list.getContent().isEmpty()) {
                throw new RuntimeException("Không tìm thấy thể loại nào tương tự");
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Lấy tất cả danh mục có phân trang.
     * @param pageable Thông tin phân trang.
     * @return Page chứa danh sách danh mục.
     */
    @Override
    public Page<CategoryEntity> getAllCategory(Pageable pageable) {
        try {
            return categoryRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Lấy tất cả danh mục không phân trang.
     * @return Danh sách danh mục.
     */
    @Override
    public List<CategoryEntity> getAllCategory() {
        try {
            List<CategoryEntity> list = categoryRepository.findAll();
            if (list.isEmpty()) {
                throw new RuntimeException("Không tìm thấy thể loại nào tương tự");
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Lấy danh mục theo ID.
     * @param id ID của danh mục.
     * @return Đối tượng CategoryEntity nếu tìm thấy.
     */
    @Override
    public CategoryEntity getCategoryById(Long id) {
        try {
            return categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại nào có ID là: " + id));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Lưu một danh mục mới vào cơ sở dữ liệu.
     * @param category Đối tượng CategoryEntity cần lưu.
     * @return Đối tượng CategoryEntity đã được lưu.
     */
    @Override
    public CategoryEntity saveCategory(CategoryEntity category) {
        try {
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Cập nhật thông tin danh mục.
     * @param category Đối tượng CategoryEntity chứa thông tin mới.
     * @return Đối tượng CategoryEntity đã được cập nhật.
     */
    @Override
    public CategoryEntity updateCategory(CategoryEntity category) {
        try {
            CategoryEntity existingCategory = categoryRepository.findById(category.getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại nào có ID là: " + category.getId()));

            existingCategory.setName(category.getName());
            return categoryRepository.save(existingCategory);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Xóa danh mục theo ID.
     * @param id ID của danh mục cần xóa.
     */
    @Override
    public void deleteCategory(Long id) {
        try {
            // Kiểm tra xem danh mục có tồn tại không
            CategoryEntity category = categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại nào có ID là: " + id));

            // Kiểm tra xem danh mục có liên kết với thực đơn nào không
            List<MenuEntity> menuEntities = menuRepository.findByCategoryId(id);
            if (!menuEntities.isEmpty()) {
                throw new RuntimeException("Bạn không thể xóa thể loại này vì đang có thực đơn sử dụng nó");
            }

            // Xóa danh mục nếu không có thực đơn nào liên kết
            categoryRepository.delete(category);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
