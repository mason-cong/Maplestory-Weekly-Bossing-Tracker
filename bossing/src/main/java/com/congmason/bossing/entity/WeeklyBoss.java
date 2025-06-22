package com.congmason.bossing.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "weekly_bosses")
public class WeeklyBoss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "boss_name", nullable = false)
    private String bossName;

    @Column(name = "party_size", nullable = false)
    private int partySize;

    @Column(name = "crystal_value", nullable = false)
    private Long crystalValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weekly_character_id")
    private WeeklyCharacter weeklyCharacter;

    public WeeklyBoss() {
    }

    public WeeklyBoss(Long id, String bossName, int partySize, Long crystalValue, WeeklyCharacter weeklyCharacter) {
        this.id = id;
        this.bossName = bossName;
        this.partySize = partySize;
        this.crystalValue = crystalValue;
        this.weeklyCharacter = weeklyCharacter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public int getPartySize() {
        return partySize;
    }

    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }

    public Long getCrystalValue() {
        return crystalValue;
    }

    public void setCrystalValue(Long crystalValue) {
        this.crystalValue = crystalValue;
    }

    public WeeklyCharacter getWeeklyCharacter() {
        return weeklyCharacter;
    }

    public void setWeeklyCharacter(WeeklyCharacter weeklyCharacter) {
        this.weeklyCharacter = weeklyCharacter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeeklyBoss that = (WeeklyBoss) o;
        return Objects.equals(id, that.id) && Objects.equals(bossName, that.bossName) && Objects.equals(partySize, that.partySize) && Objects.equals(crystalValue, that.crystalValue) && Objects.equals(weeklyCharacter, that.weeklyCharacter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bossName, partySize, crystalValue, weeklyCharacter);
    }

    @Override
    public String toString() {
        return "WeeklyBoss{" +
                "id=" + id +
                ", bossName='" + bossName + '\'' +
                ", partySize='" + partySize + '\'' +
                ", crystalValue='" + crystalValue + '\'' +
                ", weeklyCharacter=" + weeklyCharacter +
                '}';
    }
}
