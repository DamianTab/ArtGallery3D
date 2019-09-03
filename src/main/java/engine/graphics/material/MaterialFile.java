package engine.graphics.material;

import engine.graphics.texture.Texture;
import engine.graphics.texture.TextureManager;
import lombok.Getter;
import org.joml.Vector3f;

import java.io.*;
import java.nio.file.Paths;

public class MaterialFile extends Material {

    @Getter
    private String path;

    public MaterialFile(String path) throws IOException {
        this.path = path;
        init();
    }

    private void init() throws IOException {
        //Parse mtl
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        MaterialPart part = null;
        String partName = null;
        while ((line = bufferedReader.readLine()) != null) {
            line = line.trim();
            String[] split = line.split(" ");
            String identifier = split[0];
            if (identifier.equals("newmtl")) {
                if (part != null) {
                    parts.put(partName, part);
                }
                part = new MaterialPart();
                partName = split[1];
            } else if (identifier.equals("Ka")) {
                part.setAmbientColor(readColor(split));
            } else if (identifier.equals("Kd")) {
                part.setDiffuseColor(readColor(split));
            } else if (identifier.equals("Ks")) {
                part.setSpecularColor(readColor(split));
            } else if (identifier.equals("map_Ka")) {
                part.setAmbientMap(readTexture(split, Texture.Type.AMBIENT));
            } else if (identifier.equals("map_Kd")) {
                part.setDiffuseMap(readTexture(split, Texture.Type.DIFFUSE));
            } else if (identifier.equals("map_Ks")) {
                part.setSpecularMap(readTexture(split, Texture.Type.SPECULAR));
            } else if (identifier.equals("Ns")) {
                part.setShininess(Float.parseFloat(split[1]));
            }
        }
        if (part != null) {
            parts.put(partName, part);
        }
    }

    private Vector3f readColor(String[] split) {
        float x = Float.parseFloat(split[1]);
        float y = Float.parseFloat(split[2]);
        float z = Float.parseFloat(split[3]);
        return new Vector3f(x, y, z);
    }

    private Texture readTexture(String[] split, Texture.Type type) throws IOException {
        String texName = split[1];
        String parent = Paths.get(path).getParent().toString();
        String texPath = parent + File.separatorChar + texName;
        return TextureManager.getInstance().getTexture(texPath, type);
    }
}
