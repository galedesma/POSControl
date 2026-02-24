package com.galedesma.poscontrol.repository;

import com.galedesma.poscontrol.entity.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PathRepository extends JpaRepository<Path, Integer> {

    @Query("SELECT p FROM Path p JOIN FETCH p.pos1 JOIN FETCH p.pos2 WHERE p.pos1.id = :id OR p.pos2.id = :id")
    List<Path> findAllPathsConnectedTo(@Param("id") Integer id);
}
