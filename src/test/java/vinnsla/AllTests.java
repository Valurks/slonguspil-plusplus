package vinnsla;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BoardBehaviorTest.class, DiceTest.class, GameTest.class, PlayerTest.class, SnakesAndLaddersStrategyTest.class })
public class AllTests {
}
