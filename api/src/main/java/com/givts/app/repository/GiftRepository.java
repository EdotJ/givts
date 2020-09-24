package com.givts.app.repository;

import com.givts.app.model.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GiftRepository extends JpaRepository<Gift, Long> {

    @Query("select g from Gift g " +
            "where g.occasion.id = ?1 and g.occasion.giftee.id = ?2 and g.occasion.giftee.user.id = ?3")
    List<Gift> findAllByOccasionIdAndGifteeIdAndUserId(long occasionId, long gifteeId, long userId);
}
