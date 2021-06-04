package ru.itis.diploma.models;

import lombok.Getter;

public enum AnswerScore {
    EXCELLENT(8, 10),
    GOOD(5, 7),
    BAD(3, 4),
    VERY_BAD(0, 2);

    @Getter
    private final long min;
    @Getter
    private final long max;

    private AnswerScore (long min, long max) {
        this.min = min;
        this.max = max;
    }

    public static AnswerScore get (long value) {
        AnswerScore[] answerScores = values();

        for (AnswerScore answerScore : answerScores) {
            long minRange = answerScore.getMin();
            long maxRange = answerScore.getMax();

            if (value >= minRange && value <= maxRange) {
                return answerScore;
            }
        }

        return null;
    }
}
