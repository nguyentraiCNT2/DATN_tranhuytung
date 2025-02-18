package com.example.doantotnghiep_tranhuytung.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Entity đại diện cho bảng Reservations trong cơ sở dữ liệu.
 * Bảng này lưu thông tin về các lượt đặt chỗ của người dùng.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Reservations")
public class ReservationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID tự động tăng
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Một người dùng có thể có nhiều đặt chỗ
    private UserEntity userId;

    @Column(nullable = false)
    private LocalDateTime reservationDate; // Ngày đặt chỗ

    @Column(nullable = false)
    private int reservationNumber; // Số lượng chỗ đặt

    @Column(nullable = false, length = 50)
    private String status; // Trạng thái đặt chỗ (VD: Đã xác nhận, Đang chờ, Đã hủy)

    @CreatedDate
    @Column(updatable = false)
    private Timestamp createdAt; // Thời gian tạo đặt chỗ

    /**
     * Getter và Setter cho các thuộc tính của ReservationsEntity.
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

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
