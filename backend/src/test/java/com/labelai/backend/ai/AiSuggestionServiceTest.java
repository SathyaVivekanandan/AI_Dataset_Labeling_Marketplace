package com.labelai.backend.ai;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AiSuggestionServiceTest {
    private final AiSuggestionService service = new AiSuggestionService();
    @Test void suggestsPositiveForPositiveText() {
        assertEquals("POSITIVE", service.suggest("Great product, fast delivery and helpful support").suggestedLabel());
    }
    @Test void suggestsNegativeForNegativeText() {
        assertEquals("NEGATIVE", service.suggest("Terrible service, slow delivery and broken product").suggestedLabel());
    }
    @Test void neutralTextIsFlaggedForHumanReview() {
        var result = service.suggest("The parcel arrived on Tuesday");
        assertEquals("NEUTRAL", result.suggestedLabel());
        assertTrue(result.humanReviewRequired());
    }
    @Test void rejectsBlankText() {
        assertThrows(IllegalArgumentException.class, () -> service.suggest("  "));
    }
}
