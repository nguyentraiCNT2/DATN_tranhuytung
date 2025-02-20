package com.example.doantotnghiep_tranhuytung.Controller.SuperAdmin;

import com.example.doantotnghiep_tranhuytung.Entity.UserEntity;
import com.example.doantotnghiep_tranhuytung.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/superadmin/user")
public class UserManamentController {
    private final UserRepository userRepository;

    public UserManamentController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getAll(Model model,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "20") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<UserEntity> userDTOPage = userRepository.findAll(pageable);
            model.addAttribute("userDTOPage", userDTOPage.getContent());
            model.addAttribute("totalPages", userDTOPage.getTotalPages());
            model.addAttribute("currentPage", page);
            return "Admin/Users/List-User";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Admin/Users/List-User";
        }
    }
    @GetMapping("/detail/{id}")
    public String getById(@PathVariable Long id, Model model) {
        try {
            UserEntity userDTO = userRepository.findById(id).get();
            model.addAttribute("user", userDTO);
            return "Admin/Users/detail";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Admin/Users/detail";
        }
    }
    @GetMapping("/updateRole/{id}")
    public String updateRole(@PathVariable Long id, Model model) {
        try {
            UserEntity userDTO = userRepository.findById(id).get();
            model.addAttribute("user", userDTO);
            return "Admin/Users/detail";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Admin/Users/detail";
        }
    }
    // Cập nhật vai trò người dùng (ví dụ: từ form)
    // POST /admin/users/{id}/updateRole?role=ADMIN
    @PostMapping("/updateRole/{id}")
    public String updateRole(@PathVariable("id") Long userId,
                             @RequestParam("role") String role,
                             Model redirectAttributes) {
        try {
            UserEntity userDTO = userRepository.findById(userId).get();
            userDTO.setRole(role);
            userRepository.save(userDTO);
            redirectAttributes.addAttribute("message", "Cập nhật vai trò thành công.");
            return "redirect:/superadmin/user";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/superadmin/user"; // Chuyển hướng về trang danh sách người dùng

        }
    }

    // Hủy kích hoạt người dùng (Unactive)
    // POST /admin/users/{id}/deactivate
    @GetMapping("/deactivate/{id}")
    public String unActiveUser(@PathVariable("id") Long userId,
                               Model redirectAttributes) {
        try {
            UserEntity userDTO = userRepository.findById(userId).get();
            userDTO.setEnabled(false);
            userRepository.save(userDTO);
            redirectAttributes.addAttribute("message", "Người dùng đã bị hủy kích hoạt.");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", e.getMessage());
        }
        return "redirect:/superadmin/user";
    }

    // Kích hoạt người dùng (Active)
    // POST /admin/users/{id}/activate
    @GetMapping("/activate/{id}")
    public String activeUser(@PathVariable("id") Long userId,
                             Model redirectAttributes) {
        try {
            UserEntity userDTO = userRepository.findById(userId).get();
            userDTO.setEnabled(true);
            userRepository.save(userDTO);
            redirectAttributes.addAttribute("message", "Người dùng đã được kích hoạt.");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", e.getMessage());
        }
        return "redirect:/superadmin/user";
    }
}
