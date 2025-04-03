package com.neeko.restapi.section01.response;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/* @RestController: 모든 핸들러 메소드에 @ResponseBody가 적용된 것과 같다. */
@RestController
@RequestMapping("/response")
public class ResponseController {

    /* 1. 문자열 응답 */
    @GetMapping("/hello")
    public String helloWorld(){ return "Hello World!"; }

    /* 2. Object 응답 */
    @GetMapping("/message")
    public Message getMessage(){
        return new Message(200,"메세지를 응답합니다.");
    }
    /* 3. List 응답*/
    @GetMapping("/list")
    public List<String> getList(){
        return List.of(new String[] {"사과","바나나", "복숭아"});
    }

    /* 4. Map 응답 */
    @GetMapping("/map")
    public Map<Integer, String> getMap(){
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message(200, "정상 응답"));
        messageList.add(new Message(404, "페이지를 찾을 수 없습니다."));
        messageList.add(new Message(500, "개발자의 잘못입니다."));
        return messageList.stream()
                .collect(Collectors.toMap(Message::getHttpStatusCode, Message::getMessage));

    }
    /* 5. file 응답 */
    @GetMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage() throws IOException {
        return getClass().getResourceAsStream("/images/spring.png").readAllBytes();
    }
    /* 6. ResponseEntity 응답 */
    @GetMapping("/entity")
    public ResponseEntity<Message> getEntity(){
        return ResponseEntity.ok(new Message(200,"정상 수행"));
    }

}
