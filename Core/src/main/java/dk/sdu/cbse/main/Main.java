package dk.sdu.cbse.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    GameData gameData = new GameData();
    World world = new World();


    public static void main(String[] args) {
        System.out.println("IDK");
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ModuleConfig.class);

        for (String beanName : ctx.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }

        Game game = ctx.getBean(Game.class);
        game.start(window);
        ctx.close();
    }
}