package com.energizor.restapi.board.controller;

import com.energizor.restapi.board.dto.BoardDTO;
import com.energizor.restapi.board.service.BoardService;
import com.energizor.restapi.common.Criteria;
import com.energizor.restapi.common.PageDTO;
import com.energizor.restapi.common.PagingResponseDTO;
import com.energizor.restapi.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
@CrossOrigin("*")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> findAllList(@RequestParam(name = "boardTypeCode") int boardTypeCode,
                                                   @RequestParam(name = "offset", defaultValue = "1") String offset) {
        /* offset : 특정 페이지의 데이터를 조회할 때 사용
                    특정 페이지의 시작 인덱스를 나타낸다. 페이지의 크기가 10이고 offset이 1이면 1번부터 10번까지의 데이터를 가져오고 offset이 2이면
                    11번부터 20번까지의 데이터를 가져온다.
         */
        log.info("[BoardController] findAllList ==========");
        log.info("[BoardController] boardTypeCode : "+boardTypeCode);
        log.info("[BoardController] offset : "+offset);

        Criteria cri=new Criteria(Integer.valueOf(offset),10);
        PagingResponseDTO pagingResponseDTO=new PagingResponseDTO();

        Page<BoardDTO> boardList=boardService.findAllList(cri,boardTypeCode);
        pagingResponseDTO.setData(boardList.getContent());
        pagingResponseDTO.setPageInfo(new PageDTO(cri,(int)boardList.getTotalElements()));

        log.info("[BoardController] findAllList end===========");
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"조회 성공",pagingResponseDTO));


    }

    @GetMapping("list/detail/{boardCode}")
    public ResponseEntity<ResponseDTO> findDetailBoard(@PathVariable int boardCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"게시판 상세글 조회 성공",boardService.findDetailBoard(boardCode)));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody BoardDTO boardDTO) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"게시글 등록 성공",boardService.register(boardDTO)));

    }

    @PatchMapping("/update")
    public ResponseEntity<ResponseDTO> update(@RequestBody BoardDTO boardDTO) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"게시글 수정 성공",boardService.update(boardDTO)));
    }

    @PatchMapping("/delete/{boardCode}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable int boardCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"게시글 삭제 성공",boardService.delete(boardCode)));

    }

    @GetMapping("/comment/{boardCode}")
    public ResponseEntity<ResponseDTO> findComment(@PathVariable int boardCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"댓글 조회 성공",boardService.findComment(boardCode)));
    }


}