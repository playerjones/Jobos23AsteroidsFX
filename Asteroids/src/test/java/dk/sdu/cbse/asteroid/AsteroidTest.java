package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.data.Config;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.services.IAsteroidSPI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class AsteroidTest {

    @AfterEach
    void cleanUp() {
        Asteroid.testSPIOverride = null;
    }

    @Test
    void testLargeAsteroidTriggersSplit() {
        // Arrange
        Asteroid asteroid = new Asteroid();
        asteroid.setRadius((int) (Asteroid.ASTEROID_SIZING * Config.SIZING * 2)); // Stor asteroid
        GameData gameData = new GameData();

        IAsteroidSPI mockSPI = mock(IAsteroidSPI.class);
        Asteroid.testSPIOverride = mockSPI;

        // Act
        asteroid.kill(gameData);

        // Assert
        verify(mockSPI, times(1)).spawnEntity(asteroid);
    }

    @Test
    void testSmallAsteroidDoesNotSplit() {
        // Arrange
        Asteroid asteroid = new Asteroid();
        asteroid.setRadius((int) (Asteroid.ASTEROID_SIZING * Config.SIZING * 1.0)); // Lille asteroid
        GameData gameData = new GameData();

        IAsteroidSPI mockSPI = mock(IAsteroidSPI.class);
        Asteroid.testSPIOverride = mockSPI;

        // Act
        asteroid.kill(gameData);

        // Assert
        verify(mockSPI, never()).spawnEntity(any());
    }
}
