package com.lera.catalog.dto;

import java.math.BigDecimal;

public record CreateGoodRequest(
        String name,
        String description,
        BigDecimal price,
        String externalId
) {
}
