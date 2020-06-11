package com.example.seeker.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.seeker.entity.Seeker;

@Repository
public interface SeekerRepository extends CrudRepository<Seeker, Long> {
    
    List<Seeker> findByName(String name);
    
}
