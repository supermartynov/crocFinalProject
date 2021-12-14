package ru.croccode.hypernull.bot.move;

import ru.croccode.hypernull.geometry.Offset;
import ru.croccode.hypernull.geometry.Point;

import java.util.Random;
import java.util.Set;

public class BotMove {

    private static final Random rnd = new Random(System.currentTimeMillis());

    Point currentPosition = BasicMove.updateDataObject.getYourPosition();

    MoveWithoutBarriers moveWithoutBarriers = new MoveWithoutBarriers();

    BlockAnalizer blockAnalizer = new BlockAnalizer();



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


            if (!blockAnalizer.blockOnLeftAndDown()) {
                BasicMove.minusLeftStepsAmount(1);
                return new Offset(-1, -1);
            }

            if (!blockAnalizer.blockOnRightAndDown()) {
                BasicMove.plusLeftStepsAmount(1);
                return new Offset(1, -1);
            }

            if (!blockAnalizer.blockOnDown()) {
                return new Offset(0, -1);
            }

            if (!blockAnalizer.blockOnTop()) {
                BasicMove.setDirection("Down_Left");
                return new Offset(0, 1);
            }

            BasicMove.setDirection("Up");
            return new Offset(0,1);
        }

        if (BasicMove.getDirection().equals("Down_Left")) {


            if (blockAnalizer.blockOnLeftAndUp()) {
                BasicMove.minusLeftStepsAmount(1);
                return new Offset(-1, 1);
            }

            if (blockAnalizer.blockOnRightAndUp()) {
                BasicMove.plusLeftStepsAmount(1);
                return new Offset(1, 1);
            }

            if (!blockAnalizer.blockOnTop()) {
                return new Offset(0, 1);
            }

            if (!blockAnalizer.blockOnTop()) {
                BasicMove.setDirection("Up_Left");
                return new Offset(0, -1);
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
            return new Offset(-1, 0);
        }

        if (blockAnalizer.blockOnTop() && blockAnalizer.blockOnLeft() && !blockAnalizer.blockOnLeftAndUp()) {
            return new Offset(-1, 1);
        }

        if (blockAnalizer.blockOnTop() && blockAnalizer.blockOnLeft() && blockAnalizer.blockOnDown() &&
                blockAnalizer.blockOnLeftAndDown() && blockAnalizer.blockOnRightAndDown()) {
            BasicMove.setDirection("Right");
        }

        if (blockAnalizer.blockOnTop() && blockAnalizer.blockOnLeft() && blockAnalizer.blockOnLeftAndUp() && !blockAnalizer.blockOnLeftAndDown()) {
            return new Offset(-1, -1);
        }

        if (blockAnalizer.blockOnTop() && blockAnalizer.blockOnLeft() && blockAnalizer.blockOnRight()
                && blockAnalizer.blockOnRightAndUp() && blockAnalizer.blockOnLeftAndUp()) {
            BasicMove.changeDirectionToTheOpposite();
            return new Offset(0, -1);
        }

        if (blockAnalizer.blockOnTop() && blockAnalizer.blockOnLeft()
                && blockAnalizer.blockOnLeftAndUp() && blockAnalizer.blockOnLeftAndDown())
        {
            if (!blockAnalizer.blockOnRight() && !blockAnalizer.blockOnRightAndUp()) {
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
            return new Offset(-1, 0);
        }

        if (blockAnalizer.blockOnDown() && blockAnalizer.blockOnLeft() && !blockAnalizer.blockOnLeftAndDown()) {
            return new Offset(-1, -1);
        }

        if (blockAnalizer.blockOnDown() && blockAnalizer.blockOnLeft() && blockAnalizer.blockOnLeftAndDown()
                && !blockAnalizer.blockOnRightAndDown())
        {
            return new Offset(1, -1);
        }

        if (blockAnalizer.blockOnDown() && blockAnalizer.blockOnLeft() && blockAnalizer.blockOnLeftAndDown()
                && blockAnalizer.blockOnRightAndDown() && blockAnalizer.blockOnRight())
        {
            BasicMove.changeDirectionToTheOpposite();
            return new Offset(0, 1);
        }

        if (blockAnalizer.blockOnDown() && blockAnalizer.blockOnLeft() && blockAnalizer.blockOnLeftAndDown()
                && blockAnalizer.blockOnRightAndDown())
        {
            if (!blockAnalizer.blockOnRight() && !blockAnalizer.blockOnRightAndDown()) {
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

        if (blockAnalizer.blockOnRight() && !blockAnalizer.blockOnRightAndDown()) {
            return new Offset(1, -1);
        }

        return new Offset(
                rnd.nextInt(3) - 1,
                rnd.nextInt(3) - 1
        );
    }

    public Offset goToCoin(Point coin) {

        System.out.println("Расстояние до точки:");
        System.out.println(currentPosition.offsetTo(coin, BasicMove.size).length());
        System.out.println("Майнинг радиус");
        System.out.println(BasicMove.initiallyDataObject.getMiningRadius());
        if (currentPosition.offsetTo(coin, BasicMove.size).length() < BasicMove.initiallyDataObject.getViewRadius() - 1
                && currentPosition.offsetTo(coin, BasicMove.size).length() >= BasicMove.initiallyDataObject.getMiningRadius() - 1) {

            int dx = coin.x() - currentPosition.x();
            int dy = coin.y() - currentPosition.y();

            int offsetOnX = 0;
            int offsetOnY = 0;

            offsetOnX = dx > 0 ?  1 : -1;
            offsetOnY = dy > 0 ? 1 : -1;

            if (dy < 0) {
                BasicMove.setDirection("Down");
            } else if (dy > 0) {
                BasicMove.setDirection("Up");
            }

            Point point = currentPosition.apply(new Offset(offsetOnX, offsetOnY), BasicMove.size);
            if (blockAnalizer.isBlock(point)) {
                if (BasicMove.getDirection().equals("Up")) {
                    return avoidBlocksOnUp();
                } else if (BasicMove.getDirection().equals("Down")){
                    return avoidBlocksOnDown();
                }
            }
            System.out.println("Я вот тууут");


            return new Offset(offsetOnX, offsetOnY);
        }

        return moveWithoutBarriers.move();
    }

    public Offset randomOffset() {
        System.out.println(BasicMove.getDirection());
        BasicMove.changeDirectionToTheOpposite();
        System.out.println(BasicMove.getDirection());
        return new Offset(
                rnd.nextInt(3) - 1,
                rnd.nextInt(3) - 1
        );
    }


}
