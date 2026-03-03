package com.bilibili.bilibiligames.service;

import com.bilibili.bilibiligames.entity.Game;
import com.bilibili.bilibiligames.repository.GameRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private static final String GAME_CACHE_KEY_PREFIX = "game:";

    private final GameRepository gameRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public GameService(GameRepository gameRepository, RedisTemplate<String, Object> redisTemplate) {
        this.gameRepository = gameRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<Game> listAll() {
        return gameRepository.findAll();
    }

    public Optional<Game> findById(Long id) {
        String cacheKey = GAME_CACHE_KEY_PREFIX + id;
        Object cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached instanceof Game game) {
            return Optional.of(game);
        }

        Optional<Game> dbGame = gameRepository.findById(id);
        dbGame.ifPresent(game -> redisTemplate.opsForValue()
                .set(cacheKey, game, Duration.ofMinutes(5)));
        return dbGame;
    }

    public Game create(Game game) {
        Game saved = gameRepository.save(game);
        String cacheKey = GAME_CACHE_KEY_PREFIX + saved.getId();
        redisTemplate.opsForValue().set(cacheKey, saved, Duration.ofMinutes(5));
        return saved;
    }
}

