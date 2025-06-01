package dk.sdu.cbse.microservicelab;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class ScoreController {

    private final AtomicInteger totalScore = new AtomicInteger();

    @PostMapping("/score")
    public void addScore(@RequestBody int score) {
        totalScore.addAndGet(score);
    }

    @GetMapping("/score")
    public int getScore() {
        return totalScore.get();
    }
}
