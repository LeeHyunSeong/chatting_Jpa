package com.lhs.chatting.exception;

public class NotExistedUserException extends RuntimeException{
    public NotExistedUserException(){
        super("Can not found User entity");
    }
}
