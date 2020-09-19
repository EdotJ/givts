package com.givts.app.repository;

import com.givts.app.model.Giftee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GifteeRepository extends JpaRepository<Giftee, Long> {
}
