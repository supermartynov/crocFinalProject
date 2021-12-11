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

    static int counter = 0;

    static int lastColumn = 0;

    private Size mapSize;

    private static String direction = "Up";

    private static int stepLeftAmountToChangeDirection = 0;


    public BasicMove(UpdateDataObject updateDataObject, InitiallyDataObject initiallyDataObject) {
        this.updateDataObject = updateDataObject;
        this.initiallyDataObject = initiallyDataObject;
        mapSize = new Size(initiallyDataObject.getMapWidth(), initiallyDataObject.getMapHeight());
        moveWithoutBarriers = new MoveWithoutBarriers();
        blockAnalizer = new BlockAnalizer();
        botMove = new BotMove();
    }

    public Offset go() {
        Offset offset;

        if (blockAnalizer.isTopOrDownBorder()) {//если у нас достигнута верхняя или нижняя граница
            botMove.changeDirectionToTheLeft();
            //включаем режим смещения налево на одну клетку
        }

        while (stepLeftAmountToChangeDirection > 0) { //пока не доберемся до столбца левее пробуем смещаться
            offset = botMove.moveOnLeftToChangeDirection(); //передвижение налево не зафиксировано
            if (stepLeftAmountToChangeDirection == 0) {
                Offset anotherOffset = botMove.changeDirectionToVerticalWays();
                Point point = updateDataObject.getYourPosition().apply(anotherOffset, mapSize);
                initiallyDataObject.getPointHistoryArray()[point.x()][point.y()] = Visited.VISITED;
                return anotherOffset; //передвижение налево и вниз/ввер
            }
            return offset;
        }
        return botMove.moveWithoutBarrires();
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

}
