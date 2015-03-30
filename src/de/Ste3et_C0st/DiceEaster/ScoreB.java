package de.Ste3et_C0st.DiceEaster;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class ScoreB {
	

	Player player;
	Score find;
	Score hidden;
	
	public ScoreB(Player player){
		if(DiceEaster.getInstance().world.contains(player.getWorld().getName())){
			ScoreboardManager manager = Bukkit.getScoreboardManager();
			Scoreboard board = manager.getNewScoreboard();
			Team team = board.registerNewTeam("DE-" + player.getName().indexOf(0,7));
			this.player = player;
			team.addPlayer(player);
			Objective objective = board.registerNewObjective("DiceEaster", "dummy");
			objective.setDisplayName(variablen.msg.get("ScoreboardHeader"));
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			find = objective.getScore(variablen.msg.get("ScoreboardFind"));
			hidden = objective.getScore(variablen.msg.get("ScoreboardHidden"));
			find.setScore(DiceEaster.getInstance().getPlayerScore(player));
			hidden.setScore(DiceEaster.egglist.size());
			player.setScoreboard(board);
		}
	}
	
	public void update(){
		Integer hiddenEggs = DiceEaster.egglist.size();
		Integer foundEggs = DiceEaster.getInstance().getPlayerScore(player);
		if(foundEggs > hiddenEggs) foundEggs = hiddenEggs;
		
		find.setScore(foundEggs);
		hidden.setScore(hiddenEggs);
	}
	
	public void destroy(){
			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
}
