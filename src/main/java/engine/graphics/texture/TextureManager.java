package engine.graphics.texture;

import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//todo
public class TextureManager {

    //Singletone Implementation
    private static volatile TextureManager managerInstance;
    private List<FileTexture> textureContainer = new ArrayList<>();
    @Getter
    private FileTexture defaultDiffuse;
    @Getter
    private FileTexture defaultSpeculart;

    //private constructor.
    private TextureManager(){
        //Prevent form the reflection api.
        if (managerInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        try {
            this.defaultDiffuse = getTexture("defaultTextures/diffuse.png", FileTexture.Type.DIFFUSE);
            this.defaultSpeculart = getTexture("defaultTextures/specular.png", FileTexture.Type.SPECULAR);
        } catch (IOException e) {
            System.err.println("Failed to load defualt textures!");
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

    public FileTexture getTexture(String path, FileTexture.Type type) throws IOException {
        FileTexture result = null;
        for(FileTexture tex : textureContainer) {
            if(tex.getPath().equals(path) && tex.getType() == type) {
                result = tex;
            }
        }
        if(result == null) {
            result = new FileTexture(path, type);
            textureContainer.add(result);
        }
        return result;
    }

    private int depthID = 0;
    public DepthCubeMap nextDepthMap() {
        DepthCubeMap result = new DepthCubeMap(depthID);
        depthID++;
        return result;
    }

    //    ----------------------------------------------------------------------
}
