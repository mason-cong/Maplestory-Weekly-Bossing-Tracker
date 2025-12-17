package com.congmason.bossing.services.impl;

import com.congmason.bossing.entity.WeeklyBoss;
import com.congmason.bossing.entity.WeeklyBossesValues;
import com.congmason.bossing.entity.WeeklyCharacter;
import com.congmason.bossing.repository.WeeklyBossRepository;
import com.congmason.bossing.repository.WeeklyCharacterRepository;
import com.congmason.bossing.services.WeeklyBossService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WeeklyBossServiceImpl implements WeeklyBossService {

    private final WeeklyBossRepository bossRepository;
    private final WeeklyCharacterRepository weeklyCharacterRepository;

    public WeeklyBossServiceImpl(WeeklyBossRepository bossRepository, WeeklyCharacterRepository weeklyCharacterRepository) {
        this.bossRepository = bossRepository;
        this.weeklyCharacterRepository = weeklyCharacterRepository;
    }

    @Override
    public List<WeeklyBoss> listBosses(Long weeklyCharacterId) {
        return bossRepository.findByWeeklyCharacterId(weeklyCharacterId);
    }

    @Override
    public WeeklyBoss createBoss(Long weeklyCharacterId, WeeklyBoss boss) {
        if (null != boss.getId()) {
            throw new IllegalArgumentException("Boss already has an ID");
        }
        if (null == boss.getBossName()) {
            throw new IllegalArgumentException("Please select a boss");
        }

        WeeklyCharacter weeklyCharacter = weeklyCharacterRepository.findById(weeklyCharacterId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid character id provided"));

        WeeklyBoss newBoss = new WeeklyBoss(
                null,
                boss.getBossName(),
                boss.getPartySize(),
                crystalValueCalculator(boss.getBossName(), boss.getPartySize()),
                weeklyCharacter
        );

        return bossRepository.save(newBoss);
    }

    @Override
    public Optional<WeeklyBoss> getWeeklyBoss(Long weeklyCharacterId, Long weeklyBossId) {
        return bossRepository.findByWeeklyCharacterIdAndId(weeklyCharacterId, weeklyBossId);
    }

    @Transactional
    @Override
    public WeeklyBoss updateBoss(Long weeklyCharacterId, Long bossId, WeeklyBoss boss) {
        if(null == boss.getId()) {
            throw new IllegalArgumentException("Character must have an ID");
        }

        if(!Objects.equals(boss.getId(), bossId)) {
            throw new IllegalArgumentException("Changing boss id is not permitted");
        }

        WeeklyBoss updatedBoss = bossRepository.findByWeeklyCharacterIdAndId(weeklyCharacterId, bossId).orElseThrow(() ->
                new IllegalArgumentException("Boss not found"));

        updatedBoss.setPartySize(boss.getPartySize());
        updatedBoss.setBossName(boss.getBossName());
        updatedBoss.setCrystalValue(boss.getCrystalValue());

        return bossRepository.save(updatedBoss);
    }

    @Transactional
    @Override
    public void deleteBoss(Long weeklyCharacterId, Long weeklyBossId) {
        bossRepository.deleteByWeeklyCharacterIdAndId(weeklyCharacterId, weeklyBossId);
    }

    //When new weekly boss is added to a character, update the crystal value in accordance to boss and party size
    private Long crystalValueCalculator(String bossName, int partySize) {
        Long calculatedCrystalValue = 0L;
            calculatedCrystalValue = WeeklyBossesValues.valueOf(bossName).getCrystalValue()/partySize;

        return calculatedCrystalValue;
    }

    private Long weeklyTotalMesoCalculator(Long weeklyCharacterId) {
        List<WeeklyBoss> bosses = bossRepository.findByWeeklyCharacterId(weeklyCharacterId);
        Long weeklyTotal = 0L;

        for (WeeklyBoss boss: bosses) {
            weeklyTotal += boss.getCrystalValue();
        }

        return weeklyTotal;
    }
}
