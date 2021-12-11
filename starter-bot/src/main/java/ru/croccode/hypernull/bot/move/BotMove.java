package ru.croccode.hypernull.bot.move;

import ru.croccode.hypernull.geometry.Offset;
import ru.croccode.hypernull.geometry.Point;

import java.util.Set;

public class BotMove {

    public Offset moveWithoutBarrires() {
        switch (BasicMove.getDirection()){
            case ("Up"):
                return new Offset(0, 1);
            case ("Down"):
                return new Offset(0, -1);
            case ("Left"):
                return new Offset(-1, 0);
            default:
                return new Offset(0, 0);
        }
    }

    public Offset moveOnLeftToChangeDirection() {
        BlockAnalizer blockAnalizer = new BlockAnalizer();

        if (!blockAnalizer.blockOnLeft()) {
            BasicMove.minusLeftStepsAmount(1);
            return new Offset(-1, 0);
        }

        if (BasicMove.getDirection().equals("Up_Left")) {

            if (!blockAnalizer.blockOnDown()) {
                BasicMove.plusLeftStepsAmount(1);
                //BasicMove.setStepUpAmount(1);
                return new Offset(0, -1);
            }

            if (!blockAnalizer.blockOnLeftAndDown()) {
                BasicMove.minusLeftStepsAmount(1);
                return new Offset(-1, -1);
            }

            if (!blockAnalizer.blockOnRightAndDown()) {
                //BasicMove.setStepUpAmount(1);
                BasicMove.plusLeftStepsAmount(2);
                return new Offset(1, -1);
            }
        }

        if (BasicMove.getDirection().equals("Down_Left")) {

            if (!blockAnalizer.blockOnTop()) {
                // BasicMove.setStepDownAmount(1);
                return new Offset(0, 1);
            }

            if (blockAnalizer.blockOnLeftAndUp()) {
                BasicMove.minusLeftStepsAmount(1);
                return new Offset(-1, 1);
            }

            if (blockAnalizer.blockOnRightAndUp()) {
                //BasicMove.setStepDownAmount(1);
                BasicMove.plusLeftStepsAmount(1);
                return new Offset(1, 1);
            }

        }

        return new Offset(0, 0);
    }

    public void changeDirectionToTheLeft() {
        switch (BasicMove.getDirection()){
            case ("Up"):
                BasicMove.setDirection("Up_Left");
                BasicMove.plusLeftStepsAmount(1);
                break;
            case ("Down"):
                BasicMove.setDirection("Down_Left");
                BasicMove.plusLeftStepsAmount(1);
                break;
        }
    }

    public Offset changeDirectionToVerticalWays() {
        switch (BasicMove.getDirection()){
            case ("Up_Left"):
                BasicMove.setDirection("Down");
                return new Offset(-1, -1);
            case ("Down_Left"):
                BasicMove.setDirection("Up");
                return new Offset(-1, 1);
        }
        return new Offset(0, 0);
    }
}
