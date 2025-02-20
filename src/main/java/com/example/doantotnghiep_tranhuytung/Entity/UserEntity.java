package com.example.doantotnghiep_tranhuytung.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * Entity đại diện cho bảng Users trong cơ sở dữ liệu.
 * Lưu trữ thông tin người dùng trong hệ thống.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID tự động tăng
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName; // Họ và tên người dùng

    @Column(unique = true, nullable = false, length = 100)
    private String email; // Email người dùng (duy nhất)

    @Column(unique = true, nullable = false, length = 15)
    private String phone; // Số điện thoại người dùng (duy nhất)

    @Column(nullable = false)
    private String password; // Mật khẩu được mã hóa

    @Column(nullable = false, length = 20)
    private String role; // Vai trò của người dùng (ADMIN, USER,...)

    @Column(nullable = false, updatable = false)
    private Timestamp createdAt; // Thời gian tạo tài khoản

    private Timestamp updatedAt; // Thời gian cập nhật tài khoản

    @Column(columnDefinition = "LONGBLOB")
    private byte[] avatar; // Ảnh đại diện của người dùng lưu dưới dạng byte[]
    @Column(columnDefinition = "TEXT")
    private String imageUrl;
    private boolean enabled;


    /**
     * Getter và Setter cho các thuộc tính của UserEntity.
     * (Dù đã sử dụng Lombok nhưng các phương thức này vẫn được viết trong code).
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
