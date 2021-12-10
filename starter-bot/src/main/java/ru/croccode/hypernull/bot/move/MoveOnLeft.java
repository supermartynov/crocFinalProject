package ru.croccode.hypernull.bot.move;

import ru.croccode.hypernull.geometry.Offset;

public class MoveOnLeft {

    public static Offset moveOnLeftToChangeDirection() {

        if (!BasicMove.blockAnalizer.blockOnLeft()) {
            BasicMove.minusLeftStepsAmount(1);
            return new Offset(-1, 0);
        }

        if (BasicMove.getDirection().equals("Up_Left")) {

            if (!BasicMove.blockAnalizer.blockOnDown()) {
                BasicMove.plusLeftStepsAmount(1);
                //BasicMove.setStepUpAmount(1);
                return new Offset(0, -1);
            }

            if (!BasicMove.blockAnalizer.blockOnLeftAndDown()) {
                BasicMove.minusLeftStepsAmount(1);
                return new Offset(-1, -1);
            }

            if (!BasicMove.blockAnalizer.blockOnRightAndDown()) {
                //BasicMove.setStepUpAmount(1);
                BasicMove.plusLeftStepsAmount(2);
                return new Offset(1, -1);
            }
        }

        if (BasicMove.getDirection().equals("Down_Left")) {

            if (!BasicMove.blockAnalizer.blockOnTop()) {
               // BasicMove.setStepDownAmount(1);
                return new Offset(0, 1);
            }

            if (!BasicMove.blockAnalizer.blockOnLeftAndUp()) {
                BasicMove.minusLeftStepsAmount(1);
                return new Offset(-1, 1);
            }

            if (!BasicMove.blockAnalizer.blockOnRightAndUp()) {
                //BasicMove.setStepDownAmount(1);
                BasicMove.plusLeftStepsAmount(1);
                return new Offset(1, 1);
            }

        }

        return new Offset(0, 0);
    }

}
