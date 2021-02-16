package com.lhs.chatting.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public <T> Optional<T> readValue(String value, Class<T> clazz) {
        try {
            return Optional.of(MAPPER.readValue(value, clazz));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    public <T> String writeValue(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

}
