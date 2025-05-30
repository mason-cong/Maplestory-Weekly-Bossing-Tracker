package com.congmason.bossing.services.impl;

import com.congmason.bossing.entity.WeeklyCharacter;
import com.congmason.bossing.repository.WeeklyCharacterRepository;
import com.congmason.bossing.services.WeeklyCharacterService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WeeklyCharacterServiceImpl implements WeeklyCharacterService {

    private final WeeklyCharacterRepository weeklyCharacterRepository;

    public WeeklyCharacterServiceImpl(WeeklyCharacterRepository weeklyCharacterRepository) {
        this.weeklyCharacterRepository = weeklyCharacterRepository;
    }

    @Override
    public List<WeeklyCharacter> listWeeklyCharacters(Long id) {
        return weeklyCharacterRepository.findByUserId(id);
    }

    @Override
    public WeeklyCharacter createWeeklyCharacter(WeeklyCharacter weeklyCharacter) {
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

        return weeklyCharacterRepository.save(new WeeklyCharacter(
                null,
                weeklyCharacter.getCharacterClass(),
                weeklyCharacter.getCharacterLevel(),
                weeklyCharacter.getCharacterName(),
                null,
               null
        ));

    }

    @Override
    public Optional<WeeklyCharacter> getWeeklyCharacter(Long id) {
        return weeklyCharacterRepository.findById(id);
    }

    @Override
    public WeeklyCharacter updateWeeklyCharacter(Long id, WeeklyCharacter weeklyCharacter) {
        if(null == weeklyCharacter.getId()) {
            throw new IllegalArgumentException("Character must have an ID");
        }

        if(!Objects.equals(weeklyCharacter.getId(), id)) {
            throw new IllegalArgumentException("CHanging character id is not permitted");
        }

        WeeklyCharacter updatedWeeklyCharacter = weeklyCharacterRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Character not found"));

        updatedWeeklyCharacter.setCharacterLevel(weeklyCharacter.getCharacterLevel());
        updatedWeeklyCharacter.setCharacterClass(weeklyCharacter.getCharacterClass());
        updatedWeeklyCharacter.setCharacterName(weeklyCharacter.getCharacterName());

        return weeklyCharacterRepository.save(updatedWeeklyCharacter);
    }

    @Override
    public void deleteWeeklyCharacter(Long id) {
        weeklyCharacterRepository.deleteById(id);
    }
}
