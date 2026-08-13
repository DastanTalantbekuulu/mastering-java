import axiosInstance from "../http-common";
import TokenService from "./token.service";
import AuthService from "@/services/AuthService";
import EventBus from "@/common/EventBus";

let isRefreshing = false;
let refreshSubscribers = [];

const subscribeTokenRefresh = (cb) => {
    refreshSubscribers.push(cb);
};

const onRefreshed = (token) => {
    refreshSubscribers.forEach((cb) => cb(token));
    refreshSubscribers = [];
};

const setup = () => {
    axiosInstance.interceptors.request.use(
        (config) => {
            let accessToken = TokenService.getLocalAccessToken();
            if (accessToken) {
                config.headers["Authorization"] = "Bearer " + accessToken;
            }
            return config;
        },
        (error) => Promise.reject(error)
    );

    axiosInstance.interceptors.response.use(
        (response) => response,
        async (error) => {
            const originalRequest = error.config;

            if (originalRequest.url !== "/login" && error.response) {
                if (error.response.status === 401 && !originalRequest._retry) {
                    originalRequest._retry = true;

                    const refreshToken = TokenService.getLocalRefreshToken();

                    if (refreshToken) {
                        if (isRefreshing) {
                            return new Promise((resolve) => {
                                subscribeTokenRefresh((token) => {
                                    originalRequest.headers["Authorization"] = "Bearer " + token;
                                    resolve(axiosInstance(originalRequest));
                                });
                            });
                        }

                        isRefreshing = true;

                        try {
                            const response = await AuthService.refreshToken();
                            const { accessToken } = response;

                            TokenService.updateLocalAccessToken(accessToken);
                            isRefreshing = false;
                            onRefreshed(accessToken);

                            originalRequest.headers["Authorization"] = "Bearer " + accessToken;
                            return axiosInstance(originalRequest);
                        } catch (_error) {
                            console.error("Ошибка при обновлении токена:", _error);
                            isRefreshing = false;
                            TokenService.removeUser();
                            AuthService.logout();
                            EventBus.dispatch("logout");
                            window.location.href = "/";
                            return Promise.reject(_error);
                        }
                    } else {
                        console.warn("Refresh-токен отсутствует, разлогиниваем пользователя.");
                        TokenService.removeUser();
                        AuthService.logout();
                        EventBus.dispatch("logout");
                        window.location.href = "/";
                        return Promise.reject(error);
                    }
                }
            }

            return Promise.reject(error);
        }
    );
};

export default setup;
