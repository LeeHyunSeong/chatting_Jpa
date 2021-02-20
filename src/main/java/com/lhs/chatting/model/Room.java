package com.lhs.chatting.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Room {
    private int number;
    private String name;
}