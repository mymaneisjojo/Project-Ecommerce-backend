package com.example.vmo1.repository;

import com.example.vmo1.model.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Page<Orders> findAllByAccountId(Pageable pageable, Long id);
}
