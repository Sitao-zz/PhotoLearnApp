package sg.edu.nus.iss.pt5.photolearnapp.util;

import java.util.List;

import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.constants.UIType;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningTitle;
import sg.edu.nus.iss.pt5.photolearnapp.model.Participant;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizTitle;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizUserAnswer;

/**
 * Created by mjeyakaran on 24/3/18.
 */

public class CommonUtils {


    public static boolean isParticipantViewMode() {
        return (Mode.VIEW == ((Participant) SecurityContext.getInstance().getRole()).getMode());
    }

    public static boolean isParticipantEditMode() {
        return (Mode.EDIT == ((Participant) SecurityContext.getInstance().getRole()).getMode());
    }

    public static boolean isParticipantAnswerMode() {
        return (Mode.ANSWER == ((Participant) SecurityContext.getInstance().getRole()).getMode());
    }

    public static boolean isLearningUI(UIType uiType) {
        return (UIType.LEARNING == uiType);
    }

    public static boolean isLearningUI(Object obj) {
        return (obj instanceof LearningTitle || obj instanceof LearningItem);
    }

    public static boolean isQuizUI(UIType uiType) {
        return (UIType.QUIZ == uiType);
    }

    public static boolean isQuizUI(Object obj) {
        return (obj instanceof QuizTitle || obj instanceof QuizItem);
    }

    public static String generateRandomImageFileName() {
        return System.currentTimeMillis() + ".JPEG";
    }

    public static int calcQuizScore(List<QuizItem> items, List<QuizUserAnswer> answers) {
        int score = 0;
        for (QuizItem item : items) {
            for (QuizUserAnswer answer : answers) {
                if (answer.getQuizItemId().equals(item.getId())) {
                    if (answer.isOptionOne() == item.isOptionOneAnswer()
                            && answer.isOptionTwo() == item.isOptionTwoAnswer()
                            && answer.isOptionThree() == item.isOptionThreeAnswer()
                            && answer.isOptionFour() == item.isOptionFourAnswer()) {
                        score++;
                    }
                }
            }
        }
        return score;
    }
}
