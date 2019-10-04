package com.example.demo.plagiarism.Reports.course.homework;


// Constructor template for Tie1:
//     new Defeat1 (Competitor c1, Competitor c2)
// Interpretation:
//     c1 and c2 have engaged in a contest that ended with
//         c1 defeating c2

public class Defeat1 implements Outcome {

    Competitor c1; // the winner of the outcome
    Competitor c2; // the loser of the outcome

    // the Java constructor
    Defeat1(Competitor c1, Competitor c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    // public methods
    // RETURNS: true iff this outcome represents a tie
    public boolean isTie() {
        return false;
    }

    // RETURNS: one of the competitors
    public Competitor first() {
        return c1;
    }

    // RETURNS: the other competitor
    public Competitor second() {
        return c2;
    }

    // GIVEN: no arguments
    // WHERE: this.isTie() is false
    // RETURNS: the winner of this outcome
    public Competitor winner() {
        if (!this.isTie()) {
            return c1;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    // GIVEN: no arguments
    // WHERE: this.isTie() is false
    // RETURNS: the loser of this outcome
    public Competitor loser() {
        if (!this.isTie()) {
            return c2;
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
