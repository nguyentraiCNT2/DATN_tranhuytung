package com.example.doantotnghiep_tranhuytung.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entity đại diện cho bảng OrderDetails trong cơ sở dữ liệu.
 * Bảng này lưu thông tin chi tiết về các món ăn trong từng đơn hàng.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OrderDetails")
public class OrderDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID tự động tăng
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false) // Liên kết với bảng Order (một đơn hàng có nhiều chi tiết)
    private OrderEntity orderId;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false) // Liên kết với bảng Menu (món ăn được đặt hàng)
    private MenuEntity menuId;

    @Column(nullable = false)
    private int quantity; // Số lượng món ăn trong đơn hàng

    @Column(nullable = false)
    private BigDecimal price; // Giá món ăn tại thời điểm đặt hàng

    /**
     * Getter và Setter cho các thuộc tính của OrderDetailsEntity.
     * (Mặc dù đã sử dụng Lombok, nhưng các phương thức này vẫn được khai báo trong code).
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderEntity getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderEntity orderId) {
        this.orderId = orderId;
    }

    public MenuEntity getMenuId() {
        return menuId;
    }

    public void setMenuId(MenuEntity menuId) {
        this.menuId = menuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
