package ru.croccode.hypernull.bot.move;

import ru.croccode.hypernull.bot.game_data.InitiallyDataObject;
import ru.croccode.hypernull.bot.game_data.UpdateDataObject;
import ru.croccode.hypernull.bot.game_data.Visited;
import ru.croccode.hypernull.geometry.Offset;
import ru.croccode.hypernull.geometry.Point;
import ru.croccode.hypernull.geometry.Size;

import java.util.Set;

public class BasicMove {

    static InitiallyDataObject initiallyDataObject;

    static UpdateDataObject updateDataObject;

    static BlockAnalizer blockAnalizer;

    static MoveWithoutBarriers moveWithoutBarriers;

    static CoinAnalizer coinAnalizer;

    static BotMove botMove;

    static Size size;

    public static Point targetCoin;

    Set<Point> coins;

    private static String direction = "Up";

    private static int stepLeftAmountToChangeDirection = 0;


    public BasicMove(UpdateDataObject updateDataObject, InitiallyDataObject initiallyDataObject) {
        this.updateDataObject = updateDataObject;
        this.initiallyDataObject = initiallyDataObject;
        this.size = new Size(initiallyDataObject.getMapWidth(), initiallyDataObject.getMapHeight());
        size = new Size(initiallyDataObject.getMapWidth(), initiallyDataObject.getMapHeight());
        moveWithoutBarriers = new MoveWithoutBarriers();
        blockAnalizer = new BlockAnalizer();
        coinAnalizer = new CoinAnalizer();
        botMove = new BotMove();
    }

    public Offset go() {
        setDataInMapArray();
        coins = updateDataObject.getCoins();

        if(magic(updateDataObject.getYourPosition())) {
            return botMove.randomOffset();
        }

        if (blockAnalizer.isTopOrDownBorder()) {
            botMove.changeDirectionToTheLeft();
            while (stepLeftAmountToChangeDirection > 0) {
                Offset offset = botMove.moveOnLeftToChangeDirection(); //передвижение налево не зафиксировано
                if (stepLeftAmountToChangeDirection == 0) {
                    Offset anotherOffset = botMove.changeDirectionToVerticalWays();
                    return anotherOffset; //передвижение налево и вниз/ввер
                }
                return offset;
            }
        }

        if (blockAnalizer.blockOnLeft() && blockAnalizer.blockOnTop() && blockAnalizer.blockOnDown()
                && direction.equals("Left")) {
            direction.equals("Right");
        }

        if (blockAnalizer.blockOnDown() && blockAnalizer.blockOnRight() && blockAnalizer.blockOnLeft()
                && direction.equals("Down")) {
            direction.equals("Up");
        }

        if (direction.equals("Up") && blockAnalizer.blockOnTop()) {
            return botMove.avoidBlocksOnUp();
        } else if (direction.equals("Down") && blockAnalizer.blockOnDown()) {
            return botMove.avoidBlocksOnDown();
        } else if (direction.equals("Right") && blockAnalizer.blockOnRight()) {
            return botMove.avoidBlocksOnUp();
        }

        if (coinAnalizer.areThereAnyCoins()) {
            coins = updateDataObject.getCoins();
            targetCoin = coinAnalizer.getTargetCoin();
            return botMove.goToCoin(targetCoin);
        }

        if (coins != null && !coins.contains(targetCoin)) {
            targetCoin = null;
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

    public static void minusLeftStepsAmount(int n) {
        stepLeftAmountToChangeDirection -= n;
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
        initiallyDataObject.visitedArray[x][y] += 1;

        if (updateDataObject.getCoins() != null) {
            for (Point coin: updateDataObject.getCoins()) {
                if (initiallyDataObject.getPointHistoryArray()[coin.x()][coin.y()] == Visited.NOT_VISITED ) {
                    initiallyDataObject.getPointHistoryArray()[coin.x()][coin.y()] = Visited.COIN;
                }
            }
        }
    }

    public boolean magic(Point point) {
        int x = point.x();
        int y = point.y();
        if (initiallyDataObject.visitedArray[x][y] > 3) {
            initiallyDataObject.visitedArray[x][y] = 0;
            return true;
        }
        return false;
    }

}
