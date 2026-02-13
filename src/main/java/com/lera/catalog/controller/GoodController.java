package com.lera.catalog.controller;

import com.lera.catalog.dto.CreateGoodRequest;
import com.lera.catalog.dto.CreateGoodResponse;
import com.lera.catalog.dto.GetGoodsListRequest;
import com.lera.catalog.dto.GetGoodsListResponse;
import com.lera.catalog.mapper.GoodMapper;
import com.lera.catalog.service.GoodService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
@AllArgsConstructor
public class GoodController {

    //services
    public final GoodService goodService;
    public final GoodMapper goodMapper;

    @PostMapping("/createGood")
    public CreateGoodResponse addGood(@RequestBody CreateGoodRequest request) {
        var addedGood = goodService.add(request.name(), request.description(), request.price(), request.externalId());
        return new CreateGoodResponse(addedGood.getId());
    }

    @PostMapping("/getGoodsList")
    public GetGoodsListResponse getGoodsList(@RequestBody GetGoodsListRequest getGoodsListRequest) {
        var goods = goodService.findById(getGoodsListRequest.ids());
        return goodMapper.toGoodDtoList(goods);
    }
}
