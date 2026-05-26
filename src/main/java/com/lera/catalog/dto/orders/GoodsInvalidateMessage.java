package com.lera.catalog.dto.orders;

import java.util.List;

public record GoodsInvalidateMessage(
        List<GoodsItem> goods
) {
}
