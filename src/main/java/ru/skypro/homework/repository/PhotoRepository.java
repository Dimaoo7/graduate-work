package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.PhotoEntity;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {

    Optional<PhotoEntity> findByAd(AdEntity ad);

    Optional<PhotoEntity> findByAdId(Long id);
}
