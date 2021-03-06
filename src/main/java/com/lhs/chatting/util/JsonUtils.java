package com.lhs.chatting.util;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.experimental.UtilityClass;

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

    public <T> Optional<T> readValue(String value, TypeReference<T> valueTypeReference) {
        try {
            return Optional.of(MAPPER.readValue(value, valueTypeReference));
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
