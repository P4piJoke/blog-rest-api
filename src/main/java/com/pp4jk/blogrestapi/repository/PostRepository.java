package com.pp4jk.blogrestapi.repository;

import com.pp4jk.blogrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
