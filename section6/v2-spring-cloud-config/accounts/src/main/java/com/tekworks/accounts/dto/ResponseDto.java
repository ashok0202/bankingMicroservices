package com.tekworks.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Response",
        description = "Schema  to hold  successfully response infomration"
)
public class ResponseDto {

    private String status;
    private String message;

    public ResponseDto() {
    }

    public ResponseDto(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
