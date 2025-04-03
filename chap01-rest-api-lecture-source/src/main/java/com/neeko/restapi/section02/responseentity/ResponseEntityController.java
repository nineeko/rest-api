package com.neeko.restapi.section02.responseentity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/entity")
public class ResponseEntityController {

    private List<UserDTO> users;
    public ResponseEntityController() {
        users = new ArrayList<>();
        users.add(new UserDTO(1, "user01", "pass01", "유관순"));
        users.add(new UserDTO(2, "user02", "pass02", "홍길동"));
        users.add(new UserDTO(3, "user03", "pass03", "이순신"));
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers(){
        /* 응답 헤더 설정: JSON 응답이 default이기는 하나 변경이 필요할 경우 Httpheaders 설정 변경  */
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(
                new MediaType("application", "json", StandardCharsets.UTF_8)
        );
        /* 응답 바디 설정 */
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", users);

        /* 응답 메세지 설정 */
        ResponseMessage responseMessage = new ResponseMessage(
                200, "조회 성공", responseMap
        );
        return new ResponseEntity<>(responseMessage, httpHeaders, HttpStatus.OK);
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
    @PostMapping("/users")
    public ResponseEntity<Void> registUser(@RequestBody UserDTO userDTO){

        int lastUserNo = users.get(users.size() - 1).getNo();
        userDTO.setNo(lastUserNo + 1);
        users.add(userDTO);

        return ResponseEntity.
                created(URI.create("/entity/users/" + users.get(users.size() - 1).getNo()))
                .build();
    }

    @PutMapping("/users/{userNo}")  // {userNo}로 경로 변수 지정
    public ResponseEntity<Void> modifyUser(@PathVariable int userNo, @RequestBody UserDTO userDTO) {

        UserDTO foundUser = users.stream()
                .filter(user -> user.getNo() == userNo)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));  // 예외 처리 추가

        foundUser.setPwd(userDTO.getPwd());
        foundUser.setName(userDTO.getName());

        return ResponseEntity.ok().build();  // 수정 후 201(Created) 대신 200(OK) 응답
    }
    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<Void> removeUser(@PathVariable int userNo) {
        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo)
                .findFirst().get();
        users.remove(foundUser);
        return ResponseEntity.noContent().build();
    }

}

