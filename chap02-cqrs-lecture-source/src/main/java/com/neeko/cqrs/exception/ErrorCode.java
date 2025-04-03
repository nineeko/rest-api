package com.neeko.cqrs.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum ErrorCode {
    // 상품 관련 오류
    PRODUCT_NOT_FOUND("10001", "해당 상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 파일 관련 오류
    FILE_SAVE_ERROR("20001","파일 저장에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_DELETE_ERROR("20002","파일 삭제에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 입력 값 검증 오류
    VALIDATION_ERROR("40001", "입력 값 검증 오류입니다.", HttpStatus.BAD_REQUEST),
    // 그 외 기타 오류
    INTERNAL_SERVER_ERROR("50000","내부 서버 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR)
    ;

    private final String code; // 서비스에서 정의
    private final String message;  // 에러 내용 담음
    private final HttpStatus httpStatus;

}
