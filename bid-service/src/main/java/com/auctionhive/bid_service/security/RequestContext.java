package com.auctionhive.bid_service.security;

import lombok.Getter;
import lombok.Setter;

public class RequestContext {

    private static final ThreadLocal<RequestContext> CONTEXT =
            new ThreadLocal<>();

    @Getter @Setter
    private Long userId;

    @Getter @Setter
    private String role;

    @Getter @Setter
    private String email;

    public static RequestContext get() {
        RequestContext ctx = CONTEXT.get();
        if (ctx == null) {
            ctx = new RequestContext();
            CONTEXT.set(ctx);
        }
        return ctx;
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
