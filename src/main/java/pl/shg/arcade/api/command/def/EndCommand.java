/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander JagieĹ‚Ĺ‚o <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command.def;

import java.util.Arrays;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.match.MatchType;
import pl.shg.arcade.api.match.PlayerWinner;
import pl.shg.arcade.api.match.TeamWinner;
import pl.shg.arcade.api.match.UnresolvedWinner;
import pl.shg.arcade.api.match.Winner;
import pl.shg.arcade.api.team.ObserverTeamBuilder;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class EndCommand extends Command {
    public EndCommand() {
        super(new String[] {"end", "finish"},
                "Zakoncz obecnie grany mecz", "end [.|?|*|<winner>]");
        this.setOption(".", "przerwanie");
        this.setOption("?", "najlepszy gracz lub druzyna");
        this.setOption("*", "remis");
        this.setPermission("arcade.command.end");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (Arcade.getMatches().getStatus() != MatchStatus.PLAYING) {
            sender.sendError("Aby zakonczyc mecz musi byc on w trakcie trwania!");
        } else if (args.length == 0 || args[0].equals(".")) {
            this.end(sender, null);
        } else if (args[0].equalsIgnoreCase("?")) {
            Winner winner = Arcade.getMatches().getWinner();
            if (winner != null) {
                this.end(sender, winner);
            } else {
                sender.sendError("Nie mozna wyszukac najlepszego gracza lub druzyny w obecnej grze.");
            }
        } else if (args[0].equals("*")) {
            this.end(sender, new UnresolvedWinner());
        } else {
            switch (Arcade.getMatches().getMatch().getType()) {
                case PLAYERS:
                    Player player = Arcade.getServer().getPlayer(args[0]);
                    if (player != null) {
                        if (player.getTeam().getID().equals(ObserverTeamBuilder.getTeamID())) {
                            sender.sendError("Obserwator nie moze wygrac meczu.");
                        } else {
                            this.end(sender, new PlayerWinner(player));
                        }
                    } else {
                        sender.sendError("Gracz o podanym pseudonimie nie zostal odnaleziony.");
                    }
                    break;
                case TEAMS:
                    Team team = Arcade.getTeams().getTeam(args[0]);
                    if (team != null) {
                        this.end(sender, new TeamWinner(team));
                    } else {
                        sender.sendError("Druzyna o podanej nazwie nie zostala odnaleziona.");
                    }
                    break;
                default:
                    sender.sendError("Tryb gry nie zostal rozpoznany! Mozliwe opcje: " + Arrays.toString(MatchType.values()));
                    break;
            }
            
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
    
    private void end(Sender sender, Winner winner) {
        Validate.notNull(sender, "sender can not be null");
        String name = "przerwano";
        if (winner != null) {
            name = winner.getName();
        }
        sender.sendSuccess("Wymuszanie konca meczu. Wynik - " + name);
        Arcade.getMatches().getMatch().end(winner);
    }
}
