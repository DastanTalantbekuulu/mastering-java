<template>
  <div id="app">
    <nav class="navbar navbar-expand navbar-dark bg-dark custom-navbar">
      <router-link to="/" class="navbar-brand">Регистратор</router-link>
      <div class="navbar-nav mr-auto">
        <li class="nav-item" v-if="isAdmin || isManager">
          <router-link to="/users" class="nav-link">Клиенты</router-link>
        </li>
        <li class="nav-item" v-if="isAdmin || isManager">
          <router-link to="/workers" class="nav-link">Сотрудники</router-link>
        </li>
        <li class="nav-item" v-if="isAdmin || isManager || isRegistrar">
          <router-link to="/register" class="nav-link">Регистрация</router-link>
        </li>
        <li class="nav-item" v-if="isAdmin || isManager">
          <router-link to="/directories" class="nav-link">Справочники</router-link>
        </li>
        <li class="nav-item" v-if="isAdmin || isManager">
          <router-link to="/log" class="nav-link">Журнал</router-link>
        </li>
        <li class="nav-item" v-if="isAdmin || isManager">
          <router-link to="/waiting-list" class="nav-link">Лист ожидания</router-link>
        </li>
      </div>
        <div class="navbar-nav ml-auto">
          <li class="nav-item">
            <router-link v-if="!isLoggedIn" to="/login" class="nav-link">Вход</router-link>
            <router-link v-else to="/logout" class="nav-link">Выход</router-link>
          </li>
        </div>
    </nav>

    <div class="container mt-3">
      <router-view />
    </div>
  </div>
</template>

<script>
import TokenService from "./services/token.service";

export default {
  name: "App",
  data() {
    return {
      roles: [],
      isLoggedIn: false,
    };
  },
  computed: {
    isAdmin() {
      return this.roles.includes("ROLE_ADMIN");
    },
    isRegistrar() {
      return this.roles.includes("ROLE_REGISTRAR");
    },
    isManager() {
      return this.roles.includes("ROLE_MANAGER");
    },
  },
  methods: {
    logOut() {
      this.$router.push('/');
    },
    decodeToken() {
      this.roles = TokenService.getRolesFromToken();
      console.log("Updated roles:", this.roles);
    },
    checkLoginStatus() {
      const token = TokenService.getLocalAccessToken();
      this.isLoggedIn = !!token;
    },
  },
  watch: {
    "$route"() {
      console.log("Route changed, updating roles...");
      this.checkLoginStatus();
      this.decodeToken(); // Update roles whenever the route changes
    },
  },
  mounted() {
    console.log("mounted called");
    this.checkLoginStatus();
    this.decodeToken(); // Decode token when the component mounts
  },
};
</script>

<style scoped>
.custom-navbar {
  padding: 15px;
  background-color: #000000 !important;
  border-bottom: 3px solid #ec0f7f;
}

.navbar-brand {
  font-size: 20px;
  font-weight: bold;
  color: #fff !important;
}

.navbar-nav .nav-link {
  display: block;
  padding: 10px 15px;
  margin: 0;
  color: #ddd !important;
  text-align: center;
  line-height: 1.5;
  height: 40px;
  transition: all 0.3s ease;
}

.navbar-nav .nav-link:hover {
  color: #fff !important;
  background-color: #ec0f7f;
  border-radius: 5px;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}

nav {
  transition: all 0.3s ease-in-out;
}
</style>
