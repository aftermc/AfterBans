package pl.aftermc.bans.data.database.flat;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.aftermc.bans.AfterBans;
import pl.aftermc.bans.data.database.DataModel;
import pl.aftermc.bans.object.Ban;
import pl.aftermc.bans.object.Mute;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class FlatDataModel implements DataModel {

    private final File databaseFile;
    private final YamlConfiguration databaseYaml;
    private final AfterBans plugin;

    public FlatDataModel(final AfterBans plugin) {
        this.plugin = plugin;
        this.databaseFile = new File(plugin.getDataFolder(), "database.yml");
        try {
            if(!this.databaseFile.exists()) this.databaseFile.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.databaseYaml = YamlConfiguration.loadConfiguration(this.databaseFile);
        this.plugin.getLogger().info("Korzystam z bazy danych: FLAT");
    }

    @Override
    public void load() {
        ConfigurationSection cs1Bans = this.databaseYaml.getConfigurationSection("bans");
        if(cs1Bans != null) {
            int intBans = 0;
            for(String string : cs1Bans.getKeys(false)) {
                ConfigurationSection cs = cs1Bans.getConfigurationSection(string);
                this.plugin.getPluginData().addBan(new Ban(string, cs));
                intBans++;
            }
            this.plugin.getLogger().info("Załadowano " + intBans + " banów!");
        } else {
            this.plugin.getLogger().log(Level.INFO, "Brak banów do załadowania!");
        }

        ConfigurationSection cs1Mutes = this.databaseYaml.getConfigurationSection("mutes");
        if(cs1Mutes != null) {
            int intMutes = 0;
            for(String string : cs1Mutes.getKeys(false)) {
                ConfigurationSection cs = cs1Mutes.getConfigurationSection(string);
                this.plugin.getPluginData().addMute(new Mute(string, cs));
                intMutes++;
            }
            this.plugin.getLogger().info("Załadowano " + intMutes + " wyciszonych graczy!");
        } else {
            this.plugin.getLogger().log(Level.INFO, "Brak wyciszonych graczy do załadowania!");
        }
    }

    @Override
    public void save() {
        int intBans = 0;
        for(Ban ban : this.plugin.getPluginData().getBans().values()) {
            if (ban.isExpire()) {
                this.delete(ban);
                this.plugin.getLogger().info("Ban gracza " + ban.getPlayerName() + " wygasł i zostanie usunięty!");
                continue;
            }
            if(!ban.isChanges()) continue;
            this.save(ban);
            intBans++;
        }
        this.plugin.getLogger().info("Zapisano " + intBans + " banów!");

        int intMutes = 0;
        for(Mute mute : this.plugin.getPluginData().getMutes().values()) {
            if (mute.isExpire()) {
                this.delete(mute);
                this.plugin.getLogger().info("Wyciszenie gracza " + mute.getPlayerName() + " wygasło i zostanie usunięte!");
                continue;
            }
            if(!mute.isChanges()) continue;
            this.save(mute);
            intMutes++;
        }
        this.plugin.getLogger().info("Zapisano " + intMutes + " wyciszonych graczy!");
    }

    @Override
    public void save(final Ban ban) {
        this.databaseYaml.set("bans." + ban.getPlayerName() + ".reason", ban.getReason());
        this.databaseYaml.set("bans." + ban.getPlayerName() + ".banDuration", ban.getLongBanDuration());
        this.databaseYaml.set("bans." + ban.getPlayerName() + ".banDate", ban.getLongBanDate());
        this.databaseYaml.set("bans." + ban.getPlayerName() + ".banAdmin", ban.getBanAdmin());
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ban.setChanges(false);
    }
    @Override
    public void save(final Mute mute) {
        this.databaseYaml.set("mutes." + mute.getPlayerName() + ".reason", mute.getReason());
        this.databaseYaml.set("mutes." + mute.getPlayerName() + ".banDuration", mute.getLongMuteDuration());
        this.databaseYaml.set("mutes." + mute.getPlayerName() + ".banDate", mute.getLongMuteDate());
        this.databaseYaml.set("mutes." + mute.getPlayerName() + ".banAdmin", mute.getMuteAdmin());
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        mute.setChanges(false);
    }

    @Override
    public void deleteAllBans() {
        this.databaseYaml.set("bans", null);
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void deleteAllMutes() {
        this.databaseYaml.set("mutes", null);
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(final Ban ban) {
        this.databaseYaml.set("bans." + ban.getPlayerName(), null);
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void delete(final Mute mute) {
        this.databaseYaml.set("mutes." + mute.getPlayerName(), null);
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void shutdown() {
        this.save();
    }
}
