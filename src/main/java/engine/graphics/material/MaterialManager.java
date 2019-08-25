package engine.graphics.material;

import engine.graphics.texture.Texture;

import java.util.Map;

public class MaterialManager {

    //Singletone Implementation
    private static volatile MaterialManager managerInstance;

    //private constructor.
    private MaterialManager(){
        //Prevent form the reflection api.
        if (managerInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }


    public static MaterialManager getInstance() {
        //Double check locking pattern
        if (managerInstance == null) { //Check for the first time

            synchronized (MaterialManager.class) {   //Check for the second time.
                //if there is no instance available... create new one
                if (managerInstance == null) managerInstance = new MaterialManager();
            }
        }

        return managerInstance;
    }

    //    ----------------------------------------------------------------------

    private Map<String, Texture> materialContainer;



}
