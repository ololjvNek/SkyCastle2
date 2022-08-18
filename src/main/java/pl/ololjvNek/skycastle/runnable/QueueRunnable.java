package pl.ololjvNek.skycastle.runnable;

import org.bukkit.scheduler.BukkitRunnable;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.managers.QueueManager;
import pl.ololjvNek.skycastle.managers.RankingManager;

public class QueueRunnable extends BukkitRunnable {
    @Override
    public void run() {
        QueueManager.setPlayerJoin(0);
        Main.setShowTopPoints(!Main.isShowTopPoints());
        RankingManager.sortStarsRankings();
        RankingManager.sortUserRankings();
        RankingManager.sortTeamRankings();
    }
}
