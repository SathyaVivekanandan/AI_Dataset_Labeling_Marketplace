# Enhancement Proposal — Explainable Label Suggestions

## Problem
Manual text sentiment labeling is repetitive and can produce inconsistent first-pass labels.

## Solution approach
Add an authenticated REST endpoint, `POST /api/ai/suggest`, that counts a documented list of positive and negative lexical cues and returns POSITIVE, NEGATIVE, or NEUTRAL, a baseline score, rationale, and `humanReviewRequired=true`. The React labeling studio can prefill the label, but the annotator can edit it before submission.

## Technology choice
Java service layer and Spring Web REST endpoint; React UI; JUnit 5 unit tests.

## Limits and evaluation
This is a deterministic keyword baseline, not a trained model. The score is a heuristic, not calibrated probability. Negation, sarcasm, domain-specific vocabulary, and non-English text can cause errors. Evaluation against a labeled holdout set and a trained classifier are future work. Every suggestion must be reviewed by a human.
