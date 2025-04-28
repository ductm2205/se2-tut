package se2.fit.tut08.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import se2.fit.tut08.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
