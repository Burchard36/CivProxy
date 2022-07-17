package civ.proxy;

import civ.proxy.files.GameServerConfigs;
import org.example.Instance;
import org.example.socket.AsyncServerSocket;
import civ.proxy.socket.actions.GameServerLinkAction;

public class ProxyInstance extends Instance {
    protected AsyncServerSocket serverSocket;
    protected GameServerConfigs configs;

    public ProxyInstance() {
        super("ProxyInstance");
    }

    @Override
    public void onStart() {
        this.loggable.startAsync("Startup");
        this.log.start("Initializing ProxyInstance ...");
        this.configs = new GameServerConfigs();

        this.serverSocket = new AsyncServerSocket(8773);

        this.serverSocket.registerSocketActions(GameServerLinkAction.class);

        this.serverSocket.startSocket();
        this.serverSocket.startNotifer.onNotified(() -> {
            final String time = this.loggable.prettyAsyncMilliElapsed("Startup");
            this.log.complete("Successfully started ProxyInstance! (Completed in %s)"
                    .formatted(time));

            /*loggable.startAsync("Timer");
            for (int x = 0; x < 1000000; x++) {
                log.info("FOREVER LOOPING ON " + x  );
            }
            log.complete("Took %s"
                    .formatted(loggable.complete("Timer")));*/
        });
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSave() {

    }
}
