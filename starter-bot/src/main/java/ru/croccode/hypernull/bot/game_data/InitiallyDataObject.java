package ru.croccode.hypernull.bot.game_data;

public class InitiallyDataObject {
    private final int mapWidth;

    private final int mapHeight;

    private final int botId;

    private final int viewRadius;

    private final int miningRadius;

    private Visited[][] pointHistoryArray;

    private int[] visitedColumns;

    public InitiallyDataObject(int mapWidth, int mapHeight, int botId, int viewRadius, int miningRadius)
    {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.botId = botId;
        this.viewRadius = viewRadius;
        this.miningRadius = miningRadius;
        initArray();
        this.visitedColumns = new int[mapWidth];
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
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                pointHistoryArray[i][j] = Visited.NOT_VISITED;
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
