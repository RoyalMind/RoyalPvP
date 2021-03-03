package net.royalmind.royalpvp.dependencies;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultDependency {

    private Economy economy;
    private Server server;
    private static final String VAULT_ID = "Vault";

    public VaultDependency(final Server server) {
        this.server = server;
    }

    public boolean haveVault() {
        final Plugin plugin = this.server.getPluginManager().getPlugin(VAULT_ID);
        if (plugin == null) return false;
        final RegisteredServiceProvider<Economy> registration = this.server.getServicesManager().getRegistration(Economy.class);
        if (registration == null) return false;
        this.economy = registration.getProvider();
        return (this.economy != null);
    }

    public Economy getEconomy() {
        return economy;
    }
}
