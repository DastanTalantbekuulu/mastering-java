<template>
  <div class="auth-container">
    <h2>Вход</h2>
    <input
        v-model="username"
        @blur="usernameTouched = true"
        placeholder="Логин"
        class="auth-input"
    />
    <p v-if="usernameTouched && usernameError" class="error-message">{{ usernameError }}</p>

    <div class="password-container">
      <input
          v-model="password"
          @blur="passwordTouched = true"
          :type="showPassword ? 'text' : 'password'"
          placeholder="Пароль"
          class="auth-input"
      />
      <button type="button" class="eye-button" @click="togglePasswordVisibility">
        {{ showPassword ? "🔓" : "🔒" }}
      </button>
    </div>

    <p v-if="passwordTouched && passwordError" class="error-message">{{ passwordError }}</p>


    <button @click="login" class="auth-button">
      Войти
    </button>

    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
    <p v-if="successMessage" class="success-message">{{ successMessage }}</p>

  </div>
</template>

<script>
export default {
  data() {
    return {
      username: "",
      password: "",
      errorMessage: "",
      successMessage: "",
      usernameTouched: false,
      passwordTouched: false,
      showPassword: false,
    };
  },
  computed: {
    usernameError() {
      if (!this.username) {
        return "Логин обязателен для заполнения";
      }
      if (this.username.length < 4 || this.username.length > 20) {
        return "Логин должен содержать от 4 до 20 символов";
      }
      if (!/^[a-zA-Zа-яА-Я0-9]+$/.test(this.username)) {
        return "Логин может содержать только буквы и цифры";
      }
      return "";
    },
    passwordError() {
      if (!this.password) {
        return "Пароль обязателен для заполнения";
      }
      if (this.password.length < 2 || this.password.length > 12) {
        return "Пароль должен содержать от 2 до 12 символов";
      }
      // if (!/[a-zA-Z]/.test(this.password)) {
      //   return "Пароль должен содержать хотя бы одну букву";
      // }
      if (!/\d/.test(this.password)) {
        return "Пароль должен содержать хотя бы одну цифру";
      }
      return "";
    },
  },
  methods: {
    async login() {
      this.usernameTouched = true;
      this.passwordTouched = true;

      this.$store.dispatch("auth/login", { username: this.username, password: this.password }).then(
          () => {
            this.$router.push("/");
          },
          (error) => {
            if (error.response && error.response.status === 401) {
              this.errorMessage = "Неверный логин или пароль";
            } else if (error.response.status === 403) {
              this.errorMessage = "Введите логин и пароль";
            }else {
              this.errorMessage = "Ошибка сервера";
            }
          }
      );
    },
    togglePasswordVisibility() {
      this.showPassword = !this.showPassword;
    },
  },
};
</script>

<style scoped>
.auth-container {
  width: 300px;
  margin: 100px auto;
  padding: 20px;
  text-align: center;
  background: #f7f7f7;
  border-radius: 10px;
  box-shadow: 0 14px 10px rgba(0, 0, 0, 0.1);
}

.auth-container h2 {
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}

.auth-input {
  width: calc(100% - 20px);
  padding: 10px;
  margin: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
  box-sizing: border-box;
}
.password-container {
  position: relative;
  width: 100%;
  display: flex;
  align-items: center;
}

.eye-button {
  position: absolute;
  right: 10px;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 18px;
  outline: none;
}


.auth-button {
  width: calc(100% - 20px);
  padding: 10px;
  margin: 10px 0;
  background: #ec0f7f;
  color: #fff;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s;
}

.auth-button:hover {
  background: #b80d64;
}

.auth-button:disabled {
  background: #ccc;
  cursor: not-allowed;
}

.error-message {
  color: red;
  margin-top: 10px;
  font-size: 14px;
}

.success-message {
  color: green;
  margin-top: 10px;
  font-size: 14px;
}

.register-link {
  margin-top: 15px;
  font-size: 14px;
}

.register-link a {
  color: #ec0f7f;
  text-decoration: none;
}

.register-link a:hover {
  text-decoration: underline;
}
</style>
