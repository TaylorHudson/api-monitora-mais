package br.com.pj2.back.dataprovider.database.repository;

import br.com.pj2.back.dataprovider.database.entity.DisciplineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DisciplineRepository extends JpaRepository<DisciplineEntity, Long> {
    Optional<DisciplineEntity> findByName(String name);
}
