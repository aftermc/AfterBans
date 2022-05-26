package pl.aftermc.bans.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.aftermc.bans.AfterBans;
import pl.aftermc.bans.util.ChatUtil;

public class PlayerJoin implements Listener {

    public PlayerJoin(final AfterBans plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    private final AfterBans plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(final PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("abans.notifyupdate")) {
            if(this.plugin.isNewPluginUpdate()) {
                ChatUtil.sendMessage(player, "&3AfterBans &8&l- &cDostępna jest nowa wersja pluginu!");
                ChatUtil.sendURLMessage(player, "&3AfterBans &8&l- &aKliknij na wiadomość aby pobrać najnowszą wersję z githuba", "https://github.com/aftermc/AfterBans/releases");
            } else {
                ChatUtil.sendMessage(player, "&3AfterBans &8&l- &aUżywasz aktualnej wersji pluginu! :)");
            }
        }
    }
}
