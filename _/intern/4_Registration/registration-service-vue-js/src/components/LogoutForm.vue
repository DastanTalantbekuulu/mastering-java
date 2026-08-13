<template>
  <div class="logout-container">
    <div v-if="!isLoggedOut" class="message-container">
      <p class="thank-you-message">Мы рады, что работали с вами!</p>
      <p class="info-message">Нажмите кнопку "Выйти", чтобы завершить сессию.</p>
    </div>

    <button @click="logout" class="logout-button">Выйти</button>

    <p v-if="successMessage" class="success-message">{{ successMessage }}</p>
    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
  </div>
</template>

<script>
import AuthService from "../services/AuthService";

export default {
  data() {
    return {
      successMessage: "",
      errorMessage: "",
      isLoggedOut: false,
    };
  },

  methods: {
    async logout() {
      try {
        await AuthService.logout();
        this.successMessage = "Вы успешно вышли!";
        this.errorMessage = "";
        this.isLoggedOut = true;

        setTimeout(() => this.$router.push("/"), 1500);
      } catch (error) {
        this.successMessage = "";
        this.errorMessage = "Ошибка выхода. Попробуйте еще раз.";
        console.error("Ошибка выхода:", error);
      }
    },
  },
};
</script>

<style scoped>
.logout-container {
  text-align: center;
  margin-top: 50px;
  font-family: 'Arial', sans-serif;
}

.message-container {
  margin-bottom: 30px;
  color: #333;
}

.thank-you-message {
  font-size: 20px;
  color: #4caf50;
  font-weight: 600;
  margin-bottom: 5px;
}

.info-message {
  font-size: 16px;
  color: #777;
}

.logout-button {
  width: 200px;
  padding: 15px;
  margin: 20px 0;
  background-color: #ec0f7f;
  color: white;
  border: none;
  border-radius: 30px;
  font-size: 18px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.logout-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.logout-button:hover {
  background-color: #b80d64;
}

.success-message {
  font-size: 16px;
  color: green;
  margin-top: 20px;
}

.error-message {
  font-size: 16px;
  color: red;
  margin-top: 20px;
}
</style>
