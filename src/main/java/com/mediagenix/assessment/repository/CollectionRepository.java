package com.mediagenix.assessment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediagenix.assessment.entity.CollectionEntity;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity, Long>{

	Optional<CollectionEntity> findByName(String name);

}
