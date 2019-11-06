package com.rabobank.statement.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rabobank.statement.model.Record;

/**
 * Validate given records. (duplicate check,end balance check)
 */
@Service
public class ValidatorServiceImpl implements ValidatorService {
	/**
	 * @return List<Records> to get duplicate records form given list.
	 */
	public List<Record> getDuplicateRecords(List<Record> records) {
		Map<Integer, Record> uniqeRecords = new HashMap<Integer, Record>();
		List<Record> duplicateRecords = new ArrayList<Record>();
		Set<Record> distinct = new HashSet<>();
		for (Record record : records) {
			if (!distinct.add(record)) { // returns false if the object was already in the set.
				duplicateRecords.add(record);
			}
		}
		System.err.println(duplicateRecords.toString());
		return duplicateRecords;
	}

	/**
	 * @return List<Records> if startbalance - mutation != endbalance then
	 *         endbalance is wrong that list ll be returned.
	 */
	public List<Record> getEndBalanceErrorRecords(List<Record> records) {
		return records
				.stream().filter(e -> new BigDecimal(e.getStartBalance() + e.getMutation())
						.setScale(2, RoundingMode.HALF_UP).doubleValue() != e.getEndBalance())
				.collect(Collectors.toList());
	}
}
