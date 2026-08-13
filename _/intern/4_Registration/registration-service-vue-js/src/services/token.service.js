import { jwtDecode } from "jwt-decode";
import AuthService from "@/services/AuthService";

class TokenService {
  getLocalRefreshToken() {
    const user = JSON.parse(localStorage.getItem("user"));
    if (!user || !user.refreshToken) return null;

    if (this.isTokenExpired(user.refreshToken)) {
      console.warn("[TokenService] Refresh-токен истек, удаляем пользователя.");
      this.removeUser();
      AuthService.logout();
      window.location.href = "/";
      return null;
    }
    return user.refreshToken;
  }

  getLocalAccessToken() {
    const user = JSON.parse(localStorage.getItem("user"));
    if (!user || !user.accessToken) return null;

    if (this.isTokenExpired(user.accessToken)) {
      return null;
    }
    return user.accessToken;
  }


  updateLocalAccessToken(token) {
    let user = JSON.parse(localStorage.getItem("user"))
    if (!user){
      console.warn("[TokenService] Пользователь не найден в localStorage!");
      return;
    }
    user.accessToken = token;
    localStorage.setItem("user", JSON.stringify(user));
  }

  updateLocalRefreshToken(token) {
    let user = JSON.parse(localStorage.getItem("user"));
    if (!user) {
      console.warn("[TokenService] Пользователь не найден в localStorage!");
      return;
    }
    user.refreshToken = token;
    localStorage.setItem("user", JSON.stringify(user));
  }

  getUser() {
    return JSON.parse(localStorage.getItem("user"));
  }

  setUser(user) {
    localStorage.setItem("user", JSON.stringify(user));
  }

  removeUser() {
    localStorage.removeItem("user");
  }

  getRolesFromToken() {
    const user = JSON.parse(localStorage.getItem("user"));
    if (user && user.accessToken) {
      const decodedToken = jwtDecode(user.accessToken);
      return decodedToken.roles || [];
    }
    return [];
  }

  isTokenExpired(token) {
    try {
      const decodedToken = jwtDecode(token);
      if (!decodedToken.exp) return false;

      const currentTime = Date.now() / 1000;
      return decodedToken.exp < currentTime;
    } catch (error) {
      console.error("[TokenService] Ошибка при декодировании токена:", error);
      return true;
    }
  }
}

export default new TokenService();