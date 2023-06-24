package dev.mrkevr.ecommerce.entity;

import java.util.List;

public enum OrderStatus {

	PENDING, CANCELLED, ACCEPTED, DENIED, IN_PROGRESS, TO_SHIP, TO_RECEIVE, COMPLETED, RETURNED;

	public static List<OrderStatus> activeStatuses() {
		return List.of(PENDING, ACCEPTED, IN_PROGRESS, TO_SHIP, TO_RECEIVE);
	}

	public static List<OrderStatus> inactiveStatuses() {
		return List.of(DENIED, CANCELLED, COMPLETED, RETURNED);
	}
}