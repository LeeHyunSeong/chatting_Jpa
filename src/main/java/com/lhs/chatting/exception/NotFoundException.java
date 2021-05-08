package com.lhs.chatting.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private HttpStatus status;

    public NotFoundException(Object type, String paramMsg) {
        super(String.format("Can not found %s entity (%s)", type.getClass().getSimpleName(), paramMsg));
        this.status = HttpStatus.NOT_FOUND;
    }
}
