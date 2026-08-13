<template>
  <div>
    <button class="btn btn-secondary" @click="goBack">
      Назад к списку
    </button>
    <h2>Детали пользователя</h2>

    <div v-if="user" class="user-details-container">
      <!-- Блок с данными пользователя слева -->
      <div class="user-info">
        <div><strong>ID:</strong> {{ user.id }}</div>
        <div><strong>Имя:</strong> {{ user.firstName }}</div>
        <div><strong>Отчество:</strong> {{ user.middleName }}</div>
        <div><strong>Фамилия:</strong> {{ user.lastName }}</div>
        <div><strong>Пол:</strong> {{ user.personGender || "Не указано" }}</div>
        <div><strong>Национальность:</strong> {{ user.nationality || "Не указано" }}</div>
        <div><strong>Дата рождения:</strong> {{ user.dateOfBirth || "Не доступно" }}</div>
        <div><strong>Идентификационный номер:</strong> {{ user.identificationNumber || "Не доступно" }}</div>
        <div><strong>ID документа:</strong> {{ user.documentId }}</div>
        <div><strong>Выдавший орган:</strong> {{ user.issuingAuthority || "Не доступно" }}</div>
        <div><strong>Статус:</strong> {{ user.status || "Не доступно" }}</div>
      </div>

      <!-- Блок с изображениями справа -->
      <div class="user-images">
        <div class="image-grid">
          <div v-if="faceImage" class="user-image-container">
            <strong>Фото человека с паспортом:</strong>
            <div class="image-wrapper">
              <img :src="faceImage" alt="Face Image" class="user-image" />
            </div>
          </div>
          <div v-if="frontImage" class="user-image-container">
            <strong>Передняя часть паспорта:</strong>
            <div class="image-wrapper">
              <img :src="frontImage" alt="Front Image" class="user-image" />
            </div>
          </div>
        </div>
        <div v-if="backImage" class="user-image-container">
          <strong>Задняя часть паспорта:</strong>
          <div class="image-wrapper">
            <img :src="backImage" alt="Back Image" class="user-image" />
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <p>Загрузка деталей пользователя...</p>
    </div>
  </div>
</template>

<script>
import UserDataService from "../services/UserDataService";

export default {
  name: "ViewUser",
  data() {
    return {
      user: null,
      faceImage: null,
      backImage: null,
      frontImage: null,
    };
  },
  methods: {
    fetchUserDetails() {
      const userId = this.$route.params.id;
      UserDataService.getUserById(userId)
        .then((response) => {
          this.user = response.data;
          this.loadImages();
        })
        .catch((e) => {
          console.error("Error fetching user details:", e);
        });
    },

    loadImages() {
      if (this.user.images && this.user.images.length) {
        const clientType = this.getClientType(this.user.role);
        this.user.images.forEach((image) => {
          this.loadImage(clientType, image);
        });
      }
    },

    loadImage(clientType, image) {
      if (!clientType) {
        console.error("Invalid clientType for role:", this.user.role);
        return;
      }

      UserDataService.getImage(clientType, image.imageType.toLowerCase(), image.fileName)
        .then((response) => {
          const blob = new Blob([response.data], { type: response.headers["content-type"] });
          const imageUrl = URL.createObjectURL(blob);

          if (image.imageType === "FACE") {
            this.faceImage = imageUrl;
          } else if (image.imageType === "BACK") {
            this.backImage = imageUrl;
          } else if (image.imageType === "FRONT") {
            this.frontImage = imageUrl;
          }
        })
        .catch((e) => {
          console.error("Error loading image:", e);
        });
    },

    getClientType(role) {
      const staffRoles = ["ADMIN", "MANAGER", "REGISTRAR"];
      return staffRoles.includes(role) ? "staff" : "client";
    },
    goBack() {
      this.$router.go(-1); // Возвращает на предыдущую страницу
    },

  },

  mounted() {
    this.fetchUserDetails();
  },
};
</script>

<style scoped>
.user-details-container {
  display: flex;
  align-items: flex-start;
  gap: 20px;
}

.user-info {
  flex: 1;
}

.user-images {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.image-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr); /* Две колонки */
  gap: 20px; /* Расстояние между колонками */
}

.user-image-container {
  margin-bottom: 20px;
}

.image-wrapper {
  position: relative;
  width: 100%; /* Занимает всю ширину контейнера */
  padding-top: 56.25%; /* Соотношение сторон 16:9 */
  overflow: hidden;
  background-color: #f0f0f0; /* Фон для визуализации пустого пространства */
}

.user-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: contain; /* Изображение полностью вписывается в блок */
}
</style>