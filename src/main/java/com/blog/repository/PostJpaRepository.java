package com.blog.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blog.vo.Post;

@Repository("PostJpaRepository")
public interface PostJpaRepository extends JpaRepository<Post, Serializable> {
    Post findOneById(Long id);

    List<Post> findAllByOrderByUpdtDateDesc();
    List<Post> findAllByOrderByUpdtDateAsc();
    List<Post> findByOrderByRegDateDesc();
    List<Post> findByTitleContainingOrderByUpdtDateDesc(String query);
    @Query("SELECT p FROM Post p WHERE p.content LIKE %:query%")
    List<Post> findPostLikeContent(@Param("query") String query);
}
