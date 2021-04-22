package com.lhs.chatting.exception;

import org.springframework.util.StringUtils;

public class NotExistUserException extends RuntimeException {

    public NotExistUserException(Long userId) {
        super(String.format("Can not fount User entity (userId = %d)", userId));
    }

    public NotExistUserException(String email) {
        super(String.format("Can not fount User entity (email = %s)", email));
    }

}
