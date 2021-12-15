package ru.croccode.hypernull.bot.move;

import ru.croccode.hypernull.geometry.Point;
import ru.croccode.hypernull.geometry.Size;

import java.util.Set;

public class CoinAnalizer {

    private Point yourPosition = BasicMove.updateDataObject.getYourPosition();

    private Set<Point> coins = BasicMove.updateDataObject.getCoins();

    private Size size = BasicMove.size;

    private Point targetCoin;

    public boolean areThereAnyCoins() {
        return coins != null;
    }

    public Point getTargetCoin() {
        if (coins == null) {
            return null;
        }
        double minRadius = 100;
        for (Point coin : coins) {
            double distance = yourPosition.offsetTo(coin, size).length();
            if (minRadius >= distance) {
                minRadius = distance;
            }
            targetCoin = coin;
        }
        return targetCoin;
    }
}
