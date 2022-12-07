package com.sparta.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.*; // * 이 별표는 constraints를 전부다 사용할수 있다는 뜻!(constraints에 command랑 마우스 갔다대보자)



@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    //- username은  `최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)`로 구성되어야 한다.
    //- password는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성되어야 한다.


    //NotNull Null 불가라는 뜻이다  null 빼고 전부 통과라는 뜻!! Null은 '없다'라기 때문이다
    //@Size(min = 4, max = 10) // minimum and Max

    //@NotNull(message = "숫자 0~9만 구성되어야 합니다.")
    //@Pattern(regexp = "^[0-9]*$") // minimum and Max

    //@NotNull(message ="알파벳 소문자(a~z)로 구성되어야 합니다")
    //@Pattern(regexp="[a-z]") // 알파벳 a에서 z까지 pattern은 이메일, 알파벳

    //처음에 모든 어노테이션이 빨간색이였다
    //해결방법 1. 어노테이션은 private 위로 와야된다 밑으로 내려줬더니 빨간줄..

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z]).{4,10}", message = "아이디 틀렸습니다") // 알파벳 a에서 z까지 pattern은 이메일, 알파벳
    private String username;

    @Column(nullable = false)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,15}", message = "비밀번호 틀렸습니다")
    private String password;


    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}