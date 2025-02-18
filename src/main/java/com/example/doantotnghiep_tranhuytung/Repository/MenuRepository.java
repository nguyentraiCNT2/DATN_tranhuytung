package com.example.doantotnghiep_tranhuytung.Repository;

import com.example.doantotnghiep_tranhuytung.Entity.MenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Repository cho thực thể MenuEntity, xử lý truy vấn dữ liệu liên quan đến thực đơn.
 * Kế thừa JpaRepository để sử dụng các phương thức CRUD có sẵn.
 */
@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    @Query("select m from MenuEntity m WHERE m.deleteAt is null")
    List<MenuEntity> findAllByDeleteAtIsNull();

    /**
     * Truy vấn danh sách món ăn theo ID danh mục có phân trang.
     *
     * @param categoryId ID của danh mục.
     * @param pageable   Đối tượng phân trang giúp chia nhỏ kết quả.
     * @return           Danh sách món ăn thuộc danh mục được phân trang.
     */
    @Query("SELECT m FROM MenuEntity m WHERE m.categoryId.id = :categoryId")
    List<MenuEntity> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    /**
     * Truy vấn danh sách món ăn theo ID danh mục (không phân trang).
     *
     * @param categoryId ID của danh mục.
     * @return           Danh sách món ăn thuộc danh mục.
     */
    @Query("SELECT m FROM MenuEntity m WHERE m.categoryId.id = :categoryId")
    List<MenuEntity> findByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * Truy vấn tìm kiếm món ăn với nhiều điều kiện lọc như:
     * - Tên món ăn (tìm kiếm gần đúng, không phân biệt chữ hoa/thường).
     * - Danh mục món ăn.
     * - Khoảng giá (min - max).
     * - Khoảng thời gian tạo món ăn.
     * - Chỉ lấy món ăn chưa bị xóa (deleteAt IS NULL).
     *
     * @param name       Tên món ăn cần tìm kiếm (tùy chọn, có thể null).
     * @param categoryId ID của danh mục (tùy chọn, có thể null).
     * @param minPrice   Giá tối thiểu (tùy chọn, có thể null).
     * @param maxPrice   Giá tối đa (tùy chọn, có thể null).
     * @param startDate  Ngày tạo từ (tùy chọn, có thể null).
     * @param endDate    Ngày tạo đến (tùy chọn, có thể null).
     * @param pageable   Đối tượng phân trang.
     * @return           Danh sách các món ăn phù hợp với bộ lọc dưới dạng Page.
     */
    @Query("SELECT m FROM MenuEntity m WHERE " +
            "(:name IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:categoryId IS NULL OR m.categoryId.id = :categoryId) AND " +
            "(:minPrice IS NULL OR m.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR m.price <= :maxPrice) AND " +
            "(:startDate IS NULL OR m.createdAt >= :startDate) AND " +
            "(:endDate IS NULL OR m.createdAt <= :endDate) AND " +
            "m.deleteAt IS NULL")
    Page<MenuEntity> searchMenus(
            @Param("name") String name,
            @Param("categoryId") Long categoryId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("startDate") Timestamp startDate,
            @Param("endDate") Timestamp endDate,
            Pageable pageable
    );
}
