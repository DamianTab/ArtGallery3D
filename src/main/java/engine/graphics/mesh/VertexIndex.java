package engine.graphics.mesh;

import lombok.Data;

@Data
public class VertexIndex {

    private int positionId;
    private int normalId;
    private int uvId;

    public VertexIndex(String line) {
        String[] split = line.split("/");
        positionId = Integer.parseInt(split[0]);
        uvId = Integer.parseInt(split[1]);
        normalId = Integer.parseInt(split[2]);
    }

    public boolean equals(VertexIndex other) {
        return positionId == other.getPositionId() && normalId == other.getNormalId() && uvId == other.getUvId();
    }
}
