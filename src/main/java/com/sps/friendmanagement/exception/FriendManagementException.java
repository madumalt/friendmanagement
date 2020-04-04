package com.sps.friendmanagement.exception;

public class FriendManagementException extends Exception {

	public FriendManagementException() {
		super();
	}

	public FriendManagementException(String message) {
		super(message);
	}
	
	public FriendManagementException(String message, Throwable e) {
		super(message, e);
	}
}