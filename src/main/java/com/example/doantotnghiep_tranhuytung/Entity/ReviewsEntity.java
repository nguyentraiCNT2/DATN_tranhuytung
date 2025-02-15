package com.example.doantotnghiep_tranhuytung.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * Entity đại diện cho bảng Reviews trong cơ sở dữ liệu.
 * Lưu trữ đánh giá của người dùng về các món ăn trong menu.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Reviews")
public class ReviewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID tự động tăng
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Liên kết với bảng User (người dùng đánh giá)
    private UserEntity userId;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false) // Liên kết với bảng Menu (món ăn được đánh giá)
    private MenuEntity menuId;

    @Column(nullable = false)
    private int rating; // Điểm đánh giá (1-5)

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // Nội dung đánh giá

    @Column(nullable = false, updatable = false)
    private Timestamp createdAt; // Thời gian tạo đánh giá

    /**
     * Getter và Setter cho các thuộc tính của ReviewsEntity.
     * (Dù đã sử dụng Lombok nhưng các phương thức này vẫn được viết trong code).
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public MenuEntity getMenuId() {
        return menuId;
    }

    public void setMenuId(MenuEntity menuId) {
        this.menuId = menuId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
