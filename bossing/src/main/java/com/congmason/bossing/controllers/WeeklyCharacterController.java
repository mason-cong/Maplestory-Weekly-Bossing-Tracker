package com.congmason.bossing.controllers;

import com.congmason.bossing.dto.WeeklyCharacterDto;
import com.congmason.bossing.entity.WeeklyCharacter;
import com.congmason.bossing.mappers.WeeklyCharacterMapper;
import com.congmason.bossing.services.WeeklyCharacterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/weekly-characters")
public class WeeklyCharacterController {

    private final WeeklyCharacterService weeklyCharacterService;
    private final WeeklyCharacterMapper weeklyCharacterMapper;

    public WeeklyCharacterController(WeeklyCharacterService weeklyCharacterService, WeeklyCharacterMapper weeklyCharacterMapper) {
        this.weeklyCharacterService = weeklyCharacterService;
        this.weeklyCharacterMapper = weeklyCharacterMapper;
    }

    @GetMapping
    public List<WeeklyCharacterDto> listWeeklyCharacters(Long id) {
        return weeklyCharacterService.listWeeklyCharacters(id)
                .stream()
                .map(weeklyCharacterMapper::toDto)
                .toList();
    }

    @PostMapping
    public WeeklyCharacterDto createWeeklyCharacter(@RequestBody WeeklyCharacterDto weeklyCharacterDto) {
        WeeklyCharacter createdWeeklyCharacter = weeklyCharacterService.createWeeklyCharacter(
                weeklyCharacterMapper.fromDto(weeklyCharacterDto)
        );
        return weeklyCharacterMapper.toDto(createdWeeklyCharacter);
    }

    @GetMapping(path = "/{weekly_character_id}")
    public Optional<WeeklyCharacterDto> getWeeklyCharacter(@PathVariable("weekly_character_id") Long id) {
        return weeklyCharacterService.getWeeklyCharacter(id).map(weeklyCharacterMapper::toDto);
    }

    @PutMapping(path = "/{weekly_character_id}")
    public WeeklyCharacterDto updateWeeklyCharacter(@PathVariable("weekly_character_id") Long id, @RequestBody WeeklyCharacterDto weeklyCharacterDto) {
        WeeklyCharacter updatedWeeklyCharacter = weeklyCharacterService.updateWeeklyCharacter(id, weeklyCharacterMapper.fromDto(weeklyCharacterDto));

        return weeklyCharacterMapper.toDto(updatedWeeklyCharacter);
    }

    @DeleteMapping(path = "/{weekly_character_id}")
    public void deleteWeeklyCharacter(@PathVariable("weekly_character_id") Long id) {
        weeklyCharacterService.deleteWeeklyCharacter(id);
    }

}
