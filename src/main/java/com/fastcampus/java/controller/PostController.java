package com.fastcampus.java.controller;

import com.fastcampus.java.model.SearchParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostController {

    /** Post 방식 정보교환
     * HTML <Form>
     * ajax 검색
     * http post body -> data
     * json, xml, multipart-form / text-plain
     */


    @PostMapping("/postMethod")
    public SearchParam postMethod(@RequestBody SearchParam searchParam){

        return searchParam;
    }

    /** HTTP - PUT/PATCH Method
     * POST와 동일하게 BODY에 데이터가 들어있음
     * 주로 업데이트에 사용(Update)
     */
    /** HTTP - DELETE Method
     * 주로 삭제에 사용(Delete)
     */
    /** HTTP Method 정리 CRUD (http://...)
     * POST - Create : /usr
     * GET - 조회(Read) : /usr/{id}
     * PUT/PATCH - Update : /usr
     * DELETE - Delete : /usr/{1}
     *
     * POST 와 PUT/PATCH는 body에서 동작이 이루어짐
     */

}
