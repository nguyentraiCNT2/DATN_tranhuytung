package com.example.doantotnghiep_tranhuytung.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Entity đại diện cho bảng Menus trong cơ sở dữ liệu.
 * Lưu thông tin về các món ăn hoặc sản phẩm trong thực đơn.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Menus")
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID tự động tăng
    private Long id;

    @Column(nullable = false) // Tên món ăn không được null
    private String name;

    private String description; // Mô tả món ăn

    @Column(nullable = false)
    private BigDecimal price; // Giá món ăn

    @Column(columnDefinition = "LONGBLOB") // Lưu ảnh dưới dạng byte[]
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false) // Liên kết với bảng Categories
    private CategoryEntity categoryId;

    private String imageUrl; // Đường dẫn ảnh

    private Timestamp createdAt; // Thời gian tạo món ăn
    private Timestamp updatedAt; // Thời gian cập nhật món ăn
    private Timestamp deleteAt; // Thời gian xóa mềm món ăn

    /**
     * Getter và Setter cho các thuộc tính của MenuEntity.
     * (Mặc dù đã sử dụng Lombok, nhưng các phương thức này vẫn được khai báo trong code).
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public CategoryEntity getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(CategoryEntity categoryId) {
        this.categoryId = categoryId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Timestamp deleteAt) {
        this.deleteAt = deleteAt;
    }
}
