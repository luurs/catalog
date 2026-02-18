package com.lera.catalog.service;

import com.lera.catalog.model.Good;
import com.lera.catalog.repository.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class GoodService {

    public final GoodRepository goodRepository;

    @Autowired
    public GoodService(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    @Transactional
    public Good add(String name, String description, BigDecimal price, String externalId) {
        var findGood = goodRepository.findByExternalId(externalId);
        if (findGood.isPresent()) {
            var good = findGood.get();
            good.setName(name);
            good.setDescription(description);
            good.setPrice(price);
            return good;
        } else {
            var savedGood = new Good(name, description, price, externalId);
            goodRepository.save(savedGood);
            return savedGood;
        }
    }

    public List<Good> findByExternalId(List<String> externalIds) {
        return goodRepository.findByExternalIdIn(externalIds);
    }
}
