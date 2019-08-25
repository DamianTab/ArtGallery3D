package engine.graphics.mesh;

import engine.graphics.texture.Texture;

import java.util.Map;

public class MeshManager {

    //Singletone Implementation
    private static volatile MeshManager managerInstance;

    //private constructor.
    private MeshManager(){
        //Prevent form the reflection api.
        if (managerInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }


    public static MeshManager getInstance() {
        //Double check locking pattern
        if (managerInstance == null) { //Check for the first time

            synchronized (MeshManager.class) {   //Check for the second time.
                //if there is no instance available... create new one
                if (managerInstance == null) managerInstance = new MeshManager();
            }
        }

        return managerInstance;
    }

    //    ----------------------------------------------------------------------

    private Map<String, Texture> meshContainer;


}
