package com.ercan.service.specification;

import com.ercan.entity.Product;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Lists;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
public class FilterSpecification implements Specification<Product> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = Lists.newArrayList();
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            Optional.ofNullable(criteria.getValue()).ifPresent(value -> predicates.add(builder.greaterThanOrEqualTo(
                    root.<String>get(criteria.getKey()), value.toString())));

        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            Optional.ofNullable(criteria.getValue()).ifPresent(value -> predicates.add(builder.lessThanOrEqualTo(
                    root.<String>get(criteria.getKey()), value.toString())));
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                Optional.ofNullable(criteria.getValue()).ifPresent(value -> predicates.add(
                        builder.like(root.<String>get(criteria.getKey()), "%" + value + "%")));
            } else {
                Optional.ofNullable(criteria.getValue()).ifPresent(value -> predicates.add(
                        builder.equal(root.get(criteria.getKey()), value)));
            }
        }
        return CollectionUtils.isEmpty(predicates) ? null : predicates.get(0);
    }


    //    @Override
//    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//        if (criteria.getOperation().equalsIgnoreCase(">")) {
//            return builder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
//        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
//            return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
//        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
//            if (root.get(criteria.getKey()).getJavaType() == String.class) {
//                return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
//            } else {
//                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
//            }
//        }
//        return null;
//    }

}
