package net.royalmind.royalpvp;

import fr.minuskube.inv.InventoryManager;
import net.royalmind.royalpvp.configuration.Files;
import net.royalmind.royalpvp.data.DataHandler;
import net.royalmind.royalpvp.data.DataSource;
import net.royalmind.royalpvp.data.containers.effects.EffectsContainerImpl;
import net.royalmind.royalpvp.data.containers.inventory.InventoriesContainerImpl;
import net.royalmind.royalpvp.data.containers.threads.ThreadsContainerImpl;
import net.royalmind.royalpvp.effects.EffectsHandler;
import net.royalmind.royalpvp.inventory.InventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class RoyalPvP extends JavaPlugin {

    private DataSource dataSource;
    private Files files;

    private InventoryManager inventoryManager;
    //Containers
    private EffectsContainerImpl effectsContainer;
    private InventoriesContainerImpl inventoriesContainer;
    private ThreadsContainerImpl threadsContainer;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.files = new Files(this);
        try {
            this.dataSource = new DataSource(this);
        } catch (final Exception ex) {
            this.getPluginLoader().disablePlugin(this);
            this.getLogger().severe("Database not found, plugin has been disabled.");
            return;
        }
        final FileConfiguration config = files.getConfig().getFileConfiguration();
        final FileConfiguration configEffects = files.getEffects().getFileConfiguration();
        this.effectsContainer = new EffectsContainerImpl(this, config, this.threadsContainer);
        this.inventoriesContainer = new InventoriesContainerImpl(this, configEffects);
        this.threadsContainer = new ThreadsContainerImpl();
        this.inventoryManager = new InventoryManager(this);
        this.inventoryManager.init();
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryHandler(this.effectsContainer, this.inventoriesContainer, this.inventoryManager, configEffects), this);
        pluginManager.registerEvents(new EffectsHandler(this), this);
        pluginManager.registerEvents(new DataHandler(config, this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            this.dataSource.close();
        } catch (final Exception ex) { }
    }

    public Files getFiles() {
        return files;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public EffectsContainerImpl getEffectsContainer() {
        return effectsContainer;
    }

    public InventoriesContainerImpl getInventoriesContainer() {
        return inventoriesContainer;
    }

    public ThreadsContainerImpl getThreadsContainer() {
        return threadsContainer;
    }
}
