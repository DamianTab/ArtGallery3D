package engine.graphics.texture;

import engine.graphics.material.MaterialFile;
import lombok.Getter;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//todo
public class TextureManager {

    //Singletone Implementation
    private static volatile TextureManager managerInstance;
    private List<Texture> textureContainer = new ArrayList<>();
    @Getter
    private Texture defaultDiffuse;
    @Getter
    private Texture defaultSpeculart;

    //private constructor.
    private TextureManager(){
        //Prevent form the reflection api.
        if (managerInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        try {
            this.defaultDiffuse = getTexture("defaultTextures/diffuse.png", Texture.Type.DIFFUSE);
            this.defaultSpeculart = getTexture("defaultTextures/specular.png", Texture.Type.SPECULAR);
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

    public Texture getTexture(String path, Texture.Type type) throws IOException {
        Texture result = null;
        for(Texture tex : textureContainer) {
            if(tex.getPath().equals(path) && tex.getType() == type) {
                result = tex;
            }
        }
        if(result == null) {
            result = new Texture(path, type);
            textureContainer.add(result);
        }
        return result;
    }

    //    ----------------------------------------------------------------------
}
