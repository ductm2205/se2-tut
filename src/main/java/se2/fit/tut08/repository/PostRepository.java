package se2.fit.tut08.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import se2.fit.tut08.model.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findByUserUsername(String username, Pageable pageable);

    Optional<Post> findByIdAndUserUsername(Integer id, String username);

    Optional<Post> findBySlug(String slug);
}