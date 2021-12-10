package ru.croccode.hypernull.bot.game_data;

import ru.croccode.hypernull.geometry.Point;
import ru.croccode.hypernull.message.Update;

import java.util.Map;
import java.util.Set;

public class UpdateDataObject {
    private Set<Point> coins;

    private Map<Integer, Point> bots;

    private Set<Point> blocks;

    private Point yourPosition;


    public UpdateDataObject(Update update, int yourId) {
        coins = update.getCoins();
        bots = update.getBots();
        blocks = update.getBlocks();
        yourPosition = update.getBots().get(yourId);
    }

    public UpdateDataObject() {
    }

    public void resetData(Update update, int yourId) {
        coins = update.getCoins();
        bots = update.getBots();
        blocks = update.getBlocks();
        yourPosition = update.getBots().get(yourId);
    }

    public Set<Point> getCoins() {
        return coins;
    }

    public Map<Integer, Point> getBots() {
        return bots;
    }

    public Set<Point> getBlocks() {
        return blocks;
    }

    public Point getYourPosition() {
        return yourPosition;
    }

    @Override
    public String toString() {
        return "UpdateDataObject{" +
                "coins=" + coins +
                ", bots=" + bots +
                ", blocks=" + blocks +
                ", yourPosition=" + yourPosition +
                '}';
    }
}
