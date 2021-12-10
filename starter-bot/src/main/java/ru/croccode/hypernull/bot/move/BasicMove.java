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

    static Point lastPoint;

    static int counter = 0;

    static

    private Size mapSize;

    private static String direction = "Up";

    private static boolean skipCheckBorder = false;  //флаг нужен для разворота в обратную сторону

    private static int stepLeftAmountToChangeDirection = 0;


    public BasicMove(UpdateDataObject updateDataObject, InitiallyDataObject initiallyDataObject) {
        this.updateDataObject = updateDataObject;
        this.initiallyDataObject = initiallyDataObject;
        mapSize = new Size(initiallyDataObject.getMapWidth(), initiallyDataObject.getMapHeight());
    }

    public Offset go() {
        moveWithoutBarriers = new MoveWithoutBarriers();
        blockAnalizer = new BlockAnalizer();
        Offset offset;

        if (blockAnalizer.isTopOrDownBorder() && !skipCheckBorder) {//если у нас достигнута верхняя или нижняя граница
            blockAnalizer.changeDirectionToTheLeft();//включаем режим смещения налево на одну клетку
        }
//kt
        while (stepLeftAmountToChangeDirection > 0) { //пока не доберемся до столбца левее пробуем смещаться
            try {
                offset = MoveOnLeft.moveOnLeftToChangeDirection(); //передвижение налево не зафиксировано
                Point point = updateDataObject.getYourPosition().apply(offset, mapSize);
                initiallyDataObject.getPointHistoryArray()[point.x()][point.y()] = Visited.VISITED;
                if (stepLeftAmountToChangeDirection == 0) {
                    return blockAnalizer.changeDirectionToVerticalWays(); //передвижение налево и вниз/ввер
                }
                return offset;
            } catch (Exception err) {
                return new Offset(-1, 0);
            }

        }
        return moveWithoutBarriers.move();
    }

    public static Visited isPointVisited(Point point) {
        if (initiallyDataObject.getPointHistoryArray()[point.x()][point.y()] != null) {
            return initiallyDataObject.getPointHistoryArray()[point.x()][point.y()];
        } else return null;
    }

    private void addVisitedPoint(Visited vis, Point point) {
        initiallyDataObject.getPointHistoryArray()[point.x()][point.y()] = vis;
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

    public static void minusLeftStepsAmount(int n) {
        stepLeftAmountToChangeDirection -= n;
    }

    public static boolean isSkipCheckBorder() {
        return skipCheckBorder;
    }

    public static void setSkipCheckBorder(boolean skipCheckBorder) {
        BasicMove.skipCheckBorder = skipCheckBorder;
    }

}
