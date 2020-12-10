package com.qa.tdla.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.tdla.persistence.domain.TDList;

@Repository
public interface TDListRepo extends JpaRepository<TDList, Long> {

}