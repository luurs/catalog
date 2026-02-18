package com.lera.catalog.mapper;

import com.lera.catalog.dto.GetGoodsListResponse;
import com.lera.catalog.model.GoodEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoodMapper {

    public GetGoodsListResponse toGoodDtoList(List<GoodEntity> goodEntities) {
        return new GetGoodsListResponse(
                goodEntities.stream().map(this::toGoodDto).toList()
        );
    }

    public GetGoodsListResponse.GoodDto toGoodDto(GoodEntity goodEntity) {
        return new GetGoodsListResponse.GoodDto(
                goodEntity.getId(),
                goodEntity.getName(),
                goodEntity.getDescription(),
                goodEntity.getPrice(),
                goodEntity.getExternalId()
        );
    }
}
