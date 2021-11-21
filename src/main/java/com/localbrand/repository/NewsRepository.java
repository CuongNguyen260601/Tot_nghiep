package com.localbrand.repository;

import com.localbrand.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.relational.core.sql.In;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {

    Page<News> findAllByIdStatus(Integer idStatus, Pageable page);

    Page<News> findAllByTitleLike( String title,Pageable pageable);

    Page<News> findAllByTitleLikeAndIdStatus(String title, Integer idStatus, Pageable pageable);

    Optional<News> findByIdNewAndIdStatus(Long idNew, Integer idStatus);
}