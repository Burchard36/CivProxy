package civ.proxy.cli;

import civ.proxy.socket.packets.incoming.GameServerLinkObject;
import io.github.ludovicianul.prettylogger.PrettyLogger;
import org.example.socket.action.SocketAction;
import org.example.socket.interfaces.ActionHandler;
import org.example.socket.user.ConnectedClient;
import org.example.utils.Errors;
import org.example.utils.Loggable;
import picocli.CommandLine;

import java.util.concurrent.CompletableFuture;

@CommandLine.Command(
        name = "civ",
        description = "Command used to initialize a new server",
        mixinStandardHelpOptions = true
)
public class ServerHandler extends SocketAction implements Runnable {

    protected final Loggable loggable;
    protected final PrettyLogger log;

    protected boolean acceptingNewLink = false;

    public ServerHandler() {
        this.loggable = new Loggable(this.getClass());
        this.log = this.loggable.logger();
    }

    @CommandLine.Command(
            name = "link-server",
            description = "Command used to initialize a new server",
            mixinStandardHelpOptions = true,
            aliases = {"linkserver", "link"}
    )
    public void initializeNewServerLink() {
        this.log.info("Initializing new server link...");
        this.acceptingNewLink = true;

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(30 * 1000);
                acceptingNewLink = false;
                log.info("Server linking has been disabled! Re-run the command to enabled it again");
            } catch (InterruptedException e) {
                acceptingNewLink = false;
                throw new RuntimeException(e);
            }
        });

        this.log.info("The system is now accepting new server links!");
    }

    @ActionHandler
    public void onLinkReceive(ConnectedClient client,
                              GameServerLinkObject linkObject) {
        if (!this.acceptingNewLink) {
            this.log.warn(("Client connected with %s is attempting to link to a new server," +
                    " but server linking is disabled! In the future this server will immediately be whitelisted from" +
                    "linking until a manual administrator review")
                    .formatted(client.getClientSocket().getRemoteSocketAddress()));
            client.getClientSocket().send(Errors.internalServerError().toString());
            return;
        }

        this.acceptingNewLink = false;
        this.loggable.startAsync("ServerLink");
        this.log.info("Client connected with %s is attempting to link to a new server!"
                .formatted(client.getClientSocket().getRemoteSocketAddress()));


    }


    @Override
    public void run() {
        this.log.info("Initializing new server link...");
    }
}
