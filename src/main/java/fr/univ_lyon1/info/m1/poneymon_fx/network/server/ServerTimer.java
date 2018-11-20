package fr.univ_lyon1.info.m1.poneymon_fx.network.server;

public class ServerTimer {

    private long timeStart = 0;
    private long timeEnd = 0;
    private long time = 0;

    public void start() {
        timeStart = System.nanoTime();
        timeEnd = 0;
        time = 0;
    }

    public void stop() {
        if (timeStart == 0) {
            return;
        }
        timeEnd = System.nanoTime();
        time = (timeEnd - timeStart);
        timeStart = 0;
        timeEnd = 0;
    }

    public long getTimeNano() {
        return time;
    }
}
