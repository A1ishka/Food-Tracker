package com.makogon.foodtracker.service;

import com.makogon.foodtracker.model.BasePlan;
import com.makogon.foodtracker.repository.BasePlanRepository;
import org.springframework.stereotype.Service;

@Service
public class BasePlanService {

    private final BasePlanRepository basePlanRepository;

    public BasePlanService(BasePlanRepository basePlanRepository) {
        this.basePlanRepository = basePlanRepository;
    }

    public BasePlan getBasePlanById(long basePlanId) {
        return basePlanRepository.findById(basePlanId)
                .orElseThrow(() -> new IllegalArgumentException("Базовый план с идентификатором " + basePlanId + " не найден"));
    }

    public BasePlan saveBasePlan(BasePlan basePlan) {
        return basePlanRepository.save(basePlan);
    }

    public BasePlan updateBasePlan(BasePlan updatedBasePlan) {
        BasePlan existingBasePlan = getBasePlanById(updatedBasePlan.getBase_planid());

        existingBasePlan.setCalories(updatedBasePlan.getCalories());
        existingBasePlan.setProtein(updatedBasePlan.getProtein());
        existingBasePlan.setFats(updatedBasePlan.getFats());
        existingBasePlan.setCarbs(updatedBasePlan.getCarbs());
        existingBasePlan.setPlan(updatedBasePlan.getPlan());

        return basePlanRepository.save(existingBasePlan);
    }

    public void deleteBasePlanById(long basePlanId) {
        BasePlan basePlan = getBasePlanById(basePlanId);
        basePlanRepository.delete(basePlan);
    }
}