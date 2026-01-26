package com.auctionhive.user_service.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestContext {

    private static final ThreadLocal<RequestContext> context =
            ThreadLocal.withInitial(RequestContext::new);

    private Long userId;
    private String role;
    private String email;

    public static RequestContext get() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}
