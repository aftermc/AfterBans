package pl.aftermc.bans.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.aftermc.bans.AfterBans;
import pl.aftermc.bans.data.MessagesConfiguration;
import pl.aftermc.bans.object.Ban;
import pl.aftermc.bans.util.ChatUtil;

public final class PlayerPreLogin implements Listener {

    public PlayerPreLogin(final AfterBans plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    private final AfterBans plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(AsyncPlayerPreLoginEvent e){
        String name = e.getName();
        Ban ban = this.plugin.getPluginData().getBan(name);
        if(ban !=null) {
            MessagesConfiguration messageConfiguration = this.plugin.getMessagesConfiguration();
            if(ban.isPerm()) {
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fixColor(ban.replaceBan()));
                ChatUtil.sendBroadcast(messageConfiguration.bannedPlayerLogin.replace("%player%", ban.getPlayerName()), "abans.bannedtryjoin");
            } else {
                if(!ban.isExpire()) {
                    e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fixColor(ban.replaceBan()));
                    ChatUtil.sendBroadcast(messageConfiguration.bannedPlayerLogin.replace("%player%", ban.getPlayerName()), "abans.bannedtryjoin");
                } else {
                    this.plugin.getDatabase().delete(ban);
                    this.plugin.getPluginData().removeBan(ban);
                    ChatUtil.sendBroadcast(messageConfiguration.bannedPlayerLoginExpire.replace("%player%", ban.getPlayerName()), "abans.bannedtryjoin");
                }
            }
        }
    }
}
