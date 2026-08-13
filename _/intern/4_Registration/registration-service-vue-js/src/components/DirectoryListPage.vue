<template>
  <div class="directory-list">
    <h1>Справочники</h1>
    <div class="card-container">
      <div class="card" v-for="item in directories" :key="item.path">
        <router-link :to="item.path" class="card-link">
          <h2>{{ item.name }}</h2>
          <p>{{ item.description }}</p>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import TokenService from "../services/token.service";

export default {
  name: "DirectoryListPage",
  data() {
    return {
      directories: [
        { path: "/directory/cities", name: "Населенные пункты", description: "Управление списком городов и сел." },
        { path: "/directory/regions", name: "Области", description: "Управление списком областей." },
        { path: "/directory/districts", name: "Районы", description: "Управление списком районов." },
        { path: "/directory/city-types", name: "Типы населенных пунктов", description: "Управление типами городов и сел." },
        { path: "/directory/streets", name: "Улицы", description: "Управление списком улиц." },
      ],
    };
  },
  beforeRouteEnter(to, from, next) {
    const isAuthenticated = TokenService.getLocalAccessToken();
    if (!isAuthenticated) {
      next("/403");
    } else {
      next();
    }
  }
};
</script>

<style scoped>
.directory-list {
  padding: 20px;
  font-family: Arial, sans-serif;
  //background-color: #f9f9f9;
  min-height: 100vh;
}

h1 {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

.card-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  padding: 20px;
}

.card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  transition: transform 0.2s, box-shadow 0.2s;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.card-link {
  text-decoration: none;
  color: #333;
}

.card-link h2 {
  margin: 0 0 10px;
  font-size: 18px;
  color: #007BFF;
  transition: color 0.2s;
}

.card-link h2:hover {
  color: #0056b3;
}

.card-link p {
  margin: 0;
  font-size: 14px;
  color: #555;
}
</style>
