package engine.graphics.shader;

import java.util.ArrayList;
import java.util.List;

public class ShaderManager {

    private static volatile ShaderManager managerInstance;

    //private constructor.
    private ShaderManager(){
        //Prevent form the reflection api.
        if (managerInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static ShaderManager getInstance() {
        //Double check locking pattern
        if (managerInstance == null) { //Check for the first time

            synchronized (ShaderManager.class) {   //Check for the second time.
                //if there is no instance available... create new one
                if (managerInstance == null) managerInstance = new ShaderManager();
            }
        }

        return managerInstance;
    }

    public ShaderProgram getShader(String vertex, String fragment) throws Exception {
        return getShader(vertex, fragment, null);
    }

    public ShaderProgram getShader(String vertex, String fragment, String geometry) throws Exception {
        ShaderProgram result = null;
        for(ShaderProgram shaderProgram : shaderContainer) {
            if(shaderProgram.getVertexPath().equals(vertex) && shaderProgram.getFragmentPath().equals(fragment)) {
                result = shaderProgram;
            }
        }
        if(result == null) {
            result = new ShaderProgram(vertex, fragment, geometry);
            shaderContainer.add(result);
        }
        return result;
    }

    public ShaderProgram getStandardShader() throws Exception {
        return getShader("glsl/standard/v_standard.glsl", "glsl/standard/f_standard.glsl");
    }

    public ShaderProgram getNormalMappingShader() throws Exception {
        return getShader("glsl/normalMapping/v_normal.glsl", "glsl/normalMapping/f_normal.glsl");
    }

    public ShaderProgram getConstantShader() throws Exception {
        return getShader("glsl/constant/v_constant.glsl", "glsl/constant/f_constant.glsl");
    }

    private List<ShaderProgram> shaderContainer = new ArrayList<>();

}
