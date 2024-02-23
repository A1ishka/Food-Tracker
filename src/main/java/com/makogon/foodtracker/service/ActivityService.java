package com.makogon.foodtracker.service;

import com.makogon.foodtracker.model.Activity;
import com.makogon.foodtracker.repository.ActivityRepository;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Activity getActivityById(long activityId) {
        return activityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Активность с идентификатором " + activityId + " не найдена"));
    }

    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public Activity updateActivity(Activity updatedActivity) {
        Activity existingActivity = getActivityById(updatedActivity.getActivityID());

        existingActivity.setActivityName(updatedActivity.getActivityName());
        existingActivity.setActivityKoeff(updatedActivity.getActivityKoeff());

        return activityRepository.save(existingActivity);
    }

    public void deleteActivityById(long activityId) {
        Activity activity = getActivityById(activityId);
        activityRepository.delete(activity);
    }
}