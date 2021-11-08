package com.ercan.utilities;

import com.ercan.exceptions.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PageUtil {

    private static final int DEFAULT_SIZE_PAGE = 20;


    private static List<String> split(String search, String delimiter) {
        return Stream.of(search.split(delimiter))
                .collect(Collectors.toList());
    }


    /**
     * Get request pageable. Page Request Builder. custom pageable
     *
     * @param size  the number of items to collect
     * @param page  page number
     * @param order search order filter (eg: field|ASC)
     * @return PageRequest
     */
    public static PageRequest getPageable(int size, int page, String order) {

        int pageSize = (size <= 0) ? DEFAULT_SIZE_PAGE : size;
        int currentPage = (page <= 0) ? 1 : page;

        try {
            if (order != null && !order.isEmpty()) {

                final String FILTER_CONDITION_DELIMITER = "\\|";

                List<String> values = split(order, FILTER_CONDITION_DELIMITER);
                String column = values.get(0);
                String sortDirection = values.get(1);

                if (sortDirection.equalsIgnoreCase("ASC")) {
                    return PageRequest.of((currentPage - 1), pageSize, Sort.by(Sort.Direction.ASC, column));
                } else if (sortDirection.equalsIgnoreCase("DESC")) {
                    return PageRequest.of((currentPage - 1), pageSize, Sort.by(Sort.Direction.DESC, column));
                } else {
                    throw new IllegalArgumentException(String.format("Value for param 'order' is not valid : %s , must be 'asc' or 'desc'", sortDirection));
                }

            } else {
                return PageRequest.of((currentPage - 1), pageSize);
            }
        } catch (Exception ex) {
            throw new BadRequestException("Cannot create condition filter " + ex.getMessage());
        }
    }

}
