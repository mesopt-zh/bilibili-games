## bilibili-games

基于 **Spring Boot** 的示例脚手架工程，内置 **MySQL 数据源配置**、**Redis 配置**，代码结构采用常见 **MVC 分层**。

### 主要特性

- **Spring Boot 3 + Java 17**
- **MySQL**：通过 `spring-boot-starter-data-jpa` 集成，示例实体 `Game`。
- **Redis**：配置了通用 `RedisTemplate<String, Object>`，示例业务中使用 Redis 做简单缓存。
- **MVC 分层**：`controller` / `service` / `repository` / `entity` 目录清晰。

### 目录结构（核心部分）

- `src/main/java/com/bilibili/bilibiligames/BilibiliGamesApplication.java`：启动类  
- `entity`：领域实体，例如 `Game`  
- `repository`：数据访问层，例如 `GameRepository`  
- `service`：业务层，例如 `GameService`  
- `controller`：控制层，例如 `GameController`  
- `config/RedisConfig.java`：Redis 相关 Bean 配置  
- `src/main/resources/application.yml`：MySQL / Redis / JPA / 端口等配置  

### 环境准备

- JDK 17+
- Maven 3.8+
- 本地已安装并启动：
  - MySQL（默认：`jdbc:mysql://localhost:3306/bilibili_games`，用户名密码 `root/root`，可在 `application.yml` 中修改）
  - Redis（默认：`localhost:6379`）

### 本地运行

```bash
mvn spring-boot:run
```

或打包后运行：

```bash
mvn clean package
java -jar target/bilibili-games-0.0.1-SNAPSHOT.jar
```

### 示例接口

- **查询全部游戏**
  - `GET /api/games`
- **按 ID 查询游戏**
  - `GET /api/games/{id}`
- **新建游戏**
  - `POST /api/games`
  - 请求体示例：

    ```json
    {
      "name": "Test Game",
      "description": "This is a demo game."
    }
    ```

### 自定义说明

- 修改数据库名称 / 账号密码 / Redis 地址：直接改 `application.yml`。
- 按业务划分模块：可以在当前结构基础上增加包（如 `user`, `order` 等），保持 `controller/service/repository/entity` 的分层思想。
