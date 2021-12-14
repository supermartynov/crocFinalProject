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

            if (!blockAnalizer.blockOnDown()) {

                return new Offset(0, -1);
            }

            if (!blockAnalizer.blockOnLeftAndDown()) {
                BasicMove.minusLeftStepsAmount(1);
                return new Offset(-1, -1);
            }

            if (!blockAnalizer.blockOnRightAndDown()) {
                BasicMove.plusLeftStepsAmount(1);
                return new Offset(1, -1);
            }

            BasicMove.setDirection("Up");
            return new Offset(0,1);
        }

        if (BasicMove.getDirection().equals("Down_Left")) {

            if (!blockAnalizer.blockOnTop()) {
                return new Offset(0, 1);
            }

            if (blockAnalizer.blockOnLeftAndUp()) {
                BasicMove.minusLeftStepsAmount(1);
                return new Offset(-1, 1);
            }

            if (blockAnalizer.blockOnRightAndUp()) {
                BasicMove.plusLeftStepsAmount(1);
                return new Offset(1, 1);
            }

            BasicMove.setDirection("Down");
            return new Offset(0,-1);

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

        if (currentPosition.offsetTo(coin, BasicMove.size).length() < BasicMove.initiallyDataObject.getViewRadius()
                && currentPosition.offsetTo(coin, BasicMove.size).length() >= BasicMove.initiallyDataObject.getMiningRadius()) {

            int dx = coin.x() - currentPosition.x();
            int dy = coin.y() - currentPosition.y();

            int offsetOnX = 0;
            int offsetOnY = 0;

            offsetOnX = dx > 0 ?  1 : -1;
            offsetOnY = dy > 0 ? 1 : -1;

            Point point = currentPosition.apply(new Offset(offsetOnX, offsetOnY), BasicMove.size);
            if (blockAnalizer.isBlock(point)) {
                if (BasicMove.getDirection().equals("Up")) {
                    return avoidBlocksOnUp();
                } else if (BasicMove.getDirection().equals("Down")){
                    return avoidBlocksOnDown();
                }
            }
            System.out.println("Вот позиция наша");
            System.out.println(BasicMove.updateDataObject.getYourPosition());
            System.out.println("Вот сюда он говорит смещаться");
            System.out.println(new Point(offsetOnX, offsetOnY));
            System.out.println("Ниже видимвые поля");
            System.out.println(BasicMove.updateDataObject.getBlocks());
            System.out.println("Выше видимые блоки");
            System.out.println(BasicMove.getDirection());
            System.out.println("Выше направление");
            System.out.println(blockAnalizer.isBlock(point));
            System.out.println("Есть ли блок на смещении - " + point);
            System.out.println("------------------------------------");
            return new Offset(offsetOnX, offsetOnY);
        }

        BasicMove.targetCoin = null;
        return moveWithoutBarriers.move();
    }

    public Offset randomOffset() {
        BasicMove.changeDirectionToTheOpposite();
        return new Offset(
                rnd.nextInt(3) - 1,
                rnd.nextInt(3) - 1
        );
    }


}
