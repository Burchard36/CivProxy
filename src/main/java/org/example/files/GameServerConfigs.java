package org.example.files;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.ludovicianul.prettylogger.PrettyLogger;
import org.example.utils.Loggable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class GameServerConfigs {

    protected final Gson gson;
    protected final Loggable loggable;
    protected final PrettyLogger log;
    public static GameServerConfigs GAME_CONFIGS;

    public GameServerConfigs() {
        GAME_CONFIGS = this;
        this.gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        this.loggable = new Loggable(this.getClass());
        this.log = loggable.logger();
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
        }

    }

}
