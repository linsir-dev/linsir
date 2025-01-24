package com.linsir.core.support;

import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 跟踪类变动比较
 *
 * @author L.cm
 */
@Getter
@ToString
public class BeanDiff {
	/**
	 * 变更字段
	 */
	private final Set<String> fields = new HashSet<>();
	/**
	 * 旧值
	 */
	private final Map<String, Object> oldValues = new HashMap<>();
	/**
	 * 新值
	 */
	private final Map<String, Object> newValues = new HashMap<>();
}
