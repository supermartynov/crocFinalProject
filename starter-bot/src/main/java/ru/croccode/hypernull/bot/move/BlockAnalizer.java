package ru.croccode.hypernull.bot.move;

import ru.croccode.hypernull.geometry.Offset;
import ru.croccode.hypernull.geometry.Point;

import java.util.Random;
import java.util.Set;

public class BlockAnalizer {

    Point currentPoint = BasicMove.updateDataObject.getYourPosition();

    Set<Point> blocks = BasicMove.updateDataObject.getBlocks();

    public int couner = 0;

    boolean isTopOrDownBorder() {
        if (currentPoint.y() == BasicMove.initiallyDataObject.getMapHeight() - 1) {
            return true;
        } else if (currentPoint.y() == 0) {
            return true;
        }
        return false;
    }

    boolean blockOnLeft() {
        if (blocks != null && blocks.contains(new Point(currentPoint.x() - 1, currentPoint.y()))) {
            return true;
        }
        return false;
    }

    boolean blockOnTop() {
        if (blocks != null && blocks.contains(new Point(currentPoint.x(), currentPoint.y() + 1))) {
            return true;
        }
        return false;
    }

    boolean blockOnDown() {
        if (blocks != null && blocks.contains(new Point(currentPoint.x(), currentPoint.y() - 1))) {
            return true;
        }
        return false;
    }

    boolean blockOnRightAndUp() {
        if (blocks.contains(new Point(currentPoint.x() + 1, currentPoint.y() + 1))) {
            return true;
        }
        return false;
    }

    boolean blockOnLeftAndUp() {
        if (blocks.contains(new Point(currentPoint.x() - 1, currentPoint.y() + 1))) {
            return true;
        }
        return false;
    }

    boolean blockOnLeftAndDown() {
        if (blocks != null && blocks.contains(new Point(currentPoint.x() - 1, currentPoint.y() - 1))) {
            return true;
        }
        return false;
    }

    boolean blockOnRightAndDown() {
        if (blocks != null && blocks.contains(new Point(currentPoint.x() + 1, currentPoint.y() - 1))) {
            return true;
        }
        return false;
    }

    boolean blockOnRight() {
        if (blocks != null && blocks.contains(new Point(currentPoint.x() + 1, currentPoint.y()))) {
            return true;
        }
        return false;
    }

    boolean isBlock(Point pointToCheck) {
        if (blocks == null) {
            return false;
        }
        for (Point block : blocks) {
            if (pointToCheck.equals(block)) {
                return true;
            }
        }
        return false;
    }

}
