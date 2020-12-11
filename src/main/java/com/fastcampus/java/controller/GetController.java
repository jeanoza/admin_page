package com.fastcampus.java.controller;

import com.fastcampus.java.model.SearchParam;
import com.fastcampus.java.model.network.Header;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api") //받을 주소 - localhost:8080/api
public class GetController {

    @RequestMapping(method = RequestMethod.GET, path = "/getMethod") // localhost:8080/api/getMethod
    public String getRequest() {
        return "Hi getMethod";
    }

    // @RequestMapping(method = RequestMethod.GET, path="/getParameter")와 같은 의미
    @GetMapping("/getParameter") //localhost:8080/api/getParameter?id=1234&password=abcd
    public String GetParameter(@RequestParam String id, @RequestParam(name = "password") String pwd) {
        String password = "bbbb"; // pwd와 GetParameter 클래스의 지역변수 password를 매핑하는 방법

        System.out.println("id : " + id);
        System.out.println("password : " + pwd);

        return id + pwd;
    }

    //localhost:8080/api/getMultiParameter?account=abcd&email=study@gmail.com&page=10
    @GetMapping("/getMultiParameter")
    public SearchParam getMultiParameter(SearchParam searchParam) {
        System.out.println(searchParam.getAccount());
        System.out.println(searchParam.getEmail());
        System.out.println(searchParam.getPage());

        //파라미터로 넣었던 클래스를 return하면
        // Json 형태 - { k1 : v1, k1:v1 ...}로 출력
        // {"account" : "", "email" : "", "page" : 0}
        return searchParam;
    }
    @GetMapping("/header")
    public Header getHeader() {
        return Header.builder().resultCode("OK").description("OK").build();
    }
}
