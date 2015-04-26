/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command.def;

import java.util.List;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.map.team.Team;

/**
 *
 * @author Aleksander
 */
public class TeamsCommand extends Command {
    public TeamsCommand() {
        super(new String[] {"teams", "teamlist"},
                "Pokaz liste zarejestrowanych obecnie druzyn na mapie oraz informacje", "teams [-i]",
                new char[] {'i'});
        this.setOption("-i", "informacje konfiguracyjne");
        this.setPermission("arcade.command.teams");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (!this.hasFlag(args, 'i')) {
            this.showTeams(sender, false);
        } else if (!this.canNot(sender, "arcade.command.teams.info")) {
            this.showTeams(sender, true);
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
    
    private void showTeams(Sender sender, boolean info) {
        StringBuilder builder = new StringBuilder();
        List<Team> teams = Arcade.getTeams().getTeams();
        
        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            builder.append(Color.YELLOW).append(i + 1).append(". ").append(team.getDisplayName());
            builder.append(Color.GRAY).append(" (").append(team.getPlayers().size()).append("/").append(team.getSlots()).append(")");
            if (info) {
                builder.append(Color.GREEN).append(" teamColor=").append(team.getTeamColor().toString());
                builder.append(", frendly=").append(team.isFrendlyFire());
            }
            builder.append("\n");
        }
        
        sender.sendMessage(Command.getTitle("Druzyny", Color.GRAY + "(" + teams.size() + ")"));
        sender.sendMessage(builder.toString());
    }
}
