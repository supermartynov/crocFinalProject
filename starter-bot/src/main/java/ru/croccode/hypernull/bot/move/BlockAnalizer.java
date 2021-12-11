package ru.croccode.hypernull.bot.move;

import ru.croccode.hypernull.geometry.Offset;
import ru.croccode.hypernull.geometry.Point;

import java.util.Set;

public class BlockAnalizer {

    Point currentPoint = BasicMove.updateDataObject.getYourPosition();

    Set<Point> blocks = BasicMove.updateDataObject.getBlocks();


    public boolean blockOnLeft() {
        if (blocks != null && blocks.contains(new Point(currentPoint.x() - 1, currentPoint.y()))) {
            return true;
        }
        return false;
    }

    public boolean blockOnTop() {
        if (blocks != null && blocks.contains(new Point(currentPoint.x(), currentPoint.y() + 1))) {
            return true;
        }
        return false;
    }

    public boolean blockOnDown() {
        if (blocks != null && blocks.contains(new Point(currentPoint.x(), currentPoint.y() - 1))) {
            return true;
        }
        return false;
    }

    public boolean blockOnRightAndUp() {
        if (blocks.contains(new Point(currentPoint.x() + 1, currentPoint.y() + 1))) {
            return true;
        }
        return false;
    }

    public boolean blockOnLeftAndUp() {
        if (blocks.contains(new Point(currentPoint.x() - 1, currentPoint.y() + 1))) {
            return true;
        }
        return false;
    }

    public boolean blockOnLeftAndDown() {
        if (blocks.contains(new Point(currentPoint.x() - 1, currentPoint.y() - 1))) {
            return true;
        }
        return false;
    }

    public boolean blockOnRightAndDown() {
        if (blocks.contains(new Point(currentPoint.x() + 1, currentPoint.y() - 1))) {
            return true;
        }
        return false;
    }


    public boolean isTopOrDownBorder() {
        if (currentPoint.y() == BasicMove.initiallyDataObject.getMapHeight() - 1) {
            return true;
        } else if (currentPoint.y() == 0) {
            return true;
        }
        return false;
    }

}
