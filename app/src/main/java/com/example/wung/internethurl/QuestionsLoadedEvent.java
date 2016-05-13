package com.example.wung.internethurl;

/**
 * Created by Wung on 11/05/16.
 */
public class QuestionsLoadedEvent {
    SOQuestions questions = null;
    QuestionsLoadedEvent(SOQuestions questions) {
        this.questions = questions;
    }
}
