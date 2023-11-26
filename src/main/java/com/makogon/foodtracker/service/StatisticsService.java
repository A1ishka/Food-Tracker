package com.makogon.foodtracker.service;
import com.makogon.foodtracker.model.Statistics;
import com.makogon.foodtracker.repository.StatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public StatisticsService(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    public Statistics getStatisticsById(long statisticsId) {
        return statisticsRepository.findById(statisticsId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + statisticsId));}

    public Statistics saveStatistics(Statistics statistics) {
        return statisticsRepository.save(statistics);
    }

    public Statistics updateStatistics(Statistics updatedStatistics) {
        Statistics existingStatistics = getStatisticsById(updatedStatistics.getStatisticsID());

        existingStatistics.setDate(updatedStatistics.getDate());
        existingStatistics.setCalories(updatedStatistics.getCalories());
        existingStatistics.setProtein(updatedStatistics.getProtein());
        existingStatistics.setFats(updatedStatistics.getFats());
        existingStatistics.setCarbs(updatedStatistics.getCarbs());

        return statisticsRepository.save(existingStatistics);
    }

    public void deleteStatisticsById(long statisticsId) {
        Statistics statistics = getStatisticsById(statisticsId);
        statisticsRepository.delete(statistics);
    }
}