package com.example.doantotnghiep_tranhuytung.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Entity đại diện cho bảng Orders trong cơ sở dữ liệu.
 * Bảng này lưu thông tin về các đơn đặt hàng của người dùng.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID tự động tăng
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Mỗi đơn hàng thuộc về một người dùng
    private UserEntity userId;

    @Column(nullable = false, updatable = false)
    private Timestamp orderDate; // Ngày đặt hàng

    @Column(nullable = false)
    private BigDecimal totalPrice; // Tổng giá trị đơn hàng

    @Column(nullable = false, length = 50)
    private String orderStatus; // Trạng thái đơn hàng (VD: Chờ xử lý, Đang giao, Đã hoàn thành)

    private Timestamp createdAt; // Thời gian tạo đơn hàng

    private Timestamp updatedAt; // Thời gian cập nhật đơn hàng

    /**
     * Getter và Setter cho các thuộc tính của OrderEntity.
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

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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
}
