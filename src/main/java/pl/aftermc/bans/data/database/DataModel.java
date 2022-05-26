package pl.aftermc.bans.data.database;

import pl.aftermc.bans.object.Ban;
import pl.aftermc.bans.object.Mute;

public interface DataModel {

    void load();
    void save();
    void save(final Ban ban);
    void save(final Mute mute);
    void deleteAllBans();
    void deleteAllMutes();
    void delete(final Ban ban);
    void delete(final Mute mute);
    void shutdown();
}
