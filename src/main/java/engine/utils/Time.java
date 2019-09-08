package engine.utils;

public class Time {
    private static int framesCounter=0;
    private static long startTick;
    private static long endTick;
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
    }


    public static void showFPS(){

        if (TIME_ELAPSED>precision){
            System.out.println("FPS:   "+(float) framesCounter/TIME_ELAPSED * precision);
            TIME_ELAPSED=0;
            framesCounter=0;
        }else{
            TIME_ELAPSED += endTick-startTick;
            framesCounter++;
        }

    }
}
