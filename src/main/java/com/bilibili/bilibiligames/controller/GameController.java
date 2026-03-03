package com.bilibili.bilibiligames.controller;

import com.bilibili.bilibiligames.entity.Game;
import com.bilibili.bilibiligames.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<List<Game>> list() {
        return ResponseEntity.ok(gameService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> detail(@PathVariable Long id) {
        return gameService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Game> create(@RequestBody Game game) {
        Game saved = gameService.create(game);
        return ResponseEntity
                .created(URI.create("/api/games/" + saved.getId()))
                .body(saved);
    }
}

