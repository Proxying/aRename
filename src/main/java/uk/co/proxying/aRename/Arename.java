package uk.co.proxying.aRename;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.proxying.aRename.anvilGui.nms.anvil.AnvilHandlerInterface;
import uk.co.proxying.aRename.commands.RenameGive;
import uk.co.proxying.aRename.listeners.CoreListener;

import java.util.ArrayList;
import java.util.List;

public final class Arename extends JavaPlugin {

    public static Arename getInstance() {
        return instance;
    }

    private static Arename instance;

    //Anvil GUI ported from AnvilAPI 1.7.10 to allow for 1.9.x compatibility.
    public AnvilHandlerInterface getAnvilGUI() {
        return anvilGUI;
    }

    private AnvilHandlerInterface anvilGUI;

    public List<String> getPlayersRenaming() {
        return playersRenaming;
    }

    private List<String> playersRenaming = new ArrayList<>();

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new CoreListener(), this);
        getCommand("rgive").setExecutor(new RenameGive());
        String version = this.getPackageVersion();
        try {
            Class clazz = Class.forName("uk.co.proxying.aRename.anvilGui.nms." + version + ".AnvilHandler");
            if (AnvilHandlerInterface.class.isAssignableFrom(clazz)) {
                anvilGUI = (AnvilHandlerInterface) clazz.newInstance();
            }
        } catch (Exception ex) {
            this.getLogger().severe("Could not find support for version: " + version);
            this.setEnabled(false);
        }
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public String getPackageVersion() {
        String pkg = this.getServer().getClass().getPackage().getName();
        return pkg.substring(pkg.lastIndexOf('.') + 1);
    }
}
