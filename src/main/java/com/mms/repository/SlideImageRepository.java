package com.mms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mms.model.SlideImage;

@Repository
public interface SlideImageRepository extends JpaRepository<SlideImage, Long> {

}
