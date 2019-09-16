package engine.utils;

public class Time {
    private static int framesCounter=0;
    private static long startTick;
    private static long endTick;
    private static long time_elapsed=0;
    public static long TIME_ELAPSED=0;
    public static long DELTA_TIME=0;

    //Mówi o precyzji pomiaru
    private final static long precision = 1_000_000_000;

    //Dajemy na poczatku petli
    public static void startTickFPS(){
        startTick = System.nanoTime();
    }

    //Dajemy na koncu petli
    public static void endTickFPS(){
        endTick = System.nanoTime();
        DELTA_TIME = (endTick - startTick)/(1000*1000);
        TIME_ELAPSED += DELTA_TIME;
        time_elapsed += DELTA_TIME;
    }


    public static void showFPS(){

        if (time_elapsed>precision){
            System.out.println("FPS:   "+(float) framesCounter/time_elapsed * precision);
            time_elapsed=0;
            framesCounter=0;
        }else{
            time_elapsed += endTick-startTick;
            framesCounter++;
        }

    }
}
