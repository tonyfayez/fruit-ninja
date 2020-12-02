package game.gamestate;

import commands.DispenseCommand;
import game.Game;
import game.objects.Sliceable;
import game.objects.SliceableType;


public class LivesDispenser extends GameState {
    private int dispensed = 0;
    
    public LivesDispenser(int maxInterval) {
        super(maxInterval);
    }

    @Override
    DispenseCommand nextDispense() {
        double fatalChance = 0.04, dangerousChance = 0.06, special1Chance = 0.05, special2Chance = 0.03;
        double r = random.nextDouble();
        double p = fatalChance;
        if(dispensed++ >= 25) {
            stop();
            int maxInterval = this.maxInterval - 100;
            if(maxInterval < 700)
                Game.getCurrentGame().setState(new FinalLivesDispenser(700));
            else
                Game.getCurrentGame().setState(new LivesDispenser(maxInterval));
        }
        if(r<p) {
            delay = random.nextInt(maxInterval);
            return new DispenseCommand(Sliceable.newSliceable(SliceableType.FATAL_BOMB));
        }
        else if(r<(p+=dangerousChance)) {
            delay = random.nextInt(maxInterval);
            return new DispenseCommand(Sliceable.newSliceable(SliceableType.DANGEROUS_BOMB));
        }
        else if(r<(p+=special1Chance)) {
            delay = random.nextInt(maxInterval);
            return new DispenseCommand(Sliceable.newSliceable(SliceableType.SPECIAL_1));
        }
        else if(r<(p+=special2Chance)) {
            delay = random.nextInt(maxInterval);
            return new DispenseCommand(Sliceable.newSliceable(SliceableType.SPECIAL_2));
        }
        else {
            SliceableType type = SliceableType.class.getEnumConstants()[random.nextInt(6)];
            delay = random.nextInt(maxInterval);
            return new DispenseCommand(Sliceable.newSliceable(type));
        }
    }

}
