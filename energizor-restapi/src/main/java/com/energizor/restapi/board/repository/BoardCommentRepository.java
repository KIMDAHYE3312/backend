package com.energizor.restapi.board.repository;

import com.energizor.restapi.board.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardCommentRepository extends JpaRepository<BoardComment,Integer> {

    @Query("select bc "+
            "from BoardComment bc "+
            "left join bc.board b "+
            "where b.boardCode= :boardCode")
    BoardComment findByCodeWithComment(@Param("boardCode") int boardCode);

    @Query("select bc "+
            "from BoardComment bc "+
            "where bc.commentCode= :commentCode")
    BoardComment findByCommentCode(@Param("commentCode") int commentCode);
}