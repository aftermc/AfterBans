package pl.aftermc.bans.command;

import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import pl.aftermc.bans.AfterBans;
import pl.aftermc.bans.util.ChatUtil;

@Command("unmuteall")
public class UnMuteAllCommand extends CommandBase {

    public UnMuteAllCommand(final AfterBans plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfterBans plugin;

    @Default
    @Permission("abans.unmuteall")
    public void unmuteCommand(final CommandSender sender) {
        String admin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            admin = "konsola";
        }
        this.plugin.getPluginData().unmuteAll();
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().unMuteAllBroadcast.replace("%admin%", admin));
    }
}
