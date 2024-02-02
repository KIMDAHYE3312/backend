package com.energizor.restapi.users.repository;

import com.energizor.restapi.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
