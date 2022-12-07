package com.sparta.board.controller;


import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.ResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/board")
    public Board createBoard(@RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        return boardService.createBoard(requestDto, request);
    }
    //HttpservletRequest은 http에서 헤더값(토큰) 가져오는거

    @GetMapping("/api/board/{id}") //게시물 조회하는거야 근데 내가 처음에 @RequestBody 사용했는데 쓰면 안됨
    public Board readBoard(@PathVariable Long id) {
        return boardService.readBoard(id);
    }

    @GetMapping("/api/board/list") //전체 게시물 목록을 불러오는겁니다 @RequestBody 사용하면 안됨 id값만 필요한거니까 요청x
    public List<Board> getBoard() {
        return boardService.getBoard();

    }

    @PutMapping("/api/board/{id}") //게시물 수정하는거 변경하는거다 crUd 에서 Update 부분!!
    public ResponseDto updateBoard(@PathVariable Long id, HttpServletRequest request, @RequestBody BoardRequestDto requestDto) {
        return boardService.update(id, request, requestDto);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto deleteBoard(@PathVariable Long id, HttpServletRequest request) {
        return boardService.deleteBoard(id, request);
    }

}
