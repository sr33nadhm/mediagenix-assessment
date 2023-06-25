package com.mediagenix.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediagenix.assessment.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long>{

}
