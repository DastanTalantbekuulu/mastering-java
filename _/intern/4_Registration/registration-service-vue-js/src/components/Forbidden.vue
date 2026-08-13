<template>
  <div class="forbidden-container">
    <h1>🚫 Доступ запрещен (403)</h1>
    <p>У вас нет прав для просмотра этой страницы.</p>
    <button @click="goHome">На главную</button>
  </div>
</template>

<script>
import { useRouter } from "vue-router";

export default {
  name: "Forbidden",
  setup() {
    const router = useRouter();

    const goHome = () => {
      router.push("/"); // Перенаправление на главную
    };

    return { goHome };
  },
};
</script>

<style scoped>
.forbidden-container {
  text-align: center;
  margin-top: 50px;
}

h1 {
  color: red;
}

button {
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  border: none;
  cursor: pointer;
  font-size: 16px;
  border-radius: 5px;
}

button:hover {
  background-color: #0056b3;
}
</style>
