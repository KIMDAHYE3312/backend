package com.energizor.restapi.board.repository;

import com.energizor.restapi.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("select u "+
            "from User u "+
            "where u.userCode= :userCode")
    User findByCode(@Param("userCode") int userCode);
}
