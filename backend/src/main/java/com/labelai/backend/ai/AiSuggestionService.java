
package com.labelai.backend.ai;

import org.springframework.stereotype.Service;
import java.util.Locale;

@Service
public class AiSuggestionService {

    public record Suggestion(
            String suggestedLabel,
            boolean humanReviewRequired) {
    }

    public Suggestion suggest(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(
                    "Text cannot be blank");
        }

        String input = text.toLowerCase(Locale.ROOT);

        String[] positiveWords = {
            "great", "good", "excellent", "helpful",
            "fast", "love", "happy", "wonderful"
        };

        String[] negativeWords = {
            "terrible", "bad", "slow", "broken",
            "awful", "poor", "hate", "worst"
        };

        int positive = 0;
        int negative = 0;

        for (String word : positiveWords) {
            if (input.contains(word)) {
                positive++;
            }
        }

        for (String word : negativeWords) {
            if (input.contains(word)) {
                negative++;
            }
        }

        if (positive > negative) {
            return new Suggestion("POSITIVE", false);
        }

        if (negative > positive) {
            return new Suggestion("NEGATIVE", false);
        }

        return new Suggestion("NEUTRAL", true);
    }
}