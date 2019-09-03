package engine.graphics.material;

import lombok.Getter;

public class SingleMaterial extends Material {

    @Getter
    private MaterialPart material;

    public SingleMaterial( MaterialPart single) {
        material = single;
    }
}
