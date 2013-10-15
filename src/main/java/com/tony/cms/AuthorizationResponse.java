package com.tony.cms;

public class AuthorizationResponse {
    private final boolean isAllowed;

    public AuthorizationResponse(boolean isAllowed) {
        this.isAllowed = isAllowed;
    }


    public boolean allowed() {
        return isAllowed;
    }

}
