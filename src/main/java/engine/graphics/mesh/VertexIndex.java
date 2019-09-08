package engine.graphics.mesh;

import lombok.Data;

// Helper class containing indices of position, normal and uv
@Data
public class VertexIndex {

    private int positionId;
    private int normalId;
    private int uvId;

    public VertexIndex(String line) {
        String[] split = line.split("/");
        positionId = Integer.parseInt(split[0]);
        try {
            uvId = Integer.parseInt(split[1]);
        } catch (NumberFormatException e) {
            uvId = 0;
        }
        normalId = Integer.parseInt(split[2]);
    }

    public boolean equals(VertexIndex other) {
        return positionId == other.getPositionId() && normalId == other.getNormalId() && uvId == other.getUvId();
    }
}
