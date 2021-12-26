package vuesample.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import vuesample.annotation.Query;
import vuesample.annotation.Query.Operand;

/**
 * @author Bo Wang
 *
 *         vue-sample-server 25 Dec 2021
 */
@Slf4j
public class QueryHelp {
	@SuppressWarnings("unchecked")
	public static <R, Q> Predicate getPredicate(Root<R> root, Q query, CriteriaBuilder cb) {
		List<Predicate> andList = new ArrayList<>();
		List<Predicate> orList = new ArrayList<Predicate>();
		List<Predicate> mustAndList = new ArrayList<>();
		List<Predicate> applicationIdList = new ArrayList<Predicate>();
		List<Predicate> globalList = new ArrayList<Predicate>();

		boolean gloablOr = false;
		if (query == null) {
			return cb.and(andList.toArray(new Predicate[andList.size()]));
		}
		try {
			List<Field> fields = getAllFields(query.getClass(), new ArrayList<>());
			for (Field field : fields) {
				List<Predicate> list = andList;
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				Query q = field.getAnnotation(Query.class);
				if (q != null) {
					if (q.operand().equals(Operand.OR)) {
						list = orList;
					} else if (q.operand().equals(Operand.MUSTAND)) {
						list = mustAndList;
					}
					String propName = q.propName();
					String joinName = q.joinName();
					String attributeName = isBlank(propName) ? field.getName() : propName;
					Class<?> fieldType = field.getType();
					Object val = field.get(query);
					if (ObjectUtil.isNull(val) || ObjectUtil.isEmpty(val)) {
						continue;
					}

					Join join = null;
					if (ObjectUtil.isNotEmpty(joinName)) {
						switch (q.join()) {
						case LEFT:
							join = root.join(joinName, JoinType.LEFT);
							break;
						case RIGHT:
							join = root.join(joinName, JoinType.RIGHT);
							break;
						case INNER:
							join = root.join(joinName, JoinType.INNER);
							break;
						}
					}
					switch (q.type()) {
					case EQUAL:
						list.add(cb.equal(
								getExpression(attributeName, join, root).as((Class<? extends Comparable>) fieldType),
								val));
						break;
					case NOT_EQUAL:
						list.add(cb.notEqual(
								getExpression(attributeName, join, root).as((Class<? extends Comparable>) fieldType),
								val));
						break;
					case GREATER_THAN:
						list.add(cb.greaterThanOrEqualTo(
								getExpression(attributeName, join, root).as((Class<? extends Comparable>) fieldType),
								(Comparable) val));
						break;
					case LESS_THAN:
						list.add(cb.lessThanOrEqualTo(
								getExpression(attributeName, join, root).as((Class<? extends Comparable>) fieldType),
								(Comparable) val));
						break;
					case LESS_THAN_NQ:
						list.add(cb.lessThan(
								getExpression(attributeName, join, root).as((Class<? extends Comparable>) fieldType),
								(Comparable) val));
						break;
					case INNER_LIKE:
						list.add(cb.like(getExpression(attributeName, join, root).as(String.class),
								"%" + val.toString() + "%"));
						break;
					case LEFT_LIKE:
						list.add(cb.like(getExpression(attributeName, join, root).as(String.class),
								"%" + val.toString()));
						break;
					case RIGHT_LIKE:
						list.add(cb.like(getExpression(attributeName, join, root).as(String.class),
								val.toString() + "%"));
						break;
					case IN:
						if (CollUtil.isNotEmpty((Collection<Long>) val)) {
							list.add(getExpression(attributeName, join, root).in((Collection<Long>) val));
						}
						break;
					}
				}
				field.setAccessible(accessible);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		List<Predicate> predicates = new ArrayList<Predicate>();
		Predicate predicate = null;
		if (!andList.isEmpty() && orList.isEmpty()) {
			andList.addAll(mustAndList);
			predicate = cb.and(andList.toArray(new Predicate[andList.size()]));
			predicates.add(predicate);
		} else if (!andList.isEmpty() && !orList.isEmpty()) {
			predicate = cb.and(cb.and(mustAndList.toArray(new Predicate[mustAndList.size()])),
					cb.or(cb.and(andList.toArray(new Predicate[andList.size()])),
							cb.or(orList.toArray(new Predicate[orList.size()]))));
			predicates.add(predicate);
		} else if (!orList.isEmpty()) {
			predicate = cb.and(cb.and(mustAndList.toArray(new Predicate[andList.size()])),
					cb.and(cb.or(orList.toArray(new Predicate[orList.size()]))));
			predicates.add(predicate);
		} else if (!mustAndList.isEmpty()) {
			predicate = cb.and(mustAndList.toArray(new Predicate[mustAndList.size()]));
			predicates.add(predicate);
		}
		if (!applicationIdList.isEmpty()) {
			if (!globalList.isEmpty()) {

				if (gloablOr) {
					predicate = cb.and(cb.or(cb.and(applicationIdList.toArray(new Predicate[applicationIdList.size()])),
							cb.and(globalList.toArray(new Predicate[globalList.size()]))));
				} else {
					predicate = cb
							.and(cb.and(cb.and(applicationIdList.toArray(new Predicate[applicationIdList.size()])),
									cb.and(globalList.toArray(new Predicate[globalList.size()]))));
				}
				predicates.add(predicate);
			} else {
				predicate = cb.and(applicationIdList.toArray(new Predicate[applicationIdList.size()]));
				predicates.add(predicate);
			}
		} else {
			if (!globalList.isEmpty()) {
				predicate = cb.and(globalList.toArray(new Predicate[globalList.size()]));
				predicates.add(predicate);
			}
		}

		predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
		return predicate;
	}

	@SuppressWarnings("unchecked")
	private static <T, R> Expression<T> getExpression(String attributeName, Join join, Root<R> root) {
		if (ObjectUtil.isNotEmpty(join)) {
			return join.get(attributeName);
		} else
			return root.get(attributeName);
	}

	@SuppressWarnings("unchecked")
	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private static List<Field> getAllFields(Class clazz, List<Field> fields) {
		if (clazz != null) {
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
			getAllFields(clazz.getSuperclass(), fields);
		}
		return fields;
	}

	public static <R, Q> Specification query(Q query) {
		return (root, criteriaQuery, cb) -> {
			Predicate predicate = QueryHelp.getPredicate(root, query, cb);
			criteriaQuery.distinct(true);
			return predicate;

		};
	}

	public static <R, Q> Specification queryGroupBy(Q query, String... groupByName) {
		return (root, criteriaQuery, cb) -> {
			Predicate predicate = QueryHelp.getPredicate(root, query, cb);
			criteriaQuery.distinct(true);
			List<Expression> expressions = new ArrayList<Expression>();
			for (String groupName : groupByName) {
				expressions.add(root.get(groupName));
			}
			criteriaQuery.groupBy(expressions.toArray(new Expression[] {}));
			return predicate;

		};
	}
}
