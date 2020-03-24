package me.playernguyen.command;

import me.playernguyen.OptEco;
import me.playernguyen.OptEcoConfiguration;
import me.playernguyen.OptEcoLanguage;
import me.playernguyen.permission.OptEcoPermission;
import me.playernguyen.utils.ValidationChecker;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SubCommandAdd extends SubCommand {


    SubCommandAdd(String s, OptEco plugin) {
        super(s, plugin.getLanguageLoader().getLanguage(OptEcoLanguage.COMMAND_DESCRIBE_ADD), plugin);

        addArgument(CommandArguments.PLAYER);
        addArgument(CommandArguments.AMOUNT);

        addPermissions(OptEcoPermission.ADMIN);
        addPermissions(OptEcoPermission.EVERYTHING);
        addPermissions(OptEcoPermission.ADD);
    }

    @Override
    public boolean onPlayerCommand(Player player, ArrayList<String> args) {
        return this.execute(player, args);
    }

    @Override
    public boolean onConsoleCommand(CommandSender sender, ArrayList<String> args) {
        return this.execute(sender, args);
    }

    private boolean execute (CommandSender sender, ArrayList<String> args) {
        if (args.size() < 2) {
            sender.sendMessage(getMessageFormat().format(getHelp()));
            return true;
        }
        Player target = Bukkit.getServer().getPlayerExact(args.get(0));
        if (target == null) {
            sender.sendMessage(
                    getMessageFormat().format(getPlugin().getLanguageLoader().getLanguage(OptEcoLanguage.VAR_PLAYER_NOT_FOUND))
                            .replace("%who%", args.get(0))
            );
            return true;
        }
        String _value = args.get(1);
        if (!ValidationChecker.isNumber(_value)) {
            sender.sendMessage(getMessageFormat().format(
                    getPlugin().getLanguageLoader().getLanguage(OptEcoLanguage.VAR_NOT_A_NUMBER)
                            .replace("%value%", _value)
            ));
            return true;
        }
        double value = Double.parseDouble(_value);
        if (value < 0) {
            sender.sendMessage(
                    getMessageFormat()
                    .format(getPlugin().getLanguageLoader().getLanguage(OptEcoLanguage.VALUE_CANNOT_BE_NEGATIVE))
            );
            return true;
        }
        if ( this.getPlugin().getAccountLoader().addBalance(target, value) ) {
            // Send success message
            sender.sendMessage(
                    getMessageFormat().format(getPlugin().getLanguageLoader().getLanguage(OptEcoLanguage.COMMAND_SUCCEEDED_ADD))
                            .replace("%value%", getMessageFormat().numberFormat(value))
                            .replace("%currency%", getPlugin().getConfigurationLoader().getString(OptEcoConfiguration.CURRENCY_SYMBOL))
                            .replace("%who%", args.get(0))
            );
        } else {
            sender.sendMessage(
                    getMessageFormat().format(getPlugin().getLanguageLoader().getLanguage(OptEcoLanguage.COMMAND_FAILED_ADD))
                            .replace("%value%", getMessageFormat().numberFormat(value))
                            .replace("%currency%", getPlugin().getConfigurationLoader().getString(OptEcoConfiguration.CURRENCY_SYMBOL))
                            .replace("%who%", args.get(0))
            );
        }
        return true;
    }

    @Override
    public List<String> onTab(CommandSender commandSender, ArrayList<String> args) {
        if (args.size() <= 0) {
            ArrayList<String> _players = new ArrayList<>();
            for (Player player: Bukkit.getServer().getOnlinePlayers()) {
                _players.add(player.getName());
            }
            return _players;
        }
        return new ArrayList<>();
    }
}
