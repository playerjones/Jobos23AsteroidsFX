module Graphics {
    requires Common;
    requires CommonSpaceShip;
    requires javafx.graphics;
    requires java.desktop;

    exports dk.sdu.cbse.graphics;

    provides dk.sdu.cbse.common.services.IGraphicProvider
            with dk.sdu.cbse.graphics.GraphicProvider;
}
