import dk.sdu.cbse.common.services.*;

module Core {
    requires transitive Common;
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires spring.core;
    requires spring.context;
    requires spring.beans;


    exports dk.sdu.cbse.main;
    opens dk.sdu.cbse.main to javafx.graphics,spring.core;
    uses IGamePlugin;
    uses IEntityProcessingService;
    uses IEntityPostProcessingService;
    uses IWorldProvider;
}