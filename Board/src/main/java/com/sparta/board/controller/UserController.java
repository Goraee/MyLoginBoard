package com.sparta.board.controller;

import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.ResponseDto;
import com.sparta.board.dto.SignupRequestDto;
import com.sparta.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        //public 접근제한을 가지고 있는 responseDto을 반환(reutrn)값으로 가지는 이름이
        // signup 메소드입니다 ()안에 들어간게 @RequestBody 어노테이션을 가진 SignupRequestDto 클래스의 signupRequestDto 객체를 매개변수로 두겠다 라는 뜻!!
       return userService.signup(signupRequestDto); //블로그 보고 따라한건데 뭔소리지
       // ( 수정전 return userService.signup(signupRequestDto);
       // 메소드가 실행되구 실행을 할건데 signupRequestDto을 signup메소드에 argument(전달인자)로 넘겨주겠다 )
    }


    @ResponseBody
    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

}

