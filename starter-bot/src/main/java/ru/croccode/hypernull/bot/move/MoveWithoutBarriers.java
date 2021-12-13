package ru.croccode.hypernull.bot.move;

import ru.croccode.hypernull.geometry.Offset;

public class MoveWithoutBarriers {

    public Offset move() {
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
}
