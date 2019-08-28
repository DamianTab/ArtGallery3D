package artgellary;


import engine.models.GameObject;

public class ArtGalleryRootObject extends GameObject {

    @Override
    public void start() {

        Room room = new Room();
        addChild(room);

        MainCamera mainCamera = new MainCamera();
        addChild(mainCamera);

        mainCamera.setTarget(room.getTransform());
    }

    @Override
    public void update() {
    }
}
