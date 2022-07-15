package org.proxy;

import io.github.ludovicianul.prettylogger.PrettyLogger;
import org.example.Instance;
import org.proxy.files.GameServerConfigs;
import org.example.socket.AsyncServerSocket;
import org.proxy.socket.actions.GameServerLinkAction;
import org.example.utils.Loggable;

public class ProxyInstance extends Instance {

    protected Loggable loggable;
    protected PrettyLogger log;
    protected AsyncServerSocket serverSocket;
    protected GameServerConfigs configs;

    protected ProxyInstance() {
        super("ProxyInstance");
    }

    @Override
    protected void onStart() {
        this.loggable = new Loggable(this.getClass());
        this.log = this.loggable.logger();
        this.loggable.startAsync("Startup");
        this.log.start("Initializing ProxyInstance ...");
        this.configs = new GameServerConfigs();

        this.serverSocket = new AsyncServerSocket(8708);

        this.serverSocket.registerSocketActions(GameServerLinkAction.class);

        this.serverSocket.startSocket();
        this.serverSocket.startNotifer.onNotified(() -> {
            final String time = this.loggable.prettyAsyncMilliElapsed("Startup");
            this.log.complete("Successfully started ProxyInstance! (Completed in %s)"
                    .formatted(time));
        });
    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void onSave() {

    }
}
