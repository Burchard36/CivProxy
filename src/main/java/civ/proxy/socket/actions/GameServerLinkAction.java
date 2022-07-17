package civ.proxy.socket.actions;

import org.example.socket.ServerLoggable;
import org.example.socket.action.SocketAction;
import org.example.socket.interfaces.ActionHandler;
import civ.proxy.socket.packets.incoming.GameServerLinkObject;
import org.example.socket.user.ConnectedClient;
import org.example.utils.Loggable;

public class GameServerLinkAction extends SocketAction {

    public GameServerLinkAction() {
    }

    @ActionHandler
    public void onLink(ConnectedClient connectedClient,
                       GameServerLinkObject gameServerLinkObject) throws InterruptedException {
        System.out.println("GameServerLinkAction.onLink");
        thisThread(this.getClass()).log.info("GameServerLinkAction.onLink executed");
        Thread.sleep(1000);
    }


}
