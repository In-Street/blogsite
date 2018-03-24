package cyf.blog.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public LogsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAuthorIdIsNull() {
            addCriterion("author_id is null");
            return (Criteria) this;
        }

        public Criteria andAuthorIdIsNotNull() {
            addCriterion("author_id is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorIdEqualTo(Integer value) {
            addCriterion("author_id =", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdNotEqualTo(Integer value) {
            addCriterion("author_id <>", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdGreaterThan(Integer value) {
            addCriterion("author_id >", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("author_id >=", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdLessThan(Integer value) {
            addCriterion("author_id <", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdLessThanOrEqualTo(Integer value) {
            addCriterion("author_id <=", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdIn(List<Integer> values) {
            addCriterion("author_id in", values, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdNotIn(List<Integer> values) {
            addCriterion("author_id not in", values, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdBetween(Integer value1, Integer value2) {
            addCriterion("author_id between", value1, value2, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("author_id not between", value1, value2, "authorId");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andCreatedIsNull() {
            addCriterion("created is null");
            return (Criteria) this;
        }

        public Criteria andCreatedIsNotNull() {
            addCriterion("created is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedEqualTo(Date value) {
            addCriterion("created =", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedNotEqualTo(Date value) {
            addCriterion("created <>", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedGreaterThan(Date value) {
            addCriterion("created >", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedGreaterThanOrEqualTo(Date value) {
            addCriterion("created >=", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedLessThan(Date value) {
            addCriterion("created <", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedLessThanOrEqualTo(Date value) {
            addCriterion("created <=", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedIn(List<Date> values) {
            addCriterion("created in", values, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedNotIn(List<Date> values) {
            addCriterion("created not in", values, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedBetween(Date value1, Date value2) {
            addCriterion("created between", value1, value2, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedNotBetween(Date value1, Date value2) {
            addCriterion("created not between", value1, value2, "created");
            return (Criteria) this;
        }

        public Criteria andOperateTypeIsNull() {
            addCriterion("operate_type is null");
            return (Criteria) this;
        }

        public Criteria andOperateTypeIsNotNull() {
            addCriterion("operate_type is not null");
            return (Criteria) this;
        }

        public Criteria andOperateTypeEqualTo(String value) {
            addCriterion("operate_type =", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeNotEqualTo(String value) {
            addCriterion("operate_type <>", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeGreaterThan(String value) {
            addCriterion("operate_type >", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeGreaterThanOrEqualTo(String value) {
            addCriterion("operate_type >=", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeLessThan(String value) {
            addCriterion("operate_type <", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeLessThanOrEqualTo(String value) {
            addCriterion("operate_type <=", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeLike(String value) {
            addCriterion("operate_type like", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeNotLike(String value) {
            addCriterion("operate_type not like", value, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeIn(List<String> values) {
            addCriterion("operate_type in", values, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeNotIn(List<String> values) {
            addCriterion("operate_type not in", values, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeBetween(String value1, String value2) {
            addCriterion("operate_type between", value1, value2, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateTypeNotBetween(String value1, String value2) {
            addCriterion("operate_type not between", value1, value2, "operateType");
            return (Criteria) this;
        }

        public Criteria andOperateObjectIsNull() {
            addCriterion("operate_object is null");
            return (Criteria) this;
        }

        public Criteria andOperateObjectIsNotNull() {
            addCriterion("operate_object is not null");
            return (Criteria) this;
        }

        public Criteria andOperateObjectEqualTo(String value) {
            addCriterion("operate_object =", value, "operateObject");
            return (Criteria) this;
        }

        public Criteria andOperateObjectNotEqualTo(String value) {
            addCriterion("operate_object <>", value, "operateObject");
            return (Criteria) this;
        }

        public Criteria andOperateObjectGreaterThan(String value) {
            addCriterion("operate_object >", value, "operateObject");
            return (Criteria) this;
        }

        public Criteria andOperateObjectGreaterThanOrEqualTo(String value) {
            addCriterion("operate_object >=", value, "operateObject");
            return (Criteria) this;
        }

        public Criteria andOperateObjectLessThan(String value) {
            addCriterion("operate_object <", value, "operateObject");
            return (Criteria) this;
        }

        public Criteria andOperateObjectLessThanOrEqualTo(String value) {
            addCriterion("operate_object <=", value, "operateObject");
            return (Criteria) this;
        }

        public Criteria andOperateObjectLike(String value) {
            addCriterion("operate_object like", value, "operateObject");
            return (Criteria) this;
        }

        public Criteria andOperateObjectNotLike(String value) {
            addCriterion("operate_object not like", value, "operateObject");
            return (Criteria) this;
        }

        public Criteria andOperateObjectIn(List<String> values) {
            addCriterion("operate_object in", values, "operateObject");
            return (Criteria) this;
        }

        public Criteria andOperateObjectNotIn(List<String> values) {
            addCriterion("operate_object not in", values, "operateObject");
            return (Criteria) this;
        }

        public Criteria andOperateObjectBetween(String value1, String value2) {
            addCriterion("operate_object between", value1, value2, "operateObject");
            return (Criteria) this;
        }

        public Criteria andOperateObjectNotBetween(String value1, String value2) {
            addCriterion("operate_object not between", value1, value2, "operateObject");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}