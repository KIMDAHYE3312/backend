package com.energizor.restapi.board.service;

import com.energizor.restapi.board.dto.BoardCommentDTO;
import com.energizor.restapi.board.dto.BoardDTO;
import com.energizor.restapi.board.entity.Board;
import com.energizor.restapi.board.entity.BoardComment;
import com.energizor.restapi.board.repository.BoardCommentRepository;
import com.energizor.restapi.board.repository.BoardRepository;
import com.energizor.restapi.common.Criteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final ModelMapper modelMapper;


    @Override
    public Page<BoardDTO> findAllList(Criteria cri, int boardTypeCode) {
        log.info("[BoardService] findAllList start=================");
        int index=cri.getPageNum()-1;
        int count=cri.getAmount();
        Pageable paging= PageRequest.of(index,count, Sort.by("boardCode").descending());

        Page<Board> result=boardRepository.findByBoardTypeCode(boardTypeCode,paging);
        /* Entity -> DTO로 변환 */
//        Page<BoardDTO> boardList=result.map(board->modelMapper.map(board,BoardDTO.class));
        Page<BoardDTO> boardList=result.map(board->{
            BoardDTO boardDTO=modelMapper.map(board,BoardDTO.class);

            // user의 team정보가 있을 경우에만 teamName 설정
            if(board.getUser()!=null && board.getUser().getTeam()!=null) {
                boardDTO.setTeamName(board.getUser().getTeam().getTeamName());
            }
            if(board.getUser()!=null && board.getUser().getTeam()!=null) {
                boardDTO.setDeptName(board.getUser().getTeam().getDepartment().getDeptName());
            }
            return boardDTO;
        });


        log.info("[BoardService] findAllList End======================");
        log.info("boardList : "+boardList);
        return boardList;
    }

    @Override
    public BoardDTO findDetailBoard(int boardCode) {

        log.info("[BoardService] findDetailBoard start ====================");

        Board board= boardRepository.findByCode(boardCode);
        BoardDTO boardDTO=modelMapper.map(board,BoardDTO.class);

        if(board.getUser()!=null && board.getUser().getTeam()!=null) {
            boardDTO.setTeamName(board.getUser().getTeam().getTeamName());
        }
        if(board.getUser()!=null && board.getUser().getTeam()!=null) {
            boardDTO.setDeptName(board.getUser().getTeam().getDepartment().getDeptName());
        }

        log.info("[BoardService] findDetailBoard ======================");

        return boardDTO;
    }

    @Override
    public String register(BoardDTO boardDTO) {
        log.info("[BoardService] register start===============");

        Board insertBoard=modelMapper.map(boardDTO,Board.class);
        Board savedBoard=boardRepository.save(insertBoard);

        log.info("[BoardService] register end ==================");
        return "게시판 글 등록 성공";
    }

    @Transactional
    @Override
    public String update(BoardDTO boardDTO) {

        log.info("[BoardService] update start =================");

        Optional<Board> result= Optional.ofNullable(boardRepository.findByCode(boardDTO.getBoardCode()));

        if(result.isPresent()) {
            Board board=result.get();
            board.title(boardDTO.getTitle());
            board.content(boardDTO.getContent()).build();
        }

        return "게시판 글 수정 성공";
    }

    @Transactional
    @Override
    public Object delete(int boardCode) {
        log.info("[BoardService] delete start =================");

        Optional<Board> boardResult= Optional.ofNullable(boardRepository.findByCode(boardCode));
        LocalDateTime dateTime=LocalDateTime.now();

        if(boardResult.isPresent()) {
            Board board=boardResult.get();

            board.changeBoardDeletedAt(dateTime);
            boardRepository.save(board);


        }
        return "게시글 삭제 성공";
    }

    @Override
    public BoardCommentDTO findComment(int boardCode) {

        log.info("[BoardService] findComment start ======================");
        BoardComment boardComment=boardCommentRepository.findByCodeWithComment(boardCode);
        BoardCommentDTO boardCommentDTO=modelMapper.map(boardComment, BoardCommentDTO.class);


        if(boardComment.getBoard()!=null) {
            boardCommentDTO.setDeptName(boardComment.getBoard().getUser().getTeam().getDepartment().getDeptName());
            boardCommentDTO.setTeamName(boardComment.getBoard().getUser().getTeam().getTeamName());
            boardCommentDTO.setUserName(boardComment.getBoard().getUser().getUserName());
        }



        return boardCommentDTO;
    }

    @Override
    public Object registerComment(BoardCommentDTO boardCommentDTO) {
        log.info("[BoardService] registerComment start===============");

        BoardComment insertBoardComment=modelMapper.map(boardCommentDTO,BoardComment.class);
        BoardComment savedBoard=boardCommentRepository.save(insertBoardComment);

        log.info("[BoardService] register end ==================");
        return "댓글 등록 성공";
    }

    @Transactional
    @Override
    public String updateComment(BoardCommentDTO boardCommentDTO) {

        log.info("[BoardService] updateComment start ================ ");

        Optional<BoardComment> result= Optional.ofNullable(boardCommentRepository.findByCommentCode(boardCommentDTO.getCommentCode()));


        if(result.isPresent()) {
            BoardComment boardComment=result.get();
            boardComment.commentContent(boardCommentDTO.getCommentContent()).build();
        }

        return "댓글 수정 성공";
    }

    @Override
    public String deleteComment(int commentCode) {

        log.info("[BoardService] deleteComment start =================");
        Optional<BoardComment> result= Optional.ofNullable(boardCommentRepository.findByCommentCode(commentCode));

        LocalDateTime dateTime=LocalDateTime.now();
        if(result.isPresent()) {
            BoardComment boardComment=result.get();

            boardComment.changeReplyDeleteDate(dateTime);
            boardCommentRepository.save(boardComment);


        }

        return "댓글 삭제 성공";
    }
}
