package com.givts.app.mapper;

import com.givts.app.domain.Gift;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GiftMapper {
    Gift selectGiftById(long id);
    void insertGift(Gift gift);
    void updateGiftById(@Param("g") Gift gift, long id);
    void deleteGift(long id);
}
