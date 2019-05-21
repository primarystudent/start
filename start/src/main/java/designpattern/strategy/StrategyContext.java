package designpattern.strategy;

public class StrategyContext {

	private StrategyInterface strategy;

	public StrategyContext(StrategyInterface strategy) {
		this.strategy = strategy;
	}
	
	public void contextMethod(){
		strategy.process();
	}
	
	public static void main(String[] args) {
		StrategyContext context = new StrategyContext(new StrategyImpl1());
		context.contextMethod();
		context = new StrategyContext(new StrategyImpl2());
		context.contextMethod();
	}
}
