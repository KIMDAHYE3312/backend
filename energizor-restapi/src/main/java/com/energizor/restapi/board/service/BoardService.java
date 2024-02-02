package com.energizor.restapi.board.service;

import com.energizor.restapi.board.dto.BoardCommentDTO;
import com.energizor.restapi.board.dto.BoardDTO;
import com.energizor.restapi.board.entity.Board;
import com.energizor.restapi.common.Criteria;
import org.springframework.data.domain.Page;

public interface BoardService {


    Page<BoardDTO> findAllList(Criteria cri, int boardTypeCode);

    Object findDetailBoard(int boardCode);

    String register(BoardDTO boardDTO);

    String update(BoardDTO boardDTO);


    Object delete(int boardCode);

    BoardCommentDTO findComment(int boardCode);
}
