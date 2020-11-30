package com.lucky.aop.annotation;

import com.lucky.aop.enums.Location;

import java.lang.annotation.*;

/**
 * 真实方法正常执行后执行
 * @author fk-7075
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Expand(Location.AFTER_RETURNING)
public @interface AfterReturning {

	/**
	 * 设置增强方法的唯一标记(默认值：方法名)
	 * @return
	 */
	String value() default "";

	/**
	 * 切面表达式
	 * P:{包检验表达式}
	 * C:{N[类名检验表达式],I[IOC_ID校验表达式],T[IOC_TYPE校验表达式],A[是否被注解]}
	 * M:{N[方法名校验表达式],A[是否被注解],AC[访问修饰符],O[要增强的继承自Object对象的方法]}
	 * P:{*}C:{N[HelloController,MyService]}M:{AC[*],N[show,query(int,String)]}
	 * @return
	 */
	String expres() default "";

	/**
	 * 配置切面(Class)，增强方法执行的范围，用来定位需要代理的真实类<br>
	 * pointCutClass的值必须以下列前缀开始,多个值使用","分隔:<br>
	 * <p>
	 *  ioc:表示增强一种或多种类型的所有组件,可选值有:[controller,service,repository,component] eg:pointCutClass="ioc:component,service"</p>
	 * <p>
	 *  id:表示增强一个或多个指定ID的IOC组件,eg:pointCutClass="id:beanId1,beanId2"</p>
	 * <p>
	 *  path:表示增强某个路径下的所有IOC组件，eg:pointCutClass="path:com.lucky.*" OR pointCutClass="path:com.lucky.User"</p>
	 * <p>
	 *  ann: 表示增强被某一个注解标注的所有IOC组件 eg:pointCutClass="ann:com.lucky.MyAnnotation,org.lucky.HelloAnnotation"
	 * </p>
	 * @return
	 */
	String pointCutClass() default "ioc:service";

	/**
	 * 配置切点(Method)， 增强方法执行的范围，用来定位需要代理的真实类的一些具体方法<br>
	 * 多个值使用","分隔,支持"*"、"!"<br>
	 * <p>
	 *   方法名定位：需要增强的方法名，eg:pointCutMethod="method1,method2"</p>
	 * <p>
	 *    方法名+参数类型定位：需要增强的方法名+参数列表，eg:pointCutMethod="method1(String,int),method2(User,Double)"</p>
	 * @return
	 */
	String pointCutMethod() default "public,*";

	/**
	 * 配置切点(Annotation),只有当切面中的方法被pointCutAnnotation中配置的注解所标识才执行代理
	 * @return
	 */
	Class<? extends Annotation>[] pointCutMethodAnn() default {};

	/**
	 * 优先级，优先级高的增强将会被优先执行
	 * @return
	 */
	double priority() default 5;
	
}
