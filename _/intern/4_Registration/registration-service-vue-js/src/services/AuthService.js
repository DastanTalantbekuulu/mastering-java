import http from "../http-common";
import TokenService from "../services/token.service";

class AuthService {

    login({ username, password }) {
        return http
            .post("/login", null, {params: {
                    username,
                    password
                }}
            )
            .then((response) => {
                if (response.data.accessToken) {
                    TokenService.setUser(response.data);
                }
                return response.data;
            });
    }
    logout() {
        TokenService.removeUser();
        http.post("/logout")
            .then(() => {
                TokenService.removeUser();
            });
    }
    async refreshToken() {
        try {
            const refreshToken = TokenService.getLocalRefreshToken();
            if (!refreshToken) {
                console.warn("[AuthService] Нет refreshToken, разлогиниваем...");
                TokenService.removeUser();
            }

            const response = await http.post("/refresh", {},{
                headers: {
                    Authorization: `Bearer ${refreshToken}`
                }
            });
            TokenService.updateLocalAccessToken(response.data.accessToken);
            TokenService.updateLocalRefreshToken(response.data.refreshToken);

            return response.data;
        } catch (error) {
            console.error("[AuthService] Ошибка при обновлении токена:", error);
            TokenService.removeUser();
            throw error;
        }
    }

}

export default new AuthService();
