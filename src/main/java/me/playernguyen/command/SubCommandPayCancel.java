package me.playernguyen.command;

import me.playernguyen.OptEco;
import me.playernguyen.OptEcoLanguage;
import me.playernguyen.permission.OptEcoPermission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SubCommandPayCancel extends SubCommand {
    SubCommandPayCancel(String command, OptEco plugin) {
        super(command, plugin.getLanguageLoader().getLanguage(OptEcoLanguage.COMMAND_DESCRIBE_CANCEL), plugin);

        addPermissions(OptEcoPermission.EVERYTHING);
        addPermissions(OptEcoPermission.ADMIN);
        addPermissions(OptEcoPermission.USER);

        addPermissions(OptEcoPermission.CANCEL);
    }

    @Override
    public boolean onPlayerCommand(Player player, ArrayList<String> args) {
        if ( ! getPlugin().getTransactionManager().hasTransaction(player) ) {
            player.sendMessage(
                    getMessageFormat()
                            .format(getPlugin().getLanguageLoader().getLanguage(OptEcoLanguage.PAY_TRANSACTION_NOT_EXIST))
            );
            return true;
        }

        if (getPlugin().getTransactionManager().getTransaction(player).cancel() ) {
            player.sendMessage(
                    getMessageFormat().format(getPlugin().getLanguageLoader().getLanguage(OptEcoLanguage.PAY_DENY))
            );
        } else {
            player.sendMessage(
                    getMessageFormat().format(getPlugin().getLanguageLoader().getLanguage(OptEcoLanguage.PAY_FAILED))
            );
        }
        return true;
    }

    @Override
    public boolean onConsoleCommand(CommandSender sender, ArrayList<String> args) {
        return true;
    }

    @Override
    public List<String> onTab(CommandSender commandSender, ArrayList<String> args) {
        return null;
    }
}
