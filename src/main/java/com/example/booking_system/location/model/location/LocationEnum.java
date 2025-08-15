package com.example.booking_system.location.model.location;

public class LocationEnum {

    public enum RoomType {
        BUILDING("BUILDING"),
        FLOOR("FLOOR"),
        ROOM("ROOM");

        private final String roomType;

        RoomType(String roomType) {
            this.roomType = roomType;
        }

        public String getRoomType() {
            return roomType;
        }
    }
}
