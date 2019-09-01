package engine.graphics.material;

import engine.graphics.mesh.Mesh;
import engine.graphics.texture.Texture;

import java.io.IOException;
import java.util.HashMap;
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

    public Material getMaterial(String path) throws IOException {
        Material result = materialContainer.get(path);
        if(result == null) {
            result = new Material(path);
            materialContainer.put(path, result);
        }
        return result;
    }

    //    ----------------------------------------------------------------------

    private Map<String, Material> materialContainer = new HashMap<>();



}
