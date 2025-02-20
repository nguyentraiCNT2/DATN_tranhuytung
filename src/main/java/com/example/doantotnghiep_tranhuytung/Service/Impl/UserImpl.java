package com.example.doantotnghiep_tranhuytung.Service.Impl;

import com.example.doantotnghiep_tranhuytung.Entity.UserEntity;
import com.example.doantotnghiep_tranhuytung.Repository.UserRepository;
import com.example.doantotnghiep_tranhuytung.Service.UserService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Implement class cho UserService, xử lý logic liên quan đến người dùng.
 */
@Service
public class UserImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * Constructor để inject UserRepository.
     */
    public UserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Xử lý đăng nhập người dùng.
     * @param email Email người dùng.
     * @param password Mật khẩu người dùng.
     * @return Thông tin UserEntity nếu đăng nhập thành công.
     */
    @Override
    public UserEntity dangnhap(String email, String password) {
        try {
            // Tìm người dùng theo email, nếu không tìm thấy sẽ ném lỗi
            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

            // Kiểm tra mật khẩu có chính xác không
            if (!user.getPassword().equals(password)) {
                throw new RuntimeException("Mật khẩu không chính xác");
            }
            if (!user.isEnabled()){
                throw new RuntimeException("Tài khoản này đã bị khóa");
            }
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Xử lý đăng ký người dùng.
     * @param userEntity Đối tượng UserEntity chứa thông tin đăng ký.
     */
    @Override
    public void dangky(UserEntity userEntity) {
        try {
            // Kiểm tra email đã tồn tại chưa
            boolean isEmailExists = userRepository.existsByEmail(userEntity.getEmail());
            if (isEmailExists) {
                throw new RuntimeException("Email đã tồn tại");
            }

            // Kiểm tra độ dài mật khẩu tối thiểu 8 ký tự
            if (userEntity.getPassword().length() < 8) {
                throw new RuntimeException("Mật khẩu tối thiểu 8 ký tự");
            }

            // Kiểm tra mật khẩu không chứa khoảng trắng
            if (userEntity.getPassword().contains(" ")) {
                throw new RuntimeException("Mật khẩu không thể chứa khoảng trắng");
            }

            // Thiết lập vai trò mặc định cho người dùng
            userEntity.setRole("USER");
            userEntity.setEnabled(true);

            // Thiết lập thời gian tạo tài khoản
            userEntity.setCreatedAt(Timestamp.from(Instant.now()));

            // Lưu người dùng vào cơ sở dữ liệu
            userRepository.save(userEntity);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
