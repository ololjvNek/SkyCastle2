package pl.ololjvNek.skycastle.commands.sc;

import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.commands.PlayerCommand;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.Util;

public class RejestrujCommand extends PlayerCommand {

    public RejestrujCommand() {
        super("rejestruj", "rejestruj", "discord", "core.default");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        User u = UserManager.getUser(p);
        if(u != null){
            if(u.getDiscordTag() != null){
                Util.sendMessage(p, "&aKonto zostalo polaczone pomyslnie!");
            }else{
                Util.sendMessage(p, "&cAby polaczyc konto napisz do bota na discordzie o nazwie &2'DINERON.NET - Dżordż' &ckomende !rejestruj");
            }
        }
        return false;
    }
}
