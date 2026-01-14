package com.congmason.bossing.controllers;

import com.congmason.bossing.dto.DuplicateBossRequest;
import com.congmason.bossing.dto.WeeklyBossDto;
import com.congmason.bossing.entity.WeeklyBoss;
import com.congmason.bossing.entity.WeeklyCharacter;
import com.congmason.bossing.mappers.WeeklyBossMapper;
import com.congmason.bossing.mappers.WeeklyCharacterMapper;
import com.congmason.bossing.services.WeeklyBossService;
import com.congmason.bossing.services.WeeklyCharacterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/weekly-characters/{user_id}/{weekly_character_id}/bosses")
public class WeeklyBossController {

    private final WeeklyCharacterService weeklyCharacterService;
    private final WeeklyCharacterMapper weeklyCharacterMapper;
    private final WeeklyBossService weeklyBossService;
    private final WeeklyBossMapper weeklyBossMapper;

    public WeeklyBossController(WeeklyCharacterService weeklyCharacterService, WeeklyCharacterMapper weeklyCharacterMapper, WeeklyBossService weeklyBossService, WeeklyBossMapper weeklyBossMapper) {
        this.weeklyCharacterService = weeklyCharacterService;
        this.weeklyCharacterMapper = weeklyCharacterMapper;
        this.weeklyBossService = weeklyBossService;
        this.weeklyBossMapper = weeklyBossMapper;
    }

    @GetMapping
    public List<WeeklyBossDto> listWeeklyBosses(@PathVariable("user_id") Long userId,
                                                @PathVariable("weekly_character_id") Long weeklyCharacterId) {
        weeklyCharacterService.updateWeeklyMesos(userId, weeklyCharacterId);
        return weeklyBossService.listBosses(weeklyCharacterId)
                .stream()
                .map(weeklyBossMapper::toDto)
                .toList();
    }

    @PostMapping
    public WeeklyBossDto createWeeklyBoss(@PathVariable("user_id") Long userId,
                                          @PathVariable("weekly_character_id") Long weeklyCharacterId,
                                          @RequestBody WeeklyBossDto weeklyBossDto) {
        WeeklyBoss createdWeeklyBoss = weeklyBossService.createBoss(weeklyCharacterId, weeklyBossMapper.fromDto(weeklyBossDto));
        weeklyCharacterService.updateWeeklyMesos(userId, weeklyCharacterId);
        return weeklyBossMapper.toDto(createdWeeklyBoss);
    }

    @GetMapping(path = "/{boss_id}")
    public Optional<WeeklyBossDto> getWeeklyBoss(@PathVariable("weekly_character_id") Long weeklyCharacterId, @PathVariable("boss_id") Long bossId) {
        return weeklyBossService.getWeeklyBoss(weeklyCharacterId, bossId)
                .map(weeklyBossMapper::toDto);
    }

    @PutMapping(path = "/{boss_id}")
    public WeeklyBossDto updateBoss(@PathVariable("user_id") Long userId,
                                    @PathVariable("weekly_character_id") Long weeklyCharacterId,
                                    @PathVariable("boss_id") Long bossId,
                                    @RequestBody WeeklyBossDto bossDto) {
        WeeklyBoss updatedBoss = weeklyBossService.updateBoss(weeklyCharacterId, bossId, weeklyBossMapper.fromDto(bossDto));
        weeklyCharacterService.updateWeeklyMesos(userId, weeklyCharacterId);
        return weeklyBossMapper.toDto(updatedBoss);
    }

    @DeleteMapping(path = "/{boss_id}")
    public void deleteBoss (@PathVariable("user_id") Long userId,
                            @PathVariable("weekly_character_id") Long weeklyCharacterId,
                            @PathVariable("boss_id") Long bossId) {
        weeklyBossService.deleteBoss(weeklyCharacterId, bossId);
        weeklyCharacterService.updateWeeklyMesos(userId, weeklyCharacterId);
    }

    @PostMapping(path = "/duplicate")
    @Transactional
    public ResponseEntity<?> duplicateBosses(
            @PathVariable("user_id") Long userId,
            @PathVariable("weekly_character_id") Long weeklyCharacterId,
            @RequestBody DuplicateBossRequest request) {

        try {
            Long sourceCharacterId = request.getWeeklyCharacterId();

            Optional<WeeklyCharacter> sourceCharacter = weeklyCharacterService.getWeeklyCharacter(userId, sourceCharacterId);
            if (sourceCharacter.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Source character not found"));
            }
            List<WeeklyBoss> sourceBosses = weeklyBossService.listBosses(sourceCharacterId);

            if (sourceBosses.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "No bosses to copy from source character"));
            }

            if (request.isReplace()) {
                weeklyBossService.clearBosses(userId, weeklyCharacterId);
            }

            List<WeeklyBoss> copiedBosses = new ArrayList<>();

            for (WeeklyBoss sourceBoss : sourceBosses) {
                WeeklyBoss copy = new WeeklyBoss();
                copy.setBossName(sourceBoss.getBossName());
                copy.setPartySize(sourceBoss.getPartySize());

                WeeklyBoss saved = weeklyBossService.createBoss(weeklyCharacterId, copy);
                copiedBosses.add(saved);
            }

            weeklyCharacterService.updateWeeklyMesos(userId, weeklyCharacterId);

            Optional<WeeklyCharacter> targetCharacter = weeklyCharacterService.getWeeklyCharacter(userId, weeklyCharacterId);

            return ResponseEntity.ok(weeklyCharacterMapper.toDto(targetCharacter.orElse(null)));

        } catch (Exception e) {
            System.err.println("Error duplicating bosses: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to duplicate bosses: " + e.getMessage()));
        }
    }

}
