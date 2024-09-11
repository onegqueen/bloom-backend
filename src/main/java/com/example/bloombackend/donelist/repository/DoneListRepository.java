package com.example.bloombackend.donelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloombackend.donelist.entity.DoneList;

public interface DoneListRepository extends JpaRepository<DoneList, Long> {
}
