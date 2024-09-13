package com.example.bloombackend.donelist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloombackend.donelist.entity.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
	List<Photo> findByDoneListId(Long donelistId);
}
