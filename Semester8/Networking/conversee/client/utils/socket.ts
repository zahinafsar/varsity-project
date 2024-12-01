import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

export const connectWebSocket = (onConnect: (client: Client) => void) => {
    const client = new Client({
        brokerURL: "ws://localhost:8080/ws",
        connectHeaders: {},
        debug: (str) => console.log(str),
        reconnectDelay: 5000,
        webSocketFactory: () => new SockJS("http://localhost:8080/ws"),
    });

    client.onConnect = () => {
        onConnect(client);
    };

    client.activate();
    return client;
};
