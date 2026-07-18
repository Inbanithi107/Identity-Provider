package com.techforge.identityprovider.bootstrap;

import java.util.List;

public class OAuthClientProperties {

    private List<Client> clients;

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
