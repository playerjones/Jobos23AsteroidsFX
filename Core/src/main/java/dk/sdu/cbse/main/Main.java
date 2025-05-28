package dk.sdu.cbse.main;

import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.nio.file.*;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import static java.util.stream.Collectors.toList;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameKeys;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();
    public int asteroidsDestroyed = 0;

    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {
        // Load JAR-based plugins
        loadPlugins();

        // Classpath-plugins
        System.out.println("ClassPath-plugins:");
        for (IGamePluginService plugin : getPluginServices()) {
            System.out.println(" - " + plugin.getClass().getName());
            plugin.start(gameData, world);
        }

        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }

        Text text = new Text(10, 20, "Destroyed asteroids: " + asteroidsDestroyed);
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(text);

        Scene scene = new Scene(gameWindow);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) gameData.getKeys().setKey(GameKeys.LEFT, true);
            if (event.getCode().equals(KeyCode.RIGHT)) gameData.getKeys().setKey(GameKeys.RIGHT, true);
            if (event.getCode().equals(KeyCode.UP)) gameData.getKeys().setKey(GameKeys.UP, true);
            if (event.getCode().equals(KeyCode.SPACE)) gameData.getKeys().setKey(GameKeys.SPACE, true);
            if (event.getCode().equals(KeyCode.DOWN)) gameData.getKeys().setKey(GameKeys.DOWN, true);
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) gameData.getKeys().setKey(GameKeys.LEFT, false);
            if (event.getCode().equals(KeyCode.RIGHT)) gameData.getKeys().setKey(GameKeys.RIGHT, false);
            if (event.getCode().equals(KeyCode.UP)) gameData.getKeys().setKey(GameKeys.UP, false);
            if (event.getCode().equals(KeyCode.SPACE)) gameData.getKeys().setKey(GameKeys.SPACE, false);
            if (event.getCode().equals(KeyCode.DOWN)) gameData.getKeys().setKey(GameKeys.DOWN, false);
        });

        render();
        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();
    }

    private void render() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw();
                gameData.getKeys().update();
            }
        }.start();
    }

    private void update() {
        for (IEntityProcessingService processor : getEntityProcessingServices()) {
            processor.process(gameData, world);
        }
        for (IPostEntityProcessingService postProcessor : getPostEntityProcessingServices()) {
            postProcessor.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity polygonEntity : polygons.keySet()) {
            if (!world.getEntities().contains(polygonEntity)) {
                Polygon removedPolygon = polygons.get(polygonEntity);
                polygons.remove(polygonEntity);
                gameWindow.getChildren().remove(removedPolygon);
            }
        }

        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }

        gameWindow.getChildren().removeIf(node -> node instanceof Text && ((Text) node).getText().startsWith("Entities: "));
        Text entityCountText = new Text(10, 40, "Entities: " + world.getEntities().size());
        entityCountText.setFill(Color.BLACK);
        entityCountText.setFont(new Font(16));
        gameWindow.getChildren().add(entityCountText);
        entityCountText.toFront();
    }

    private Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private void loadPlugins() {
        Path pluginDir = Path.of("plugins/jars");
        if (!Files.exists(pluginDir)) return;

        try {
            List<Path> jarPaths = Files.walk(pluginDir)
                    .filter(path -> path.toString().endsWith(".jar"))
                    .toList();

            ModuleFinder finder = ModuleFinder.of(jarPaths.toArray(new Path[0]));
            ModuleLayer parent = ModuleLayer.boot();
            Configuration config = parent.configuration()
                    .resolve(finder, ModuleFinder.of(), List.of("Bullet"));

            ModuleLayer layer = parent.defineModulesWithOneLoader(config, ClassLoader.getSystemClassLoader());
            ClassLoader loader = layer.findLoader("Bullet");

            ServiceLoader.load(IGamePluginService.class, loader)
                    .forEach(plugin -> {
                        System.out.println("Plugin loaded from JAR: " + plugin.getClass().getName());
                        plugin.start(gameData, world);
                    });

            ServiceLoader.load(IEntityProcessingService.class, loader)
                    .forEach(proc -> {});

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
