package com.lera.catalog.dto;

import java.math.BigDecimal;
import java.util.List;

public record GetGoodsListResponse(
        List<GoodDto> goods
) {
    public record GoodDto(
      Long id,
      String name,
      String description,
      BigDecimal price,
      String externalId
    ) {
    }
}
