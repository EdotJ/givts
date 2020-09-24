package com.givts.app.repository;

import com.givts.app.model.Occasion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OccasionRepository extends JpaRepository<Occasion, Long> {

    @Query("select o from Occasion o where o.giftee.id = ?1 and o.giftee.user.id = ?2")
    List<Occasion> findAllByGifteeIdAndUserId(long gifteeId, long userId);
}
