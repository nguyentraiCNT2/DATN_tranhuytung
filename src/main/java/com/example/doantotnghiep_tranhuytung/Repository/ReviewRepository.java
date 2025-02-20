package com.example.doantotnghiep_tranhuytung.Repository;

import com.example.doantotnghiep_tranhuytung.Entity.ReviewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewsEntity, Long> {
    @Query("select r from ReviewsEntity r WHERE r.menuId.id = :menuId")
    List<ReviewsEntity> findByMenuId(@Param("menuId") Long menuId);
}
