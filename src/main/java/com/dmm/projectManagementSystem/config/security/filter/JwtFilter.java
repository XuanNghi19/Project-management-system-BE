package com.dmm.projectManagementSystem.config.security.filter;

import com.dmm.projectManagementSystem.config.security.JwtUtils;
import com.dmm.projectManagementSystem.model.User;
import com.nimbusds.jwt.SignedJWT;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Value("${api.prefix}")
    private String apiPrefix;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            if(isBypassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            final String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }

            // Lấy ra chuỗi token
            final String token = authorizationHeader.substring(7);
            // Lấy ra phoneNumber từ token
            final String idNum = SignedJWT.parse(token).getJWTClaimsSet().getSubject();

            if(idNum != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Lấy ra user bằng idNum từ token
                User user = (User) userDetailsService.loadUserByUsername(idNum);

                createSessionForUser(user, idNum, request);
            }
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private void createSessionForUser(
            User user,
            String idNum,
            HttpServletRequest request
    ) {

        // Xác thực người dùng, tạo token với thông tin idNum và Role - quyền hạn
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                idNum, null, user.getAuthorities()
        );

        // Gán các chi tiết liên quan đến phiên làm việc (session) và thông tin người dùng vào đối tượng (địa chỉ ip)
        // WebAuthenticationDetails chứa thông tin chi tiết về môi trường web nơi yêu cầu xác thực đang diễn ra (chẳng hạn như IP, session ID...)
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // đánh dấu là đã xác thực trong hệ thống, và thông tin xác thực (bao gồm cả quyền hạn) sẽ được lưu trữ
        // trong SecurityContext, để có thể sử dụng lại trong các bước xử lý sau này mà không cần xác thực lại
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of("/api-docs", "GET"),
                Pair.of("/api-docs/**", "GET"),
                Pair.of("/swagger-resources", "GET"),
                Pair.of("/swagger-resources/**", "GET"),
                Pair.of("/configuration/ui", "GET"),
                Pair.of("/configuration/security", "GET"),
                Pair.of("/swagger-ui", "GET"),
                Pair.of("/swagger-ui.html", "GET"),
                Pair.of("/swagger-ui/index.html", "GET"),
                Pair.of("/create_group", "POST"),
                Pair.of("/invite", "GET"),
                Pair.of("/project/", "GET"),
                Pair.of("/project/get_meeting", "GET"),
                Pair.of("/project/get_council", "GET"),
                Pair.of("/group/**", "POST"),
                Pair.of("/group/create_group", "POST"),
                Pair.of("/group/invite", "POST"),
                Pair.of("/group/accept", "PUT"),
                Pair.of("/group/decline", "PUT"),
                Pair.of("/group/delete_team", "DELETE"),
                Pair.of("/topic/register_topic", "POST"),
                Pair.of("/topic/update_topic", "PUT"),
                Pair.of("/topic/get_topic", "GET")
                );

        for(Pair<String, String> bypassToken : bypassTokens) {
//            System.out.println("Request:");
//            System.out.println(request.getServletPath());
//            System.out.println(request.getMethod());
            if(request.getServletPath().contains(bypassToken.getFirst())
                    && request.getMethod().equals(bypassToken.getSecond()))
                return true;
        }
        return false;
    }
}
