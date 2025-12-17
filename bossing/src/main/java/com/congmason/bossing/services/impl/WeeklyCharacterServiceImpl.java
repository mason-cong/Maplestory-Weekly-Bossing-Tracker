package com.congmason.bossing.services.impl;

import com.congmason.bossing.entity.User;
import com.congmason.bossing.entity.WeeklyBoss;
import com.congmason.bossing.entity.WeeklyCharacter;
import com.congmason.bossing.repository.UserRepository;
import com.congmason.bossing.repository.WeeklyCharacterRepository;
import com.congmason.bossing.services.WeeklyCharacterService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WeeklyCharacterServiceImpl implements WeeklyCharacterService {

    private final WeeklyCharacterRepository weeklyCharacterRepository;
    private final UserRepository userRepository;

    public WeeklyCharacterServiceImpl(WeeklyCharacterRepository weeklyCharacterRepository, UserRepository userRepository) {
        this.weeklyCharacterRepository = weeklyCharacterRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<WeeklyCharacter> listWeeklyCharacters(Long id) {
        return weeklyCharacterRepository.findByUserId(id);
    }

    @Override
    public WeeklyCharacter createWeeklyCharacter(Long userId, WeeklyCharacter weeklyCharacter) {
        if (null != weeklyCharacter.getId()) {
            throw new IllegalArgumentException("Character already has an ID");
        }

        if (null == weeklyCharacter.getCharacterName() || weeklyCharacter.getCharacterName().isBlank()) {
            throw new IllegalArgumentException("Character name is required");
        }

        if (null == weeklyCharacter.getCharacterLevel() || weeklyCharacter.getCharacterLevel().isBlank()) {
            throw new IllegalArgumentException("Character level is required");
        }

        if (null == weeklyCharacter.getCharacterClass() || weeklyCharacter.getCharacterClass().isBlank()) {
            throw new IllegalArgumentException("Character class is required");
        }


        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id entered"));

        return weeklyCharacterRepository.save(new WeeklyCharacter(
                null,
                weeklyCharacter.getCharacterClass(),
                weeklyCharacter.getCharacterLevel(),
                weeklyCharacter.getCharacterName(),
                weeklyCharacter.getMeso(),
                currentUser,
               null
        ));

    }

    @Override
    public Optional<WeeklyCharacter> getWeeklyCharacter(Long userId, Long weeklyCharacterId) {
        return weeklyCharacterRepository.findByUserIdAndId(userId, weeklyCharacterId);
    }

    @Transactional
    @Override
    public WeeklyCharacter updateWeeklyCharacter(Long userId, Long characterId, WeeklyCharacter weeklyCharacter) {
        if(null == weeklyCharacter.getId()) {
            throw new IllegalArgumentException("Character must have an ID");
        }

        if(!Objects.equals(weeklyCharacter.getId(), characterId)) {
            throw new IllegalArgumentException("CHanging character id is not permitted");
        }

        WeeklyCharacter updatedWeeklyCharacter = weeklyCharacterRepository.findByUserIdAndId(userId, characterId).orElseThrow(() ->
                new IllegalArgumentException("Character not found"));

        updatedWeeklyCharacter.setCharacterLevel(weeklyCharacter.getCharacterLevel());
        updatedWeeklyCharacter.setCharacterClass(weeklyCharacter.getCharacterClass());
        updatedWeeklyCharacter.setCharacterName(weeklyCharacter.getCharacterName());
        updatedWeeklyCharacter.setMeso(weeklyCharacterMeso(userId, characterId));

        return weeklyCharacterRepository.save(updatedWeeklyCharacter);
    }

    @Transactional
    @Override
    public void deleteWeeklyCharacter(Long userId, Long weeklyCharacterId) {
        weeklyCharacterRepository.deleteByUserIdAndId(userId, weeklyCharacterId);
    }

    @Transactional
    @Override
    public void updateWeeklyMesos(Long userId, Long weeklyCharacterId) {
        WeeklyCharacter character = weeklyCharacterRepository.findByUserIdAndId(userId, weeklyCharacterId).orElseThrow();
        List<WeeklyBoss> bosses = character.getWeeklyBosses();
        Long weeklyTotal = 0L;

        for (WeeklyBoss boss: bosses) {
            weeklyTotal += boss.getCrystalValue();
        }

        character.setMeso(weeklyTotal);
        weeklyCharacterRepository.save(character);
    }


    //Calculate the total amount of meso that a character earns from completed bosses
    @Override
    public Long weeklyCharacterMeso(Long userId, Long weeklyCharacterId) {
        WeeklyCharacter character = weeklyCharacterRepository.findByUserIdAndId(userId, weeklyCharacterId).orElseThrow();
        List<WeeklyBoss> bosses = character.getWeeklyBosses();
        Long weeklyTotal = 0L;

        for (WeeklyBoss boss: bosses) {
            weeklyTotal += boss.getCrystalValue();
        }

        return weeklyTotal;
    }

}
