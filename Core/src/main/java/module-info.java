module Core {
    requires Common;
    requires javafx.graphics;
    requires javafx.controls;
    exports dk.sdu.cbse.main;
    opens dk.sdu.cbse.main to javafx.graphics;
    uses dk.sdu.cbse.common.services.IGamePluginService;
    uses dk.sdu.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.cbse.common.services.IPostEntityProcessingService;
}