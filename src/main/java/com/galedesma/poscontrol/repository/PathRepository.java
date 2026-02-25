package com.galedesma.poscontrol.repository;

import com.galedesma.poscontrol.entity.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PathRepository extends JpaRepository<Path, Integer> {

    @Query("SELECT p FROM Path p JOIN FETCH p.pos1 JOIN FETCH p.pos2 WHERE p.pos1.id = :id OR p.pos2.id = :id")
    List<Path> findAllPathsConnectedTo(@Param("id") Integer id);

    @Query("SELECT p from Path p WHERE (p.pos1.id = :id1 AND p.pos2.id = :id2) OR (p.pos2.id = :id1 AND p.pos1.id = :id2)")
    Optional<Path> findPathBetween(@Param("id1") Integer id1, @Param("id2") Integer id2);
}
