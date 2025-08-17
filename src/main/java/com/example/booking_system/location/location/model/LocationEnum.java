package com.example.booking_system.location.location.model;

public class LocationEnum {

    public enum RoomType {
        VIP("VIP"),
        NORMAL("NORMAL"),
        NORMAL_PLUS("NORMAL-PLUS");

        private final String roomType;

        RoomType(String roomType) {
            this.roomType = roomType;
        }

        public String getRoomType() {
            return roomType;
        }
    }
}
