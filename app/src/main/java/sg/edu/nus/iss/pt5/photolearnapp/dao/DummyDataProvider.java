package sg.edu.nus.iss.pt5.photolearnapp.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sg.edu.nus.iss.pt5.photolearnapp.model.LearningItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningTitle;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizTitle;

/**
 * Created by mjeyakaran on 18/3/18.
 */

public class DummyDataProvider {

    public static List<LearningSession> getLearningSessionList() {
        List<LearningSession> learningSessionList = new ArrayList<>();
        LearningSession learningSession;
        for (int i = 0; i < 20; i++) {
            learningSession = new LearningSession();
            learningSession.setSessionID("20180201-IOT-M0" + i);
            learningSession.setCourseDate(Calendar.getInstance());
            learningSessionList.add(learningSession);
        }

        return learningSessionList;
    }

    public static List<LearningTitle> getLearningTitleList() {
        List<LearningTitle> learningTitleList = new ArrayList<>();
        LearningTitle learningTitle;
        for (int i = 0; i < 2; i++) {
            learningTitle = new LearningTitle();
            learningTitle.setTitle("Learning Title" + i);
            learningTitle.setDateTime(Calendar.getInstance());
            learningTitleList.add(learningTitle);
        }

        return learningTitleList;
    }

    public static List<QuizTitle> getQuizTitleList() {
        List<QuizTitle> quizTitleList = new ArrayList<>();
        QuizTitle quizTitle;
        for (int i = 0; i < 2; i++) {
            quizTitle = new QuizTitle();
            quizTitle.setTitle("Quiz Title" + i);
            quizTitle.setDateTime(Calendar.getInstance());
            quizTitleList.add(quizTitle);
        }

        return quizTitleList;
    }

    public static List<LearningItem> getLearningItemList() {
        List<LearningItem> learningItemList = new ArrayList<>();
        LearningItem learningItem;
        for (int i = 0; i < 2; i++) {
            learningItem = new LearningItem();
            learningItem.setPhotoDesc("Learning Item" + i);
            learningItem.setPhotoUrl("uploads/1521886071849.JPEG");
            learningItemList.add(learningItem);
        }

        return learningItemList;
    }

    public static List<QuizItem> getQuizItemList() {
        List<QuizItem> quizItemList = new ArrayList<>();
        QuizItem quizItem;
        for (int i = 0; i < 2; i++) {
            quizItem = new QuizItem();
            quizItem.setPhotoDesc("Quiz Item" + i);
            quizItem.setPhotoUrl("uploads/1521886071849.JPEG");
            quizItemList.add(quizItem);
        }

        return quizItemList;
    }
}
