package com.example.doantotnghiep_tranhuytung.Service;

import com.example.doantotnghiep_tranhuytung.Entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface định nghĩa các phương thức liên quan đến quản lý thể loại (Category).
 */
public interface CategoryService {

    /**
     * Tìm danh sách thể loại theo tên, hỗ trợ phân trang.
     *
     * @param categoryName Tên thể loại cần tìm kiếm.
     * @param pageable Thông tin phân trang.
     * @return Danh sách thể loại phù hợp dưới dạng Page.
     */
    Page<CategoryEntity> getByName(String categoryName, Pageable pageable);

    /**
     * Lấy danh sách tất cả thể loại có hỗ trợ phân trang.
     *
     * @param pageable Thông tin phân trang.
     * @return Danh sách tất cả thể loại dưới dạng Page.
     */
    Page<CategoryEntity> getAllCategory(Pageable pageable);

    /**
     * Lấy danh sách tất cả thể loại không phân trang.
     *
     * @return Danh sách tất cả thể loại.
     */
    List<CategoryEntity> getAllCategory();

    /**
     * Tìm thể loại theo ID.
     *
     * @param id ID của thể loại.
     * @return Đối tượng CategoryEntity nếu tìm thấy.
     */
    CategoryEntity getCategoryById(Long id);

    /**
     * Lưu một thể loại mới vào cơ sở dữ liệu.
     *
     * @param category Đối tượng CategoryEntity cần lưu.
     * @return Thể loại sau khi đã được lưu.
     */
    CategoryEntity saveCategory(CategoryEntity category);

    /**
     * Cập nhật thông tin của một thể loại.
     *
     * @param category Đối tượng CategoryEntity chứa thông tin cập nhật.
     * @return Thể loại sau khi đã được cập nhật.
     */
    CategoryEntity updateCategory(CategoryEntity category);

    /**
     * Xóa một thể loại theo ID.
     *
     * @param id ID của thể loại cần xóa.
     */
    void deleteCategory(Long id);
}
