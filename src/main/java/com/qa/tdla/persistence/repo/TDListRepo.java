package com.qa.tdla.persistence.repo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.tdla.persistence.domain.TdList;

@Repository
@Profile({"dev", "prod"})
public interface TdListRepo extends JpaRepository<TdList, Long> {

}