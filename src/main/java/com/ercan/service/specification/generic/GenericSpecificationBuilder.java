package com.ercan.service.specification.generic;

import com.ercan.enums.SearchOperation;
import org.assertj.core.util.Lists;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GenericSpecificationBuilder<T> {
    private final List<SearchCriteriaOther> params;
    private final List<Specification<T>> specifications;

    public GenericSpecificationBuilder() {
        this.params = Lists.newArrayList();
        this.specifications = Lists.newArrayList();
    }

    public final GenericSpecificationBuilder<T> with(final String key,
                                                     final SearchOperation operation,
                                                     final List<Object> arguments) {

        return with(key, operation, false, arguments);
    }

    public final GenericSpecificationBuilder<T> with(final String key,
                                                     final SearchOperation operation,
                                                     final boolean isOrOperation,
                                                     final List<Object> arguments) {
        params.add(new SearchCriteriaOther(key, operation, isOrOperation, arguments));
        return this;
    }

    public final GenericSpecificationBuilder<T> with(Specification<T> specification) {
        specifications.add(specification);
        return this;
    }

    public Specification<T> build() {
        Specification<T> result = null;
        if (!params.isEmpty()) {
            result = new GenericSpecification<>(params.get(0));
            for (int index = 1; index < params.size(); ++index) {
                SearchCriteriaOther criteria = params.get(index);
                result = criteria.isOrOperation() ? Specification.where(result).or(new GenericSpecification<>(criteria))
                        : Specification.where(result).and(new GenericSpecification<>(criteria));
            }
        }
        if (!specifications.isEmpty()) {
            int index = 0;
            Optional.ofNullable(result).orElse(result = specifications.get(index++));
            for (; index < specifications.size(); ++index) {
                result = Specification.where(result).and(specifications.get(index));
            }
        }
        return result;
    }


}
