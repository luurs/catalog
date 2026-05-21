package com.lera.catalog.service;

import com.lera.catalog.dto.orders.GoodsInvalidateMessage;
import com.lera.catalog.dto.orders.GoodsItem;
import com.lera.catalog.model.GoodEntity;
import com.lera.catalog.repository.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class GoodService {

    private final GoodRepository goodRepository;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public GoodService(GoodRepository goodRepository, KafkaProducerService kafkaProducerService) {
        this.goodRepository = goodRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Transactional
    public GoodEntity add(String name, String description, BigDecimal price, String externalId) {
        GoodEntity result;
        var findGood = goodRepository.findByExternalId(externalId);
        if (findGood.isPresent()) {
            var good = findGood.get();
            good.setName(name);
            good.setDescription(description);
            good.setPrice(price);
            result = good;
        } else {
            var savedGood = new GoodEntity(name, description, price, externalId);
            goodRepository.save(savedGood);
            result = savedGood;
        }

        var message = new GoodsInvalidateMessage(
                List.of(new GoodsItem(result.getId(),
                        result.getExternalId())
                )
        );
        kafkaProducerService.sendInvalidateMessage(message);

        return result;
    }

    public List<GoodEntity> findByExternalId(List<String> externalIds) {
        return goodRepository.findByExternalIdIn(externalIds);
    }
}
