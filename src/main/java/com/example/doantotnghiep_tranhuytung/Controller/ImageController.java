package com.example.doantotnghiep_tranhuytung.Controller;

import com.example.doantotnghiep_tranhuytung.Entity.MenuEntity;
import com.example.doantotnghiep_tranhuytung.Entity.UserEntity;
import com.example.doantotnghiep_tranhuytung.Repository.MenuRepository;
import com.example.doantotnghiep_tranhuytung.Repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
@Controller
@RequestMapping("/image")
public class ImageController {
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    public ImageController(MenuRepository menuRepository, UserRepository userRepository) {
        this.menuRepository = menuRepository;
        this.userRepository = userRepository;
    }

    /**
     * Trả về ảnh của món ăn theo ID.
     */
    @GetMapping("/menu/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Optional<MenuEntity> menuOpt = menuRepository.findById(id);

        // Nếu món ăn có ảnh, trả về ảnh đó dưới dạng dữ liệu nhị phân
        if (menuOpt.isPresent() && menuOpt.get().getImage() != null) {
            byte[] imageBytes = menuOpt.get().getImage();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        }

        // Nếu không tìm thấy ảnh, trả về lỗi 404
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<byte[]> getImageUser(@PathVariable Long id) {
        Optional<UserEntity> user = userRepository.findById(id);

        // Nếu món ăn có ảnh, trả về ảnh đó dưới dạng dữ liệu nhị phân
        if (user.isPresent() && user.get().getAvatar() != null) {
            byte[] imageBytes = user.get().getAvatar();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        }

        // Nếu không tìm thấy ảnh, trả về lỗi 404
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
