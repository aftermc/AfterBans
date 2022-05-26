package pl.aftermc.bans.command;

import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Completion;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import pl.aftermc.bans.AfterBans;
import pl.aftermc.bans.object.Mute;
import pl.aftermc.bans.util.ChatUtil;

@Command("muteinfo")
public class MuteInfoCommand extends CommandBase {

    public MuteInfoCommand(final AfterBans plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfterBans plugin;

    @Default
    @Permission("abans.muteinfo")
    @Completion("#mutedplayers")
    public void muteInfoCommand(final CommandSender sender, final String[] args) {
        if(args.length < 1) {
            ChatUtil.sendMessage(sender, "&7Prawidłowe użycie komendy: &3/muteinfo <gracz>");
            return;
        }
        Mute mute = this.plugin.getPluginData().getMute(args[0]);
        if(mute == null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerNotMuted);
            return;
        }

        ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().muteInfo
                .replace("%player%", mute.getPlayerName())
                .replace("%banDate%", mute.getMuteDate())
                .replace("%banAdmin%", mute.getMuteAdmin())
                .replace("%reason%", mute.getReason())
                .replace("%expire%", mute.isPerm() ? "nigdy" : mute.getMuteDuration()));
    }
}
