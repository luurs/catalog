package com.lera.catalog.dto;

import java.util.List;

public record GetGoodsListRequest(
        List<String> externalIds
) {
}
