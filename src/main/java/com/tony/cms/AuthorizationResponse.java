package com.tony.cms;

public class AuthorizationResponse {
    private static final String DEFAULT_REASON = "";
    private final boolean isAllowed;
    private final String failureReason;

    public AuthorizationResponse(boolean isAllowed) {
        this(isAllowed, DEFAULT_REASON);
    }

    public AuthorizationResponse(boolean isAllowed, String failureReason) {
        this.isAllowed = isAllowed;
        this.failureReason = failureReason;
    }


    public boolean allowed() {
        return isAllowed;
    }

    public String reason() {
        return failureReason;
    }
}
