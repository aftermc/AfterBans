package pl.aftermc.bans.command;

import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.aftermc.bans.AfterBans;
import pl.aftermc.bans.object.Ban;
import pl.aftermc.bans.util.ChatUtil;
import pl.aftermc.bans.util.StringUtil;

@Command("ban")
public class BanCommand extends CommandBase {

    public BanCommand(final AfterBans plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfterBans plugin;

    @Default
    @Permission("abans.ban")
    @Completion("#players")
    public void banCommand(final CommandSender sender, final String[] args) {
        if(args.length < 1) {
            ChatUtil.sendMessage(sender, "&7Prawidłowe użycie komendy: &3/ban <gracz> <powód>");
            return;
        }
        if(this.plugin.getPluginData().getBan(args[0]) != null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerAlreadyBanned);
            return;
        }
        String reason = this.plugin.getPluginConfiguration().noReasonBan;
        if(args.length > 1) {
            reason = StringUtil.stringBuilder(args, 1);
        }
        String banAdmin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            banAdmin = "konsola";
        }
        Ban ban = new Ban(args[0], reason, -1, banAdmin);
        this.plugin.getPluginData().addBan(ban);
        this.plugin.getDatabase().save(ban);
        Player banned = Bukkit.getPlayer(args[0]);
        if(banned != null) {
            banned.kickPlayer(ChatUtil.fixColor(ban.replaceBan()));
        }
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().permBanBroadcast.replace("%player%", args[0]).replace("%admin%", banAdmin).replace("%reason%", reason));
    }
}
