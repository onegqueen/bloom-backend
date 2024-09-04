package com.example.bloombackend.user.entity;

public enum Gender {
    M("남성"),F("여성"),O("기타");

    private final String label;

    Gender(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
