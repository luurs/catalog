package com.lera.catalog.repository;

import com.lera.catalog.model.Good;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoodRepository extends JpaRepository<Good, Long> {
    Optional<Good> findByExternalId(String externalId);
    List<Good> findByExternalIdIn(List<String> externalIds);
}
