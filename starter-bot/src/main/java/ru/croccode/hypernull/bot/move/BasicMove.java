package ru.croccode.hypernull.bot.move;

import ru.croccode.hypernull.bot.game_data.InitiallyDataObject;
import ru.croccode.hypernull.bot.game_data.UpdateDataObject;
import ru.croccode.hypernull.bot.game_data.Visited;
import ru.croccode.hypernull.geometry.Offset;
import ru.croccode.hypernull.geometry.Point;
import ru.croccode.hypernull.geometry.Size;

public class BasicMove {

    static InitiallyDataObject initiallyDataObject;

    static UpdateDataObject updateDataObject;

    static BlockAnalizer blockAnalizer;

    static MoveWithoutBarriers moveWithoutBarriers;

    static BotMove botMove;

    private Size mapSize;

    private static String direction = "Up";

    private static int stepLeftAmountToChangeDirection = 0;

    private static int stepRightAmountToChangeDirection = 0;


    public BasicMove(UpdateDataObject updateDataObject, InitiallyDataObject initiallyDataObject) {
        this.updateDataObject = updateDataObject;
        this.initiallyDataObject = initiallyDataObject;
        mapSize = new Size(initiallyDataObject.getMapWidth(), initiallyDataObject.getMapHeight());
        moveWithoutBarriers = new MoveWithoutBarriers();
        blockAnalizer = new BlockAnalizer();
        botMove = new BotMove();
    }

    public static int getStepRightAmountToChangeDirection() {
        return stepRightAmountToChangeDirection;
    }

    public Offset go() {
        setDataInMapArray();

        if (blockAnalizer.isTopOrDownBorder()) {//если у нас достигнута верхняя или нижняя граница
            botMove.changeDirectionToTheLeft();
        }

        if (direction.equals("Up") && blockAnalizer.blockOnTop()) {
            return botMove.avoidBlocksOnUp();
        } else if (direction.equals("Down") && blockAnalizer.blockOnDown()) {
            return botMove.avoidBlocksOnDown();
        }

        while (stepLeftAmountToChangeDirection > 0) { //пока не доберемся до столбца левее пробуем смещаться
            Offset offset = botMove.moveOnLeftToChangeDirection(); //передвижение налево не зафиксировано
            if (stepLeftAmountToChangeDirection == 0) {
                Offset anotherOffset = botMove.changeDirectionToVerticalWays();
                return anotherOffset; //передвижение налево и вниз/ввер
            }
            return offset;
        }
        return botMove.moveWithoutBarrires();
    }

    public static String getDirection() {
        return direction;
    }

    public static void setDirection(String direction) {
        BasicMove.direction = direction;
    }

    public static void plusLeftStepsAmount(int n) {
        stepLeftAmountToChangeDirection += n;
    }

    public static void plusRightStepsAmount(int n) {
        stepRightAmountToChangeDirection += n;
    }

    public static void minusLeftStepsAmount(int n) {
        stepLeftAmountToChangeDirection -= n;
    }

    public static void minusRightStepsAmount(int n) {
        stepRightAmountToChangeDirection -= n;
    }

    public static void changeDirectionToTheOpposite() {
        if (direction.equals("Up")) {
            direction = "Down";
        } else if (direction.equals("Down")) {
            direction = "Up";
        }
    }

    public void setDataInMapArray() {

        if (updateDataObject.getBlocks() != null) {
            for (Point block : updateDataObject.getBlocks()) {
                if (initiallyDataObject.getPointHistoryArray()[block.x()][block.y()] == Visited.NOT_VISITED) {
                    initiallyDataObject.getPointHistoryArray()[block.x()][block.y()] = Visited.BlOCK;
                }
            }
        }

        int x = updateDataObject.getYourPosition().x();
        int y = updateDataObject.getYourPosition().y();
        initiallyDataObject.getPointHistoryArray()[x][y] = Visited.VISITED;

        if (updateDataObject.getCoins() != null) {
            for (Point coin: updateDataObject.getCoins()) {
                if (initiallyDataObject.getPointHistoryArray()[coin.x()][coin.y()] == Visited.NOT_VISITED ) {
                    initiallyDataObject.getPointHistoryArray()[coin.x()][coin.y()] = Visited.COIN;
                }
            }
        }
    }


}
