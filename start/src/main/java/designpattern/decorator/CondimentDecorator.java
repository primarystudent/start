package designpattern.decorator;

/**
 * 必须让装饰者能取代被装饰者，所以需要继承
 * @author wangwenlong
 *
 */
public abstract class CondimentDecorator extends Beverage {

	public abstract String getDescription();

}
