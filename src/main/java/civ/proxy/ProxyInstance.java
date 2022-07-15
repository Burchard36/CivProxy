package civ.proxy;

import civ.proxy.files.GameServerConfigs;
import io.github.ludovicianul.prettylogger.PrettyLogger;
import org.example.Instance;
import org.example.socket.AsyncServerSocket;
import civ.proxy.socket.actions.GameServerLinkAction;
import org.example.utils.Loggable;

public class ProxyInstance extends Instance {

    protected Loggable loggable;
    protected PrettyLogger log;
    protected AsyncServerSocket serverSocket;
    protected GameServerConfigs configs;

    public ProxyInstance() {
        super("ProxyInstance");
    }

    @Override
    public void onStart() {
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
    public void onStop() {

    }

    @Override
    public void onSave() {

    }
}
