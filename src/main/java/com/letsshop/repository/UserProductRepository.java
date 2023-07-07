package com.letsshop.repository;

import com.letsshop.entity.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProductRepository extends JpaRepository<UserProduct, Integer> {
    List<UserProduct> findAllByUserId(int currentUserId);
}
