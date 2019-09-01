package engine.graphics.mesh;

import engine.graphics.texture.Texture;

import java.io.IOException;
import java.util.HashMap;
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

    public Mesh getMesh(String path) throws IOException {
        Mesh result = meshContainer.get(path);
        if(result == null) {
            result = new Mesh(path);
            meshContainer.put(path, result);
        }
        return result;
    }

    //    ----------------------------------------------------------------------

    private Map<String, Mesh> meshContainer = new HashMap<>();


}
