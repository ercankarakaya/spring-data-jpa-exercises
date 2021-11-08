package com.ercan.service.specification.generic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericSpecification<T> implements Specification<T> {

    private SearchCriteriaOther criteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Object> arguments = criteria.getArguments();
        Object arg = arguments.get(0);

        switch (criteria.getSearchOperation()) {
            case EQUALITY:
                return builder.equal(root.get(criteria.getKey()), arg);
            case GREATER_THAN:
                return builder.greaterThan(root.get(criteria.getKey()), (Comparable) arg);
            case LESS_THAN:
                return builder.lessThan(root.get(criteria.getKey()), (Comparable) arg);
            case LIKE:
                return builder.like(root.get(criteria.getKey()), "%" + arg + "%");
            case IN:
                return root.get(criteria.getKey()).in(arguments);
        }

        return null;
    }

}
