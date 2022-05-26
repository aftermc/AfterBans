package pl.aftermc.bans.command;

import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import pl.aftermc.bans.AfterBans;
import pl.aftermc.bans.util.ChatUtil;

@Command("unbanall")
public class UnBanAllCommand extends CommandBase {

    public UnBanAllCommand(final AfterBans plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfterBans plugin;

    @Default
    @Permission("abans.unbanall")
    public void unbanCommand(final CommandSender sender) {
        String unbanAdmin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            unbanAdmin = "konsola";
        }
        this.plugin.getPluginData().unbanAll();
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().unBanAllBroadcast.replace("%admin%", unbanAdmin));
    }
}
