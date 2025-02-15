package com.example.doantotnghiep_tranhuytung.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * Entity đại diện cho bảng Revenue trong cơ sở dữ liệu.
 * Bảng này lưu thông tin về doanh thu của hệ thống.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Revenue")
public class RevenueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID tự động tăng
    private Long id;

    @Column(nullable = false, updatable = false)
    private Timestamp createdAt; // Thời gian tạo bản ghi doanh thu

    @Column(nullable = false)
    private int totalOrders; // Tổng số đơn hàng

    @Column(nullable = false)
    private int totalRevenue; // Tổng doanh thu

    /**
     * Getter và Setter cho các thuộc tính của RevenueEntity.
     * (Dù đã sử dụng Lombok nhưng các phương thức này vẫn được viết trong code).
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
