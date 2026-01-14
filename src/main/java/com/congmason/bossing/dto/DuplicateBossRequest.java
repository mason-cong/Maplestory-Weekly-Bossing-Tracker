package com.congmason.bossing.dto;

public class DuplicateBossRequest {

    private Long weeklyCharacterId;
    private boolean replace;

    public boolean isReplace() {
        return replace;
    }

    public void setReplace(boolean replace) {
        this.replace = replace;
    }

    public Long getWeeklyCharacterId() {
        return weeklyCharacterId;
    }

    public void setWeeklyCharacterId(Long weeklyCharacterId) {
        this.weeklyCharacterId = weeklyCharacterId;
    }
}
