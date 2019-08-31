package artgellary;


import engine.models.GameObject;
import org.joml.Vector3f;

public class ArtGalleryRootObject extends GameObject {

    @Override
    public void start() {

        Room room = new Room();
        addChild(room);

        Player player = new Player();
        addChild(player);
    }

    @Override
    public void update() {
    }
}
