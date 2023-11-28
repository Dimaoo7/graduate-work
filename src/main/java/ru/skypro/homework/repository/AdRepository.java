package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<AdEntity, Long> {

    List<AdEntity> findByAuthor(UserEntity author);
}
