package engine;

public class Time {
    private static int framesCounter=0;
    private static long timeElapsed=0;
    private static long startTick;
    private static long endTick;

    //Mówi o precyzji pomiaru
    private final static long precision = 1_000_000_000;

    //Dajemy na poczatku petli
    public static void startTickFPS(){
        startTick = System.nanoTime();
    }

    //Dajemy na koncu petli
    public static void endTickFPS(){
        startTick = System.nanoTime();
    }


    public static void showFPS(){

        if (timeElapsed>precision){
            System.out.println("FPS:   "+(float) framesCounter/timeElapsed * precision);
            timeElapsed=0;
            framesCounter=0;
        }else{
            timeElapsed += endTick-startTick;
            framesCounter++;
        }

    }

    public static long getDeltaTime(){
        return (endTick - startTick) * precision;
    }
}
