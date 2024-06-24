package com.green.greengram.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ResultDto<T> {
    @Schema(example = "2")
    private HttpStatus statusCode;
    @Schema(example = "메시지")
    private String resultMsg;
    private T resultData;

    public static <T> ResultDto<T> returnDto(HttpStatus status, String msg, T data) {
        return ResultDto.<T>builder()
                .statusCode(status)
                .resultMsg(msg)
                .resultData(data)
                .build();
    }

}
