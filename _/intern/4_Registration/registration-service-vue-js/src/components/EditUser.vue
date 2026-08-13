<template>
  <div>
    <h2>Редактировать пользователя</h2>
    <form @submit.prevent="updateUser">
      <div class="form-group">
        <label for="firstName">Имя</label>
        <input
          type="text"
          id="firstName"
          class="form-control"
          v-model="user.firstName"
        />
      </div>
      <div class="form-group">
        <label for="lastName">Фамилия</label>
        <input
          type="text"
          id="lastName"
          class="form-control"
          v-model="user.lastName"
        />
      </div>
      <div class="form-group">
        <label for="middleName">Отчество</label>
        <input
          type="text"
          id="middleName"
          class="form-control"
          v-model="user.middleName"
        />
      </div>
      <div class="form-group">
        <label for="personGender">Пол</label>
        <input
          type="text"
          id="personGender"
          class="form-control"
          v-model="user.personGender"
        />
      </div>
      <div class="form-group">
        <label for="nationality">Национальность</label>
        <input
          type="text"
          id="nationality"
          class="form-control"
          v-model="user.nationality"
        />
      </div>
      <div class="form-group">
        <label for="dateOfBirth">Дата рождения</label>
        <input
          type="date"
          id="dateOfBirth"
          class="form-control"
          v-model="user.dateOfBirth"
        />
      </div>
      <div class="form-group">
        <label for="identificationNumber">Номер удостоверения</label>
        <input
          type="text"
          id="identificationNumber"
          class="form-control"
          v-model="user.identificationNumber"
        />
      </div>
      <div class="form-group">
        <label for="dateOfIssue">Дата выдачи</label>
        <input
          type="date"
          id="dateOfIssue"
          class="form-control"
          v-model="user.dateOfIssue"
        />
      </div>
      <div class="form-group">
        <label for="dateOfExpiry">Дата окончания</label>
        <input
          type="date"
          id="dateOfExpiry"
          class="form-control"
          v-model="user.dateOfExpiry"
        />
      </div>
      <div class="form-group">
        <label for="email">Электронная почта</label>
        <input
          type="email"
          id="email"
          class="form-control"
          v-model="user.email"
        />
      </div>
      <!-- Роль -->
      <div class="form-group">
        <label for="role">Роль</label>
        <select id="role" class="form-control" v-model="user.role">
          <option v-for="role in roles" :key="role" :value="role">{{ role }}</option>
        </select>
      </div>
      <!-- Статус -->
      <div class="form-group">
        <label for="status">Статус</label>
        <select id="status" class="form-control" v-model="user.status">
          <option v-for="status in statuses" :key="status" :value="status">{{ status }}</option>
        </select>
      </div>
      <div class="form-group">
        <label for="documentId">ID документа</label>
        <input
          type="text"
          id="documentId"
          class="form-control"
          v-model="user.documentId"
        />
      </div>
      <div class="form-group">
        <label for="issuingAuthority">Орган, выдавший документ</label>
        <input
          type="text"
          id="issuingAuthority"
          class="form-control"
          v-model="user.issuingAuthority"
        />
      </div>
      <div class="form-group">
        <label for="regionId">Регион</label>
        <select
            id="regionId"
            class="form-control"
            v-model="user.regionId"
        >
          <option
              v-for="region in regions"
              :key="region.id"
              :value="region.id"
          >
            {{ region.name }}
          </option>
        </select>
      </div>
      <div class="form-group">
        <label for="districtId">Район</label>
        <select
            id="districtId"
            class="form-control"
            v-model="user.districtId"
        >
          <option
              v-for="district in districts"
              :key="district.id"
              :value="district.id"
          >
            {{ district.name }}
          </option>
        </select>
      </div>
      <div class="form-group">
        <label for="cityId">Город</label>
        <select
            id="cityId"
            class="form-control"
            v-model="user.cityId"
        >
          <option
              v-for="city in cities"
              :key="city.id"
              :value="city.id"
          >
            {{ city.name }}
          </option>
        </select>
      </div>
      <div class="form-group">
        <label for="streetId">Улица</label>
        <select
            id="streetId"
            class="form-control"
            v-model="user.streetId"
        >
          <option
              v-for="street in streets"
              :key="street.id"
              :value="street.id"
          >
            {{ street.name }}
          </option>
        </select>
      </div>
      <div class="form-group">
        <label for="houseNumber">Номер дома</label>
        <input
          type="number"
          id="houseNumber"
          class="form-control"
          v-model="user.house"
        />
      </div>
      <div class="form-group">
        <label for="apartmentNumber">Номер квартиры</label>
        <input
          type="number"
          id="apartmentNumber"
          class="form-control"
          v-model="user.apartment"
        />
      </div>

      <!-- Поля для загрузки изображений -->
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

      <button type="submit" class="btn btn-primary" style="margin-right: 10px;">Сохранить</button>

    </form>
  </div>
</template>

<script>
import UserDataService from "../services/UserDataService";
import DirectoryDataService from "@/services/DirectoryDataService";

export default {
  name: "EditUser",
  data() {
    return {
      user: {},
      statuses: [],
      roles: [],
      regions: [],
      districts: [],
      cities: [],
      streets: [],
      faceImage: null,
      backImage: null,
      frontImage: null,
    };
  },
  methods: {
    fetchUser() {
      const userId = this.$route.params.id;
      UserDataService.getUserById(userId)
          .then((response) => {
            this.user = response.data;
          })
          .catch((e) => {
            console.error("Error fetching user:", e);
          });
    },
    fetchStatusesAndRoles() {
      UserDataService.getStatuses()
          .then((response) => {
            this.statuses = response.data;
          })
          .catch((e) => {
            console.error("Error fetching statuses:", e);
          });
      UserDataService.getRoles()
          .then((response) => {
            this.roles = response.data;
          })
          .catch((e) => {
            console.error("Error fetching roles:", e);
          });
    },
    fetchLocationData() {
      DirectoryDataService.getAllRegions()
          .then((response) => {
            this.regions = response.data;
            this.loadImages();
          })
          .catch((e) => {
            console.error("Error fetching regions:", e);
          });

      DirectoryDataService.getAllDistricts()
          .then((response) => {
            this.districts = response.data;
          })
          .catch((e) => {
            console.error("Error fetching districts:", e);
          });

      DirectoryDataService.getAllCities()
          .then((response) => {
            this.cities = response.data;
          })
          .catch((e) => {
            console.error("Error fetching cities:", e);
          });

      DirectoryDataService.getAllStreets()
          .then((response) => {
            this.streets = response.data;
          })
          .catch((e) => {
            console.error("Error fetching streets:", e);
          });
    },
    updateUser() {
      UserDataService.updateUser(this.$route.params.id, this.user)
          .then(() => {
            alert("User updated successfully!");
            this.$router.push("/users");
          })
          .catch((e) => {
            console.error("Error updating user:", e);
          });
    },
    onFileChange(event, type) {
      const file = event.target.files[0];
      if (type === "passportFace") this.passportFace = file;
      if (type === "passportBack") this.passportBack = file;
      if (type === "passportFront") this.passportFront = file;
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
            const blob = new Blob([response.data], {type: response.headers["content-type"]});
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
  },
  mounted() {
    this.fetchUser();
    this.fetchStatusesAndRoles();
    this.fetchLocationData();
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
