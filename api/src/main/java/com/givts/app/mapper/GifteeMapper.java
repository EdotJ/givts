package com.givts.app.mapper;

import com.givts.app.domain.Giftee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GifteeMapper {

    Giftee selectGifteeById(long id);
    void insertGiftee(Giftee giftee);
    void updateGifteeById(@Param("g") Giftee giftee, long id);
    void deleteGiftee(long id);
}
