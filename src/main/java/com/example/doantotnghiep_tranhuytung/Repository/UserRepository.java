package com.example.doantotnghiep_tranhuytung.Repository;

import com.example.doantotnghiep_tranhuytung.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho thực thể UserEntity, giúp thao tác với bảng Users trong cơ sở dữ liệu.
 * Kế thừa JpaRepository để sử dụng các phương thức CRUD mặc định.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Tìm kiếm người dùng theo địa chỉ email.
     *
     * @param email Email của người dùng cần tìm.
     * @return      Một Optional chứa UserEntity nếu tìm thấy, nếu không sẽ trả về Optional rỗng.
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Kiểm tra xem email đã tồn tại trong hệ thống hay chưa.
     *
     * @param email Email cần kiểm tra.
     * @return      true nếu email đã tồn tại, false nếu chưa.
     */
    boolean existsByEmail(String email);
}
