package com.congmason.bossing.controllers;

import com.congmason.bossing.dto.WeeklyBossDto;
import com.congmason.bossing.entity.WeeklyBoss;
import com.congmason.bossing.mappers.WeeklyBossMapper;
import com.congmason.bossing.services.WeeklyBossService;
import com.congmason.bossing.services.WeeklyCharacterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/weekly-characters/{user_id}/{weekly_character_id}/bosses")
public class WeeklyBossController {

    private final WeeklyCharacterService weeklyCharacterService;
    private final WeeklyBossService weeklyBossService;
    private final WeeklyBossMapper weeklyBossMapper;

    public WeeklyBossController(WeeklyCharacterService weeklyCharacterService, WeeklyBossService weeklyBossService, WeeklyBossMapper weeklyBossMapper) {
        this.weeklyCharacterService = weeklyCharacterService;
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
    public void deleteBoss (@PathVariable("weekly_character_id") Long weeklyCharacterId,
                            @PathVariable("boss_id") Long bossId) {
        weeklyBossService.deleteBoss(weeklyCharacterId, bossId);
    }

}
