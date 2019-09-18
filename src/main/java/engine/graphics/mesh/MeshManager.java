package engine.graphics.mesh;

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

    public MeshFile getMesh(String path) throws IOException {
        MeshFile result = meshContainer.get(path);
        if(result == null) {
            result = new MeshFile(path);
            meshContainer.put(path, result);
        }
        return result;
    }

    public PlaneMesh getPlaneMesh() {
        if(planeMesh == null) {
            planeMesh = new PlaneMesh();
        }
        return planeMesh;
    }

    private Map<String, MeshFile> meshContainer = new HashMap<>();
    private PlaneMesh planeMesh;

}
