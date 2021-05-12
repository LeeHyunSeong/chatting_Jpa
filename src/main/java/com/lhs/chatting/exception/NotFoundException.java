package com.lhs.chatting.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final HttpStatus status;

    public <T> NotFoundException(Class<T> clazz, String paramMsg) {
        super(String.format("Can not found %s entity (%s)", clazz.getSimpleName(), paramMsg));
        this.status = HttpStatus.NOT_FOUND;
    }
}
