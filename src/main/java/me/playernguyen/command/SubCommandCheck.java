package me.playernguyen.command;

import me.playernguyen.OptEco;
import me.playernguyen.OptEcoConfiguration;
import me.playernguyen.OptEcoLanguage;
import me.playernguyen.permission.OptEcoPermission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SubCommandCheck extends SubCommand {

    public SubCommandCheck(String command, OptEco optEco) {
        super(command, optEco.getLanguageLoader().getLanguage(OptEcoLanguage.COMMAND_DESCRIBE_CHECK), optEco);

        addArgument(CommandArguments.PLAYER);

        addPermissions(OptEcoPermission.EVERYTHING);
        addPermissions(OptEcoPermission.CHECK);
        addPermissions(OptEcoPermission.ADMIN);
    }

    @Override
    public boolean onPlayerCommand(Player player, ArrayList<String> args) {

        if (args.size() < 1) {
            player.sendMessage(
                    getMessageFormat().format(
                            getPlugin().getLanguageLoader().getLanguage(OptEcoLanguage.CHECK_SELF)
                                    .replace("%value%", String.valueOf(getPlugin().getAccountLoader().getBalance(player)))
                                    .replace("%currency%", getPlugin().getConfigurationLoader().getString(OptEcoConfiguration.CURRENCY_SYMBOL))
                    )
            );
            return true;
        }
        return this.exec(player, args);
    }

    @Override
    public boolean onConsoleCommand(CommandSender sender, ArrayList<String> args) {
        return this.exec(sender, args);
    }

    private boolean exec(CommandSender sender, ArrayList<String> args) {
        if (args.size() < 1) {
            sender.sendMessage(getMessageFormat().format(getHelp()));
            return true;
        }
        String _target = args.get(0);
        Player target = Bukkit.getServer().getPlayerExact(_target);
        if (target == null) {
            sender.sendMessage(
                    getMessageFormat().format(getPlugin().getLanguageLoader().getLanguage(OptEcoLanguage.VAR_PLAYER_NOT_FOUND)
                            .replace("%who%", _target))
            );
            return true;
        }
        sender.sendMessage(
               getMessageFormat().format(
                       getPlugin().getLanguageLoader().getLanguage(OptEcoLanguage.CHECK_ANOTHER)
                               .replace("%who%", _target)
                               .replace("%value%", getMessageFormat().numberFormat(getPlugin().getAccountLoader().getBalance(target)))
                               .replace("%currency%", getPlugin().getConfigurationLoader().getString(OptEcoConfiguration.CURRENCY_SYMBOL))
               )
        );
        return true;
    }

    @Override
    public List<String> onTab(CommandSender commandSender, ArrayList<String> args) {
        return null;
    }
}
