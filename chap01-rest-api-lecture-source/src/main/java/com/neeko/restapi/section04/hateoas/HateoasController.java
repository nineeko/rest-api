package com.neeko.restapi.section04.hateoas;


import com.neeko.restapi.section02.responseentity.ResponseMessage;
import com.neeko.restapi.section02.responseentity.UserDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/hateoas")
public class HateoasController {
    private List<UserDTO> users;
    public HateoasController() {
        users = new ArrayList<>();
        users.add(new UserDTO(1, "user01", "pass01", "유관순"));
        users.add(new UserDTO(2, "user02", "pass02", "홍길동"));
        users.add(new UserDTO(3, "user03", "pass03", "이순신"));
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers(){
        /* Hateoas 설정 */
        List<EntityModel<UserDTO>> userWithRel = users.stream().map(
                user ->
                        EntityModel.of(
                                user,
                                linkTo(methodOn(HateoasController.class).findUserByNo(user.getNo())).withSelfRel(),
                                linkTo(methodOn(HateoasController.class).findAllUsers()).withRel("users")
                        )
        ).toList();
        /* 응답 바디 설정 */
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", userWithRel);

        /* 응답 메세지 설정 */
        ResponseMessage responseMessage = new ResponseMessage(
                200, "조회 성공", responseMap
        );
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo){
        /* 응답 헤더 설정: JSON 응답이 default이기는 하나 변경이 필요할 경우 Httpheaders 설정 변경  */
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(
                new MediaType("application", "json", StandardCharsets.UTF_8)
        );
        /* 응답 바디 설정 */
        Map<String, Object> responseMap = new HashMap<>();
        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo)
                .findFirst().get();
        responseMap.put("users", foundUser);

        /* 응답 메세지 설정 */
        ResponseMessage responseMessage = new ResponseMessage(
                200, "조회 성공", responseMap
        );
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(new ResponseMessage(200,"조회 성공", responseMap));
    }
}
