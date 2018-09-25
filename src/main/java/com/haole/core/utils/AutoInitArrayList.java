/**
 * 
 */
package com.haole.core.utils;

import java.util.ArrayList;

/**
 * @author 绳钧钊
 * 
 */
public class AutoInitArrayList<T> extends ArrayList<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -204856731039718275L;

	private Class<T> t = null;

	public AutoInitArrayList(Class<T> t) {
		super();
		this.t = t;
	}

	@Override
	public T get(int index) {
		// TODO 自动生成方法存根
		try {
			while (index >= size()) {
				add(t.newInstance());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.get(index);
	}
}
