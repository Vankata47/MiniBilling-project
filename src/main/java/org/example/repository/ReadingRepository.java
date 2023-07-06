package org.example.repository;

import org.example.entity.Reading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, Long> {
    List<Reading> findAllByReferenceNumber(String referenceNumber);

    List<Reading> findAllByReferenceNumberOrderByDateTimeDesc(String referenceNumber);
}

