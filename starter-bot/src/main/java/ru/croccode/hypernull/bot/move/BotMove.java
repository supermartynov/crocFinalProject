package ru.croccode.hypernull.bot.move;

import ru.croccode.hypernull.geometry.Offset;
import ru.croccode.hypernull.geometry.Point;

import java.util.Random;
import java.util.Set;

public class BotMove {

    private static final Random rnd = new Random(System.currentTimeMillis());



    public Offset moveWithoutBarrires() {
        switch (BasicMove.getDirection()){
            case ("Up"):
                return new Offset(0, 1);
            case ("Down"):
                return new Offset(0, -1);
            case ("Left"):
                return new Offset(-1, 0);
            case ("Right"):
                return new Offset(1, 0);
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

    public Offset avoidBlocksOnUp() {
        BlockAnalizer blockAnalizer = new BlockAnalizer();

        if (blockAnalizer.blockOnTop() && !blockAnalizer.blockOnLeft()) {
            BasicMove.plusRightStepsAmount(1);
            return new Offset(-1, 0);
        }

        if (blockAnalizer.blockOnTop() && blockAnalizer.blockOnLeft() && !blockAnalizer.blockOnLeftAndUp()) {
            BasicMove.plusRightStepsAmount(1);
            return new Offset(-1, 1);
        }


        if (blockAnalizer.blockOnTop() && blockAnalizer.blockOnLeft() && blockAnalizer.blockOnLeftAndUp() && !blockAnalizer.blockOnLeftAndDown()) {
            BasicMove.plusRightStepsAmount(1);
            return new Offset(-1, -1);
        }

        if (blockAnalizer.blockOnTop() && blockAnalizer.blockOnLeft() && blockAnalizer.blockOnRight()
                && blockAnalizer.blockOnRightAndUp() && blockAnalizer.blockOnLeftAndUp()) {
            System.out.println("Стремный случай при движении вверх");
            BasicMove.changeDirectionToTheOpposite();
            return new Offset(0, -1);
        }

        if (blockAnalizer.blockOnTop() && blockAnalizer.blockOnLeft()
                && blockAnalizer.blockOnLeftAndUp() && blockAnalizer.blockOnLeftAndDown())
        {
            if (!blockAnalizer.blockOnRight() && !blockAnalizer.blockOnRightAndUp()) {
                BasicMove.minusRightStepsAmount(1);
                BasicMove.changeDirectionToTheOpposite();
                return new Offset(1, 0);
            } else {
                BasicMove.changeDirectionToTheOpposite();
                return new Offset(
                        rnd.nextInt(3) - 1,
                        rnd.nextInt(3) - 1
                );
            }
        }

        if (blockAnalizer.blockOnRight() && !blockAnalizer.blockOnRightAndUp()) {
            BasicMove.minusRightStepsAmount(1);
            return new Offset(1, 1);
        }

        return new Offset(
                rnd.nextInt(3) - 1,
                rnd.nextInt(3) - 1
        );
    }

    public Offset avoidBlocksOnDown() {
        BlockAnalizer blockAnalizer = new BlockAnalizer();

        if (blockAnalizer.blockOnDown() && !blockAnalizer.blockOnLeft()) {
            BasicMove.plusRightStepsAmount(1);
            return new Offset(-1, 0);
        }

        if (blockAnalizer.blockOnDown() && blockAnalizer.blockOnLeft() && !blockAnalizer.blockOnLeftAndDown()) {
            BasicMove.plusRightStepsAmount(1);
            return new Offset(-1, -1);
        }

        if (blockAnalizer.blockOnDown() && blockAnalizer.blockOnLeft() && blockAnalizer.blockOnLeftAndDown()
                && !blockAnalizer.blockOnRightAndDown())
        {
            BasicMove.plusRightStepsAmount(1);
            return new Offset(1, -1);
        }

        if (blockAnalizer.blockOnDown() && blockAnalizer.blockOnLeft() && blockAnalizer.blockOnLeftAndDown()
                && blockAnalizer.blockOnRightAndDown() && blockAnalizer.blockOnRight())
        {
            System.out.println("Стремный случай при движении вниз");
            BasicMove.plusRightStepsAmount(1);
            BasicMove.changeDirectionToTheOpposite();
            return new Offset(0, 1);
        }

        if (blockAnalizer.blockOnDown() && blockAnalizer.blockOnLeft() && blockAnalizer.blockOnLeftAndDown()
                && blockAnalizer.blockOnRightAndDown())
        {
            if (!blockAnalizer.blockOnRight() && !blockAnalizer.blockOnRightAndDown()) {
                BasicMove.changeDirectionToTheOpposite();
                BasicMove.minusRightStepsAmount(1);
                return new Offset(1, 0);
            } else {
                BasicMove.changeDirectionToTheOpposite();
                BasicMove.minusRightStepsAmount(1);
                return new Offset(
                        rnd.nextInt(3) - 1,
                        rnd.nextInt(3) - 1
                );
            }
        }

        if (blockAnalizer.blockOnRight() && !blockAnalizer.blockOnRightAndDown()) {
            BasicMove.minusRightStepsAmount(1);
            return new Offset(1, -1);
        }

        return new Offset(              //магия
                rnd.nextInt(3) - 1,
                rnd.nextInt(3) - 1
        );
    }


}
