package org.sparta.newsfeed.friend.exception;

public class NoAuthorizationException extends RuntimeException {
    public NoAuthorizationException(String message) {
        super(message);
    }
}
