package sg.edu.nus.iss.pt5.photolearnapp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizUserAnswer;
import sg.edu.nus.iss.pt5.photolearnapp.util.CommonUtils;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CommonUtilsUnitTest {
    @Test
    public void calcQuizScore_isCorrect() throws Exception {
        List<QuizItem> items = new ArrayList<QuizItem>();
        List<QuizUserAnswer> answers = new ArrayList<QuizUserAnswer>();

        QuizItem item =new QuizItem("QT1", "", "", "", "", 0.0, 0.0);
        item.setOptionOneAnswer(true);
        item.setOptionTwoAnswer(false);
        item.setOptionThreeAnswer(false);
        item.setOptionFourAnswer(true);
        items.add(item);

        QuizUserAnswer answer =new QuizUserAnswer("", "QT1");
        answer.setOptionOne(false);   // true
        answer.setOptionTwo(false);   // false
        answer.setOptionThree(false); // false
        answer.setOptionFour(true);   // true
        answers.add(answer);

        assertEquals(0, CommonUtils.calcQuizScore(items, answers));

        item =new QuizItem("QT2", "", "", "", "", 0.0, 0.0);
        item.setOptionOneAnswer(true);
        item.setOptionTwoAnswer(false);
        item.setOptionThreeAnswer(false);
        item.setOptionFourAnswer(true);
        items.add(item);

        answer =new QuizUserAnswer("", "QT2");
        answer.setOptionOne(true);    // true
        answer.setOptionTwo(false);   // false
        answer.setOptionThree(false); // false
        answer.setOptionFour(true);   // true
        answers.add(answer);

        assertEquals(1, CommonUtils.calcQuizScore(items, answers));

        item =new QuizItem("QT3", "", "", "", "", 0.0, 0.0);
        item.setOptionOneAnswer(true);
        item.setOptionTwoAnswer(false);
        item.setOptionThreeAnswer(true);
        item.setOptionFourAnswer(false);
        items.add(item);

        answer =new QuizUserAnswer("", "QT3");
        answer.setOptionOne(true);    // true
        answer.setOptionTwo(false);   // false
        answer.setOptionThree(false); // true
        answer.setOptionFour(true);   // false
        answers.add(answer);

        assertEquals(1, CommonUtils.calcQuizScore(items, answers));
    }
}