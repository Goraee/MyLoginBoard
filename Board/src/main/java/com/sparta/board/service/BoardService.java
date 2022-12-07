package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.ResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.User;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Transactional
    public Board createBoard(BoardRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request); //request안에 있는 토큰 가져온다
        Claims claims; // 값 저장하는 인터페이스

        if (token != null) {

            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }


            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            ); //굳이 사용자 정보 알 필요 없으니까 왜냐하면 토큰이 유효한지 알면 되니까
            return boardRepository.save(new Board(requestDto, user.getUsername(), user.getId()));

        } else {
            return null;
        }
    }

    @Transactional
    public Board readBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        boardRepository.save(board);
        return board;
    }

    @Transactional(readOnly = true)
    public List<Board> getBoard() {
        return boardRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public ResponseDto update(Long id, HttpServletRequest request, BoardRequestDto requestDto) { //<-이거 매개변수다 parameter
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {

            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }


            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            ); //굳이 사용자 정보 알 필요 없으니까 왜냐하면 토큰이 유효한지 알면 되니까

            Board board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 게시글 입니다.")
            );
            board.update(requestDto);
        }

        return new ResponseDto("게시물 수정 성공",200);

//        System.out.println(requestDto.getPassword());
//        System.out.println(board.getPassword()); // 어디서 데이터가 안들어갔는지 들어갔는지 확인하려구 이거 쓰는거
        // 근데 왜 sout 하면 이거는 print니까

    }


    @Transactional
    public ResponseDto deleteBoard(Long id, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request); //request안에 있는 토큰 가져온다
        Claims claims; // 값 저장하는 인터페이스

        if (token != null) {

            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }


            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            ); //굳이 사용자 정보 알 필요 없으니까 왜냐하면 토큰이 유효한지 알면 되니까

            Board board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 게시글 입니다.")
            );

            boardRepository.delete(board);

        }
            return new ResponseDto("삭제 성공",200);


    }
}



