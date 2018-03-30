package sg.edu.nus.iss.pt5.photolearnapp.model;

import java.util.Iterator;

/**
 * Created by mjeyakaran on 24/3/18.
 */

public class QuizItem extends Item {
    private QuizType type;
    private String explanation;
    private String optionOne;
    private boolean isOptionOneAnswer;
    private String optionTwo;
    private boolean isOptionTwoAnswer;
    private String optionThree;
    private boolean isOptionThreeAnswer;
    private String optionFour;
    private boolean isOptionFourAnswer;

    public QuizItem() {
    }

    public QuizItem(String id, String userId, String titleId, String photoUrl, String photoDesc, double latitude, double longitude) {
        super(id, userId, titleId, photoUrl, photoDesc, latitude, longitude);
    }

    // getters and setters
    public QuizType getType() {
        return type;
    }

    public void setType(QuizType type) {
        this.type = type;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getOptionOne() {
        return optionOne;
    }

    public void setOptionOne(String optionOne) {
        this.optionOne = optionOne;
    }

    public boolean isOptionOneAnswer() {
        return isOptionOneAnswer;
    }

    public void setOptionOneAnswer(boolean optionOneAnswer) {
        isOptionOneAnswer = optionOneAnswer;
    }

    public String getOptionTwo() {
        return optionTwo;
    }

    public void setOptionTwo(String optionTwo) {
        this.optionTwo = optionTwo;
    }

    public boolean isOptionTwoAnswer() {
        return isOptionTwoAnswer;
    }

    public void setOptionTwoAnswer(boolean optionTwoAnswer) {
        isOptionTwoAnswer = optionTwoAnswer;
    }

    public String getOptionThree() {
        return optionThree;
    }

    public void setOptionThree(String optionThree) {
        this.optionThree = optionThree;
    }

    public boolean isOptionThreeAnswer() {
        return isOptionThreeAnswer;
    }

    public void setOptionThreeAnswer(boolean optionThreeAnswer) {
        isOptionThreeAnswer = optionThreeAnswer;
    }

    public String getOptionFour() {
        return optionFour;
    }

    public void setOptionFour(String optionFour) {
        this.optionFour = optionFour;
    }

    public boolean isOptionFourAnswer() {
        return isOptionFourAnswer;
    }

    public void setOptionFourAnswer(boolean optionFourAnswer) {
        isOptionFourAnswer = optionFourAnswer;
    }

    // public methods
    public boolean isMCQ() {
        return this.type == QuizType.MCQ;
    }

    public boolean isMSQ() {
        return this.type == QuizType.MSQ;
    }

    public QuizUserAnswer answerQuiz(User user, Iterable<Boolean> answer) {
        return new QuizUserAnswer(user.getId(), this.getId(), answer);
    }
}
