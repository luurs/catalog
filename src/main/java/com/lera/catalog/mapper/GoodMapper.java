package com.lera.catalog.mapper;

import com.lera.catalog.dto.GetGoodsListResponse;
import com.lera.catalog.model.Good;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoodMapper {

    public GetGoodsListResponse toGoodDtoList(List<Good> goods) {
        return new GetGoodsListResponse(
                goods.stream().map(this::toGoodDto).toList()
        );
    }

    public GetGoodsListResponse.GoodDto toGoodDto(Good good) {
        return new GetGoodsListResponse.GoodDto(
                good.getId(),
                good.getName(),
                good.getDescription(),
                good.getPrice(),
                good.getExternalId()
        );
    }
}
