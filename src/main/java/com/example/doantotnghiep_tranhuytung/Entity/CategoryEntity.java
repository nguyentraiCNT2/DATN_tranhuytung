package com.example.doantotnghiep_tranhuytung.Entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

/**
 * Entity đại diện cho bảng Categories trong cơ sở dữ liệu.
 * Lưu thông tin danh mục món ăn hoặc dịch vụ.
 */
@Entity
@Table(name = "Categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID khi thêm dữ liệu mới
    private Long id;

    @Column(nullable = false, unique = true) // Tên danh mục không được null và phải là duy nhất
    private String name;

    private Timestamp createdAt; // Thời gian tạo danh mục
    private Timestamp updatedAt; // Thời gian cập nhật danh mục
    private Timestamp deletedAt; // Thời gian xóa mềm (soft delete)

    /**
     * Getter và Setter cho các thuộc tính của CategoryEntity
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }
}
