package dk.sdu.cbse.main;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.data.GameData.Keys;
import dk.sdu.cbse.common.services.*;

import java.util.*;

import static java.util.stream.Collectors.toList;

import java.lang.module.ModuleFinder;
import java.nio.file.Paths;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;


public class Game {

    GameData gameData = new GameData();
    World world = new World();
    private final Map<Entity, Node> entitySprites = new HashMap<>();


    public Game(Collection<? extends IEntityProcessingService> processingServices, Collection<? extends IEntityPostProcessingService> postProcessingServices) {
        this.processingServices = processingServices;
        this.postProcessingServices = postProcessingServices;
    }

    Pane gameWindow = new Pane();
    Canvas canvas = new Canvas(gameData.width, gameData.height);
    Text scoreText = new Text(10,10, "0 score");


    public void start(Stage primaryStage) {

        gameWindow.setPrefSize(gameData.width, gameData.height);

        scoreText.setFill(Paint.valueOf("white"));
        gameData.getScore().addListener((observable, oldValue, newValue) -> {
            scoreText.setText(String.format("%d score", newValue.intValue()));
        });

        gameWindow.getChildren().add(scoreText);
        gameWindow.getChildren().add(canvas);

        Background fallback = new Background(new BackgroundFill(Color.BLACK, null, null));

        Background background = ServiceLoader
                .load(layer, IGraphicProvider.class)
                .findFirst()
                .map(IGraphicProvider::getBackground)
                .orElse(fallback);

        gameWindow.setBackground(background);


        Scene scene = new Scene(gameWindow);

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            gameData.width = (int)((double) newVal);
            canvas.setWidth(gameData.width);
        });
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            gameData.height = (int) ((double) newVal);
            canvas.setHeight(gameData.height);
        });

        scene.setOnKeyPressed(event -> setupKeys(event, true));
        scene.setOnKeyReleased(event -> setupKeys(event, false));

        primaryStage.setScene(scene);
        primaryStage.setTitle("AsteroidsFX");
        primaryStage.setResizable(true);
        primaryStage.setOnCloseRequest(event -> {
            for(IGamePlugin plugin : getPlugins()) {
                plugin.stop(gameData, world);
            }
            try {
                int score = gameData.getScore().get();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.postForObject("http://localhost:8080/score", score, Void.class);
                System.out.println("Score sent: " + score);
            } catch (Exception e) {
                System.out.println("Failed to send score to microservice: " + e.getMessage());
            }

            System.exit(0);
        });
        primaryStage.show();

        for (IWorldProvider module : ServiceLoader.load(IWorldProvider.class)) {
            module.provideWorld(world);
        }

        for (IGamePlugin plugin : getPlugins()) {
            plugin.start(gameData, world);
        }

        startTicking();

    }

    void setupKeys(KeyEvent event, boolean pressed) {
        switch (event.getCode()) {
            case UP:
            case W:
                this.gameData.setDown(Keys.UP, pressed);
                break;
            case RIGHT:
            case D:
                this.gameData.setDown(Keys.RIGHT, pressed);
                break;
            case LEFT:
            case A:
                this.gameData.setDown(Keys.LEFT, pressed);
                break;
            case SPACE:
                this.gameData.setDown(Keys.SPACE, pressed);
                break;
            default:
                break;
        }
    }

    void startTicking() {
        new AnimationTimer() {
            long lastTick = System.nanoTime();
            double accumulator = 0.0;
            final double fixedDelta = 1.0 / 60.0; // 60 updates per second

            @Override
            public void handle(long now) {
                double elapsedTime = (now - lastTick) / 1_000_000_000.0;
                lastTick = now;
                accumulator += elapsedTime;

                while (accumulator >= fixedDelta) {
                    tick();
                    accumulator -= fixedDelta;
                }
            }
        }.start();
    }

    void tick() {
        update();
        gameData.updateKeys();
        render();
    }

    void update() {
        for (IEntityProcessingService service : getEntityProcessingServices()) {
            service.process(world, gameData);
        }
        for (IEntityPostProcessingService service : getEntityPostProcessingServices()) {
            service.process(world, gameData);
        }
    }

    void render() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gameData.width, gameData.height);

        for (Entity e : this.world.getEntities()) {
            if (!e.isAlive()) {
                Polygon removedPolygon = e.getPolygon();
                Node removedSprite = entitySprites.remove(e);
                this.world.removeEntity(e);

                gameWindow.getChildren().remove(removedPolygon);
                if (removedSprite != null) {
                    gameWindow.getChildren().remove(removedSprite);
                }
                continue;
            }


            if (!gameWindow.getChildren().contains(e.getPolygon())) {
                gameWindow.getChildren().add(e.getPolygon());
            }

            e.Render(gc);
        }

        for (Entity e : world.getEntities()) {
            if (!entitySprites.containsKey(e)) {
                ServiceLoader.load(layer, IGraphicProvider.class).findFirst().ifPresent(provider -> {
                    Node sprite = provider.createSpriteFor(e);
                    if (sprite != null) {
                        entitySprites.put(e, sprite);
                        gameWindow.getChildren().add(sprite);
                        // Hide polygon
                        e.getPolygon().setVisible(false);
                    }
                });
            }

            Node sprite = entitySprites.get(e);
            if (sprite != null) {
                double width = sprite.getBoundsInParent().getWidth();
                double height = sprite.getBoundsInParent().getHeight();

                sprite.setLayoutX(e.getLocation().getX() - width / 2);
                sprite.setLayoutY(e.getLocation().getY() - height / 2);
                sprite.setRotate(e.getRotation());

            }
        }

    }

    // Services

    Collection<? extends IEntityProcessingService> processingServices;
    Collection<? extends IEntityPostProcessingService> postProcessingServices;

    ModuleLayer layer = getModuleLayer(2);

    private ModuleLayer getModuleLayer(int _layer) {
        ModuleFinder finder = ModuleFinder.of(Paths.get(String.format("mods-mvn-%,d", _layer)));
        ModuleLayer parent = ModuleLayer.boot();
        List<String> modules = finder.findAll().stream().map(m -> m.descriptor().name()).collect(toList());
        java.lang.module.Configuration config = parent.configuration().resolve(finder, ModuleFinder.of(), modules);
        ModuleLayer layer = parent.defineModulesWithOneLoader(config, ClassLoader.getPlatformClassLoader());
        return layer;
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        if (processingServices == null) {
            processingServices = ServiceLoader.load(layer, IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
        }
        return processingServices;
    }

    private Collection<? extends IEntityPostProcessingService> getEntityPostProcessingServices() {
        if (postProcessingServices == null) {
            postProcessingServices = ServiceLoader.load(layer, IEntityPostProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
        }
        return postProcessingServices;
    }

    private Collection<? extends IGamePlugin> getPlugins() {
        return ServiceLoader.load(layer, IGamePlugin.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

}