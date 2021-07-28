package com.lhs.chatting.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DisplayedMsgNumUtils {

    private static final Integer DEFAULT_MSG_COUNT_OF_PAGE = 10;
    
    public int getNewDisplayedMsgNum(int displayedMsgNum, int addedMsgNum) {
        return (displayedMsgNum / DEFAULT_MSG_COUNT_OF_PAGE) * DEFAULT_MSG_COUNT_OF_PAGE + addedMsgNum;
    }

}