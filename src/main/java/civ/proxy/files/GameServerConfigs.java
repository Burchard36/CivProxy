package civ.proxy.files;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.ludovicianul.prettylogger.PrettyLogger;
import lombok.Getter;
import org.example.utils.Loggable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;

public class GameServerConfigs {

    protected final Gson gson;
    protected final Loggable loggable;
    protected final PrettyLogger log;
    public static GameServerConfigs GAME_CONFIGS;
    @Getter
    protected GameServerConfig configs;

    public GameServerConfigs() {
        GAME_CONFIGS = this;
        this.gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        this.loggable = new Loggable(this.getClass());
        this.log = loggable.logger();
        this.log.info("Loading game-configs.json file...");
        try {
            this.loadGameConfigs();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void loadGameConfigs() throws IOException {
        final File configDirectory = new File("/configs");
        if (!configDirectory.exists()) {
            if (configDirectory.mkdir()) {
                this.log.success("Successfully created /configs directory!");
                this.loadGameConfigs();
            } else this.log.error("Error creating /configs directory! Does the server have file writing permissions?");
            return;
        }

        final File gameConfigsFile = new File(configDirectory, "game-configs.json");
        if (!gameConfigsFile.exists()) {
            this.log.info("Creating game-configs.json file...");
            final GameServerConfig config = new GameServerConfig();
            final String fileString = this.gson.toJson(config, GameServerConfig.class);
            final Writer writer = new FileWriter(gameConfigsFile);
            writer.append(fileString);
            writer.close();
            this.log.success("Successfully created game-configs.json file with default configurations!");
        } else {
            this.configs = this.gson.fromJson(Files.newBufferedReader(gameConfigsFile.toPath()), GameServerConfig.class);
            this.log.success("Successfully loaded game-configs.json file!");
        }
    }

}
