package project.manager.server.security.filter;

public final class ConstantUrl {
    public static final String[] NO_NEED_AUTH_URLS = { "/favicon.ico",
            "/auth/kakao", "/auth/kakao/callback",
            "/auth/google", "/auth/google/callback",
            "/auth/apple", "/auth/apple/callback",
            "/auth/refresh", "/image" };
}
