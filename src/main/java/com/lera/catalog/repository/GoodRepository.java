package com.lera.catalog.repository;

import com.lera.catalog.model.GoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoodRepository extends JpaRepository<GoodEntity, Long> {
    Optional<GoodEntity> findByExternalId(String externalId);
    List<GoodEntity> findByExternalIdIn(List<String> externalIds);
}
