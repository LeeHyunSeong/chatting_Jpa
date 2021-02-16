package com.lhs.chatting.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RoomNumberGenerateUtils {
    private static int roomNumber = 0;

    public int getNextRoomNumber() {
        return ++roomNumber;
    }

}
