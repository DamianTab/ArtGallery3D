package engine.graphics.texture;

import java.util.Map;

//todo
public class TextureManager {

    //Singletone Implementation
    private static volatile TextureManager managerInstance;

    //private constructor.
    private TextureManager(){
        //Prevent form the reflection api.
        if (managerInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }


    public static TextureManager getInstance() {
        //Double check locking pattern
        if (managerInstance == null) { //Check for the first time

            synchronized (TextureManager.class) {   //Check for the second time.
                //if there is no instance available... create new one
                if (managerInstance == null) managerInstance = new TextureManager();
            }
        }

        return managerInstance;
    }

    //    ----------------------------------------------------------------------

    private Map<String, Texture> textureContainer;
}
