package com.example.bloombackend.achievement.entity;

public enum AchievementIcon {
    SEED("http://www.bloom24.kro.kr:8080/flower-icons/seed.svg"),
    SPROUT1("http://www.bloom24.kro.kr:8080/flower-icons/sprout1.svg"),
    SPROUT2("http://www.bloom24.kro.kr:8080/flower-icons/sprout2.svg");

    private final String url;

    AchievementIcon(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public static AchievementIcon fromLevel(int level) {
        if (level >= 2 && level <= 4) {
            return SPROUT1;
        } else if (level >= 5 && level <= 8) {
            return SPROUT2;
        }
        return SEED;
    }
}