package com.example.doantotnghiep_tranhuytung.Service;

import com.example.doantotnghiep_tranhuytung.Entity.UserEntity;

/**
 * Interface định nghĩa các phương thức liên quan đến quản lý người dùng.
 */
public interface UserService {

    /**
     * Xác thực đăng nhập người dùng bằng email và mật khẩu.
     *
     * @param email Địa chỉ email của người dùng.
     * @param password Mật khẩu của người dùng.
     * @return Đối tượng UserEntity nếu đăng nhập thành công.
     * @throws RuntimeException Nếu thông tin đăng nhập không hợp lệ.
     */
    UserEntity dangnhap(String email, String password);

    /**
     * Đăng ký tài khoản người dùng mới.
     *
     * @param userEntity Đối tượng UserEntity chứa thông tin người dùng cần đăng ký.
     * @throws RuntimeException Nếu email đã tồn tại hoặc mật khẩu không hợp lệ.
     */
    void dangky(UserEntity userEntity);
}
