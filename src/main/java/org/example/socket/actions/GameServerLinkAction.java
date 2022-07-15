package org.example.socket.actions;

import org.example.socket.action.SocketAction;
import org.example.socket.interfaces.ActionHandler;
import org.example.socket.packets.incoming.GameServerLinkObject;
import org.example.socket.user.ConnectedClient;

public class GameServerLinkAction extends SocketAction {

    public GameServerLinkAction() {

    }

    @ActionHandler
    public void onLink(ConnectedClient connectedClient,
                       GameServerLinkObject gameServerLinkObject) {

    }
}
