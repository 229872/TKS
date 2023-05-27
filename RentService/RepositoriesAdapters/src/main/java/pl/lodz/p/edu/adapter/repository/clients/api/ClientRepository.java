package pl.lodz.p.edu.adapter.repository.clients.api;

import pl.lodz.p.edu.adapter.repository.clients.data.ClientEnt;

public interface ClientRepository extends Repository<ClientEnt> {

    @Override
    ClientEnt update(ClientEnt elem);
}
