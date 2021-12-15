package ru.croccode.hypernull.bot.game_data;

import ru.croccode.hypernull.message.MatchStarted;

public class InitiallyDataObject {
    private final int mapWidth;

    private final int mapHeight;

    private final int botId;

    private final int viewRadius;

    private final int miningRadius;

    private Visited[][] pointHistoryArray;

    public int[][] visitedArray;

    public InitiallyDataObject(MatchStarted matchStarted)
    {
        this.mapWidth = matchStarted.getMapSize().width();
        this.mapHeight = matchStarted.getMapSize().height();
        this.botId = matchStarted.getYourId();
        this.viewRadius = matchStarted.getViewRadius();
        this.miningRadius = matchStarted.getMiningRadius();
        initArray();
    }


    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getBotId() {
        return botId;
    }

    public int getViewRadius() {
        return viewRadius;
    }

    public int getMiningRadius() {
        return miningRadius;
    }

    public Visited[][] getPointHistoryArray() {
        return pointHistoryArray;
    }

    public void initArray() {
        this.pointHistoryArray = new Visited[mapWidth][mapHeight];
        this.visitedArray = new int[mapWidth][mapHeight];
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                pointHistoryArray[i][j] = Visited.NOT_VISITED;
                visitedArray[i][j] = 0;
            }
        }
    }

    @Override
    public String toString() {
        return "InitiallyDataObject{" +
                "mapWidth=" + mapWidth +
                ", mapHeight=" + mapHeight +
                ", botId=" + botId +
                ", viewRadius=" + viewRadius +
                ", miningRadius=" + miningRadius +
                '}';
    }

}
