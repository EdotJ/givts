package com.givts.app.mapper;

import com.givts.app.domain.Occasion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OccasionMapper {

    Occasion selectOccasionById(long id);
    void insertOccasion(Occasion occasion);
    void updateOccasionById(@Param("o") Occasion occasion, long id);
    void deleteOccasion(long id);
}
