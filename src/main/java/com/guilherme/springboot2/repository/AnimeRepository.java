package com.guilherme.springboot2.repository;

import com.guilherme.springboot2.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

    List<Anime> findAllByName(String name);
}
