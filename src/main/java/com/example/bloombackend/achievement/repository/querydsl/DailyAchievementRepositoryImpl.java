package com.example.bloombackend.achievement.repository.querydsl;

import com.example.bloombackend.achievement.controller.dto.response.MonthlyAchievementResponse;
import com.example.bloombackend.achievement.controller.response.DailyFlowerResponse;
import com.example.bloombackend.achievement.entity.QDailyAchievementEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class DailyAchievementRepositoryImpl implements DailyAchievementRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public DailyAchievementRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<MonthlyAchievementResponse> getRecentSixMonthsAchievements(Long userId) {
        QDailyAchievementEntity dailyAchievement = QDailyAchievementEntity.dailyAchievementEntity;

        LocalDate now = LocalDate.now();
        LocalDateTime startOfSixthMonth = now.minusMonths(5).withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfCurrentMonth = now.withDayOfMonth(now.lengthOfMonth()).atTime(23, 59, 59);

        return queryFactory
                .select(Projections.constructor(
                        MonthlyAchievementResponse.class,
                        dailyAchievement.createdAt.yearMonth(),
                        dailyAchievement.achievementLevel.when(9).then(1).otherwise(0).sum()
                ))
                .from(dailyAchievement)
                .where(
                        dailyAchievement.user.id.eq(userId),
                        dailyAchievement.createdAt.between(startOfSixthMonth, endOfCurrentMonth)
                )
                .groupBy(dailyAchievement.createdAt.yearMonth())
                .fetch();
    }

    public DailyFlowerResponse getDailyFlower(Long userId) {
        QDailyAchievementEntity dailyAchievement = QDailyAchievementEntity.dailyAchievementEntity;

        LocalDate now = LocalDate.now();
        LocalDateTime startOfToday = now.atStartOfDay();
        LocalDateTime endOfToday = now.atTime(LocalTime.MAX);

        return queryFactory
                .select(Projections.constructor(
                        DailyFlowerResponse.class,
                        dailyAchievement.flower.id,
                        dailyAchievement.flower.iconUrl))
                .from(dailyAchievement)
                .where(
                        dailyAchievement.user.id.eq(userId),
                        dailyAchievement.createdAt.between(startOfToday, endOfToday)
                )
                .fetchOne();
    }
}