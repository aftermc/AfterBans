package pl.aftermc.bans.command;

import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Completion;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import pl.aftermc.bans.AfterBans;
import pl.aftermc.bans.object.Ban;
import pl.aftermc.bans.util.ChatUtil;

@Command("unban")
public class UnBanCommand extends CommandBase {

    public UnBanCommand(final AfterBans plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfterBans plugin;

    @Default
    @Permission("abans.unban")
    @Completion("#bannedplayers")
    public void unbanCommand(final CommandSender sender, final String[] args) {
        if(args.length < 1) {
            ChatUtil.sendMessage(sender, "&7Prawidłowe użycie komendy: &3/unban <gracz>");
            return;
        }
        if(this.plugin.getPluginData().getBan(args[0]) == null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerNotBanned);
            return;
        }
        String unbanAdmin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            unbanAdmin = "konsola";
        }
        Ban ban = this.plugin.getPluginData().getBan(args[0]);
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().unBanBroadcast.replace("%player%", ban.getPlayerName()).replace("%admin%", unbanAdmin));
        this.plugin.getDatabase().delete(ban);
        this.plugin.getPluginData().removeBan(ban);
    }
}
