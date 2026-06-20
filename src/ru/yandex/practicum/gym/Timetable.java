package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    private final Map<Coach, Integer> countByCoaches = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        Map<TimeOfDay, List<TrainingSession>> daySchedule = timetable.getOrDefault(trainingSession.getDayOfWeek(),
                new TreeMap<>());
        List<TrainingSession> trainingSessions = daySchedule.getOrDefault(trainingSession.getTimeOfDay(), new ArrayList<>());

        trainingSessions.add(trainingSession);
        daySchedule.put(trainingSession.getTimeOfDay(), trainingSessions);
        timetable.put(trainingSession.getDayOfWeek(), daySchedule);
        Integer count = countByCoaches.getOrDefault(trainingSession.getCoach(), 0);
        countByCoaches.put(trainingSession.getCoach(), count + 1);
    }

    public Map<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        Map<TimeOfDay, List<TrainingSession>> daySchedule = this.getTrainingSessionsForDay(dayOfWeek);
        return daySchedule.getOrDefault(timeOfDay, new ArrayList<>());
    }

    public Map<Coach, Integer> getCountByCoaches() {
        List<Map.Entry<Coach, Integer>> list = new ArrayList<>(countByCoaches.entrySet());
        list.sort((o1, o2) -> o2.getValue() - o1.getValue());

        Map<Coach, Integer> sortedCountByCoaches = new LinkedHashMap<>();
        for (Map.Entry<Coach, Integer> entry : list) {
            sortedCountByCoaches.put(entry.getKey(), entry.getValue());
        }
        return sortedCountByCoaches;
    }
}
