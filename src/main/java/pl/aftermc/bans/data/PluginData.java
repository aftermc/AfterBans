package pl.aftermc.bans.data;

import pl.aftermc.bans.AfterBans;
import pl.aftermc.bans.object.Ban;
import pl.aftermc.bans.object.Mute;

import java.util.HashMap;
import java.util.Map;

public final class PluginData {

    private final AfterBans plugin;
    private final Map<String, Ban> bans;
    private final Map<String, Mute> mutes;

    public PluginData(final AfterBans plugin) {
        this.plugin = plugin;
        this.bans = new HashMap<>();
        this.mutes = new HashMap<>();
    }

    public Map<String, Ban> getBans() {
        return this.bans;
    }
    public Map<String, Mute> getMutes() {
        return this.mutes;
    }

    public void addBan(final Ban ban) {
        if(this.bans.containsKey(ban.getPlayerName()) || this.bans.containsValue(ban)) return;
        this.bans.put(ban.getPlayerName(), ban);
    }
    public void addMute(final Mute mute) {
        if(this.mutes.containsKey(mute.getPlayerName()) || this.mutes.containsValue(mute)) return;
        this.mutes.put(mute.getPlayerName(), mute);
    }

    public void removeBan(final Ban ban) {
        this.bans.remove(ban.getPlayerName());
    }
    public void removeMute(final Mute mute) {
        this.mutes.remove(mute.getPlayerName());
    }

    public Ban getBan(final String playerName){
        return this.bans.values().parallelStream().filter(user -> user.getPlayerName().equalsIgnoreCase(playerName)).findFirst().orElse(null);
    }
    public Mute getMute(final String playerName){
        return this.mutes.values().parallelStream().filter(user -> user.getPlayerName().equalsIgnoreCase(playerName)).findFirst().orElse(null);
    }

    public void unbanAll() {
        this.plugin.getDatabase().deleteAllBans();
        this.bans.clear();
    }
    public void unmuteAll() {
        this.plugin.getDatabase().deleteAllMutes();
        this.mutes.clear();
    }
}
