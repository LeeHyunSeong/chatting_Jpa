package com.lhs.chatting.service;

import com.lhs.chatting.model.Room;
import com.lhs.chatting.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository repository;

    public List<Room> getRooms() {
        return repository.findAll();
    }

    public Room getRoom(int number) {
        return repository.findByNumber(number);
    }

    public void makeRoom(String name) {
        repository.saveByName(name);
    }

    public boolean isRoomExist(int number) {
        return repository.findByNumber(number) != null;
    }

}
