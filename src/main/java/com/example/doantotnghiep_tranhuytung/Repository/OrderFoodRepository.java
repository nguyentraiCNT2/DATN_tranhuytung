package com.example.doantotnghiep_tranhuytung.Repository;

import com.example.doantotnghiep_tranhuytung.Entity.OrderFoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderFoodRepository extends JpaRepository<OrderFoodEntity, Long> {
    @Query("select orf from OrderFoodEntity orf WHERE orf.user.id = :userId ")
    List<OrderFoodEntity> findByUser(@Param("userId") Long userId);
}
