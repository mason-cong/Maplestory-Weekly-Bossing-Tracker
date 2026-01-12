package com.congmason.bossing.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "weekly_characters")
public class WeeklyCharacter {

    private final int BOSS_LIMIT = 14;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "character_class", nullable = false)
    private String characterClass;

    @Column(name = "character_level", nullable = false)
    private String characterLevel;

    @Column(name = "character_name", nullable = false)
    private String characterName;

    @Column(name = "character_meso", nullable = false)
    private Long meso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "weeklyCharacter", cascade = CascadeType.ALL, orphanRemoval = true) // Assuming the association is defined on the other entity
    private List<WeeklyBoss> weeklyBosses;

    public WeeklyCharacter() {}

    public WeeklyCharacter(Long id, String characterClass, String characterLevel, String characterName, Long meso, User user, List<WeeklyBoss> weeklyBosses) {
        this.id = id;
        this.characterClass = characterClass;
        this.characterLevel = characterLevel;
        this.characterName = characterName;
        this.meso = meso;
        this.user = user;
        this.weeklyBosses = weeklyBosses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public String getCharacterLevel() {
        return characterLevel;
    }

    public void setCharacterLevel(String characterLevel) {
        this.characterLevel = characterLevel;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<WeeklyBoss> getWeeklyBosses() {
        return weeklyBosses;
    }

    public void setWeeklyBosses(List<WeeklyBoss> weeklyBosses) {
        this.weeklyBosses = weeklyBosses;
    }

    public Long getMeso() {
        return meso;
    }

    public void setMeso(Long meso) {
        this.meso = meso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeeklyCharacter that = (WeeklyCharacter) o;
        return Objects.equals(id, that.id) && Objects.equals(characterClass, that.characterClass) && Objects.equals(characterLevel, that.characterLevel) && Objects.equals(characterName, that.characterName) && Objects.equals(meso, that.meso) && Objects.equals(user, that.user) && Objects.equals(weeklyBosses, that.weeklyBosses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, characterClass, characterLevel, characterName, user, meso, weeklyBosses);
    }

    @Override
    public String toString() {
        return "WeeklyCharacter{" +
                "id=" + id +
                ", characterClass='" + characterClass + '\'' +
                ", characterLevel='" + characterLevel + '\'' +
                ", characterName='" + characterName + '\'' +
                "meso=" + meso + '\'' +
                ", user=" + user + '\'' +
                ", weeklyBosses=" + weeklyBosses +
                '}';
    }


}

