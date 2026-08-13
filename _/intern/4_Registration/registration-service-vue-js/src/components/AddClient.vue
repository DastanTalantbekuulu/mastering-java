<template>
  <div class="submit-form">
    <h1>Регистрация</h1>
    <div class="form-group">
      <label for="last_name">Фамилия</label>
      <input
          type="text"
          class="form-control"
          id="last_name"
          v-model="form.lastName"
          name="last_name"
          placeholder="Введите фамилию"
          required
          @input="validateTextField('lastName', $event)"
      />
      <span v-if="errors.lastName" style="color: red" class="error-message">{{ errors.lastName }}</span>
    </div>

    <div class="form-group">
      <label for="first_name">Имя</label>
      <input
          type="text"
          class="form-control"
          id="first_name"
          v-model="form.firstName"
          name="first_name"
          placeholder="Введите имя"
          required
          @input="validateTextField('firstName', $event)"
      />
      <span v-if="errors.firstName" style="color: red" class="error-message">{{ errors.firstName }}</span>
    </div>

    <div class="form-group">
      <label for="middle_name">Отчество</label>
      <input
          type="text"
          class="form-control"
          id="middle_name"
          v-model="form.middleName"
          name="middle_name"
          placeholder="Введите отчество"
          required
          @input="validateTextField('middleName', $event)"
      />
      <span v-if="errors.middleName" style="color: red" class="error-massage">{{ errors.middleName }}</span>
    </div>


    <div class="form-group">
      <label for="person_gender">Пол</label>
      <select
          class="form-control"
          id="person_gender"
          v-model="form.personGender"
          name="person_gender"
      >
        <option value="" disabled selected hidden>Выберите пол</option>
        <option value="Male">Мужской</option>
        <option value="Female">Женский</option>
      </select>
    </div>

    <div class="form-group">
      <label for="nationality">Национальность</label>
      <select
          class="form-control"
          id="nationality"
          v-model="form.nationality"
          @change="clearIdentificationNumber"
          name="nationality"
      >
        <option value="" disabled selected hidden>Выберите национальность</option>
        <option value="Kyrgyz">КР</option>
        <option value="Foreigner">Другое</option>
      </select>
    </div>

    <div v-if="form.nationality === 'Kyrgyz'" class="form-group">
      <label for="identification_number">ИНН</label>
      <input
          type="text"
          class="form-control"
          id="identification_number"
          v-model="form.identificationNumber"
          @input="validateIdentificationNumber"
          name="identification_number"
          maxlength="14"
          placeholder="Введите 14-значное число"
          pattern="\d*"
      />
      <span v-if="!isIdentificationNumberValid" style="color: red;">
            Должно быть ровно 14 цифр
          </span>
    </div>

    <div class="form-group">
      <label for="date_of_birth">Дата рождения</label>
      <input
          type="date"
          class="form-control datepicker"
          id="date_of_birth"
          v-model="form.dateOfBirth"
          name="date_of_birth"
          :max="currentDate"
          required
      />
    </div>

    <div class="form-group">
      <label for="date_of_issue">Дата выдачи</label>
      <input
          type="date"
          class="form-control datepicker"
          id="date_of_issue"
          v-model="form.dateOfIssue"
          name="date_of_issue"
          :max="currentDate"
          required
      />
    </div>

    <div class="form-group">
      <label for="date_of_expiry">Дата истечения срока действия</label>
      <input
          type="date"
          class="form-control datepicker"
          id="date_of_expiry"
          v-model="form.dateOfExpiry"
          name="date_of_expiry"
          :min="form.dateOfIssue"
          required
      />
    </div>

    <div class="form-group">
      <label for="document_id">Номер документа</label>
      <div class="input-group">
        <span v-if="form.nationality === 'Kyrgyz'" class="input-group-text">ID</span>
        <input
            type="text"
            class="form-control"
            id="document_id"
            v-model="form.documentId"
            :placeholder="form.nationality === 'Kyrgyz' ? 'Введите 7 цифр' : 'Введите идентификатор документа'"
            name="document_id"
            @input="validateDocumentId"
            v-if="form.nationality === 'Kyrgyz'"
        />
        <input
            v-else
            type="text"
            class="form-control"
            id="document_id"
            v-model="form.documentId"
            placeholder="Введите идентификатор документа"
        />
      </div>
      <span v-if="form.nationality === 'Kyrgyz' && !isDocumentIdValid" style="color: red;">Введите 7 цифр</span>
    </div>

    <div class="form-group">
      <label for="issuing_authority">Орган выдачи</label>
      <div class="input-group">
        <span v-if="form.nationality === 'Kyrgyz'" class="input-group-text">MKK</span>
        <input
            type="text"
            class="form-control"
            id="issuing_authority"
            v-model="form.issuingAuthority"
            :placeholder="form.nationality === 'Kyrgyz' ? 'Введите от 6 до 8 цифр (MKK + номер)' : 'Введите орган выдачи'"
            name="issuing_authority"
            @input="validateIssuingAuthority"
        />
      </div>
      <span v-if="form.nationality === 'Kyrgyz' && !isIssuingAuthorityValid"
            style="color: red;">Введите от 6 до 8 цифр</span>
    </div>


    <div class="form-group">
      <label for="role">Роль</label>
      <select
          id="role"
          class="form-control"
          v-model="form.role"
          name="role"
          required
      >
        <option value="" disabled selected hidden>Выберите роль</option>
        <option value="CLIENT">Клиент</option>
        <option value="MANAGER">Менеджер</option>
        <option value="REGISTRAR">Регистратор</option>
        <option value="ADMIN">Админ</option>
      </select>
    </div>

    <div class="form-group" v-if="form.role !== 'CLIENT'">
      <label for="email">Email</label>
      <input
          type="email"
          class="form-control"
          id="email"
          v-model="form.email"
          name="email"
          placeholder="Введите почту"
          required
          @blur="validateEmail"
      />
      <span v-if="!isEmailValid" style="color: red;">Email должен быть с доменом @gmail.com</span>
    </div>

    <div class="form-group">
      <label for="region">Регион</label>
      <select
          class="form-control"
          id="region"
          v-model="form.regionId"
          @change="loadDistricts"
          name="region"
      >
        <option value="" disabled selected>Выберите область (или оставьте пустым)</option>
        <option value="null">Нет области</option>
        <option v-for="region in regions" :value="region.id" :key="region.id">
          {{ region.name }}
        </option>
      </select>
    </div>

    <div class="form-group">
      <label for="district">Район</label>
      <select
          class="form-control"
          id="district"
          v-model="form.districtId"
          @change="loadCities"
          name="district"
      >
        <option value="" disabled selected>Выберите район (или оставьте пустым)</option>
        <option value="null">Нет района</option>
        <option v-for="district in filteredDistricts" :value="district.id" :key="district.id">
          {{ district.name }}
        </option>
      </select>
    </div>

    <div class="form-group">
      <label for="city">Город</label>
      <div class="dropdown-container">
        <input
            type="text"
            class="form-control dropdown-input"
            id="city"
            v-model="citySearch"
            @input="filterCities"
            @focus="showCitySuggestions = true"
            @blur="hideSuggestionsWithDelay"
            placeholder="Поиск города..."
        />
        <ul v-if="showCitySuggestions && filteredCities.length" class="dropdown-list">
          <li
              v-for="city in filteredCities"
              :key="city.id"
              @mousedown.prevent="selectCity(city)"
          >
            {{ city.name }}
          </li>
        </ul>
      </div>
    </div>


    <div class="form-group">
      <label for="street">Улица</label>
      <div class="dropdown-container">
        <input
            type="text"
            class="form-control dropdown-input"
            id="street"
            v-model="streetSearch"
            @input="filterStreets"
            @focus="showStreetSuggestions = true"
            @blur="hideStreetSuggestionsWithDelay"
            placeholder="Поиск улицы..."
        />
        <ul v-if="showStreetSuggestions && filteredStreets.length" class="dropdown-list">
          <li
              v-for="street in filteredStreets"
              :key="street.id"
              @mousedown.prevent="selectStreet(street)"
          >
            {{ street.name }}
          </li>
        </ul>
      </div>
    </div>


    <div class="form-group">
      <label for="house_number">Номер дома</label>
      <input type="text" class="form-control"
             id="house_number" v-model="form.house"
             name="house_number" placeholder="Введите номер дома"
             @input="validateNumericField('house', $event)"
      />
    </div>

    <div class="form-group">
      <label for="apartment_number">Номер квартиры</label>
      <input type="text" class="form-control" id="apartment_number" v-model="form.apartment"
             name="apartment_number" placeholder="Введите номер квартиры"
             @input="validateNumericField('apartment', $event)"
      />
    </div>

    <div class="form-group">
      <label>Лицевая сторона паспорта:</label>
      <img v-if="imagePreviews.idCardFront"
           :src="imagePreviews.idCardFront"
           class="image-preview"
           @click="openFullImage('idCardFront')"
           alt="Front Side Preview">
      <input type="file" id="id_card_front" name="id_card_front" accept="image/*" ref="idCardFront"
             @change="handleFileChange('idCardFront')">
    </div>

    <div class="form-group">
      <label>Задняя сторона паспорта:</label>
      <img v-if="imagePreviews.idCardBack"
           :src="imagePreviews.idCardBack"
           class="image-preview"
           @click="openFullImage('idCardBack')"
           alt="Back Side Preview">
      <input type="file" id="id_card_back" name="id_card_back" accept="image/*" ref="idCardBack"
             @change="handleFileChange('idCardBack')">
    </div>

    <div class="form-group">
      <label>Удостоверение личности и лицо:</label>
      <img v-if="imagePreviews.idCardFace"
           :src="imagePreviews.idCardFace"
           class="image-preview"
           @click="openFullImage('idCardFace')"
           alt="ID Face Preview">
      <input type="file" id="id_card_face" name="id_card_face" accept="image/*" ref="idCardFace"
             @change="handleFileChange('idCardFace')">
    </div>

    <button @click="submitForm" class="btn btn-success" :disabled="!isFormValid">Сохранить</button>
    <button @click="resetForm" class="btn btn-reset">Очистить</button>
  </div>
</template>


<script>

import UserDataService from "../services/UserDataService";
import DirectoryDataService from "../services/DirectoryDataService";

export default {
  data() {
    return {
      imagePreviews: {
        idCardFront: null,
        idCardBack: null,
        idCardFace: null
      },
      fullImageUrls: {
        idCardFront: null,
        idCardBack: null,
        idCardFace: null
      },
      form: {
        lastName: "",
        firstName: "",
        middleName: "",
        personGender: "",
        nationality: "",
        dateOfBirth: "",
        identificationNumber: "",
        dateOfIssue: "",
        dateOfExpiry: "",
        documentId: "",
        issuingAuthority: "",
        role: "",
        regionId: null,
        districtId: null,
        cityId: null,
        streetId: null,
        house: "",
        apartment: "",
      },
      currentDate: "",
      isIssuingAuthorityValid: true,
      isEmailValid: true,
      errors: {
        lastName: "",
        firstName: "",
        middleName: ""
      },
      isFileValid: {
        idCardFront: true,
        idCardBack: true,
        idCardFace: true
      },
      isIdentificationNumberValid: true,
      isDocumentIdValid: true,
      regions: [],
      districts: [],
      filteredDistricts: [],
      cities: [],
      filteredCities: [],
      streets: [],
      filteredStreets: [],
      citySearch: "",
      showCitySuggestions: false,
      streetSearch: "",
      showStreetSuggestions: false,
    };
  },
  methods: {
    validateTextField(field, event) {
      let value = event.target.value;
      const regex = /^[a-zA-Zа-яА-ЯёЁ]*$/;

      this.form[field] = value.replace(/[^a-zA-Zа-яА-ЯёЁ]/g, "");

      if (value.length > 30) {
        value = value.slice(0, 30);
        this.errors[field] = "Поле не должно содержать больше 30 букв";
        this.form[field] = value;
      } else if (!regex.test(event.target.value)) {
        this.errors[field] = "Можно вводить только буквы!";
      } else if (!this.form[field]) {
        this.errors[field] = "Поле обязательно для заполнения";
      } else if (this.form[field].length < 2) {
        this.errors[field] = "Поле должно содержать минимум 2 буквы";
      } else {
        this.errors[field] = "";
      }
    },

    setDefaultDates() {
      const today = new Date();

      this.currentDate = today.toISOString().split('T')[0];

      const dateOfBirth = new Date();
      dateOfBirth.setFullYear(today.getFullYear() - 16);
      this.form.dateOfBirth = dateOfBirth.toISOString().split('T')[0];

      const dateOfIssue = new Date();
      dateOfIssue.setFullYear(today.getFullYear());
      this.form.dateOfIssue = dateOfIssue.toISOString().split('T')[0];

      const dateOfExpiry = new Date();
      dateOfExpiry.setFullYear(today.getFullYear() + 10);
      this.form.dateOfExpiry = dateOfExpiry.toISOString().split('T')[0];
    },

    validateIssuingAuthority() {
      const numericValue = this.form.issuingAuthority.replace(/\D/g, "");
      this.form.issuingAuthority = numericValue.slice(0, 8);
      this.isIssuingAuthorityValid = this.form.issuingAuthority.length >= 6 && this.form.issuingAuthority.length <= 8;
    },

    validateEmail() {
      const regex = /^[a-zA-Z0-9._%+-]+@gmail\.com$/;
      this.isEmailValid = regex.test(this.form.email);
    },

    validateNumericField(field, event) {
      this.form[field] = event.target.value.replace(/\D/g, "");
    },

    handleFileChange(fileRef) {
      this.validateFileType(fileRef);
      if (this.isFileValid[fileRef]) {
        this.previewImage(fileRef);
      }
    },
    previewImage(fileRef) {
      const fileInput = this.$refs[fileRef];
      if (fileInput.files.length > 0) {
        const file = fileInput.files[0];
        const maxWidth = 500;
        const maxHeight = 300;

        const img = new Image();
        img.onload = () => {
          let width = img.width;
          let height = img.height;

          this.fullImageUrls[fileRef] = URL.createObjectURL(file);

          if (width > maxWidth || height > maxHeight) {
            const aspectRatio = width / height;
            if (width > height) {
              width = maxWidth;
              height = Math.round(width / aspectRatio);
            } else {
              height = maxHeight;
              width = Math.round(height * aspectRatio);
            }
          }

          const canvas = document.createElement("canvas");
          canvas.width = width;
          canvas.height = height;
          const ctx = canvas.getContext("2d");
          ctx.drawImage(img, 0, 0, width, height);

          canvas.toBlob((blob) => {
            if (blob) {
              this.imagePreviews[fileRef] = URL.createObjectURL(blob);
            }
          }, file.type);
        };

        img.src = URL.createObjectURL(file);
      }
    },

    openFullImage(fileRef) {
      if (this.fullImageUrls[fileRef]) {
        window.open(this.fullImageUrls[fileRef], "_blank");
      } else {
        console.error("Full-size image not found.");
      }
    },

    validateFileType(fileRef) {
      const fileInput = this.$refs[fileRef];
      const file = fileInput.files[0];

      if (!fileInput) {
        console.error(`File input reference ${fileRef} is null or not found.`);
        return;
      }

      if (file) {
        const allowedTypes = ['image/jpeg', 'image/png', 'image/jpg'];

        if (!allowedTypes.includes(file.type)) {
          this.isFileValid[fileRef] = false;
          alert('Invalid file type. Please select a JPEG or PNG image.');
          document.getElementById(fileRef).value = '';
        } else {
          this.isFileValid[fileRef] = true;
        }
      }
    },
    submitForm() {
      const formData = new FormData();

      if (!this.isFileValid.idCardFront || !this.isFileValid.idCardBack || !this.isFileValid.idCardFace) {
        alert("Please upload valid image files.");
        return;
      }

      if (this.$refs.idCardFront.files.length) {
        formData.append("passport_front", this.$refs.idCardFront.files[0]);
      }

      if (this.$refs.idCardBack.files.length) {
        formData.append("passport_back", this.$refs.idCardBack.files[0]);
      }

      if (this.$refs.idCardFace.files.length) {
        formData.append("passport_face", this.$refs.idCardFace.files[0]);
      }

      if (this.form.nationality !== "Кыргыз") {
        this.form.identificationNumber = null;
      }

      const clientDataJson = JSON.stringify(this.form);
      formData.append("data_client", clientDataJson);

      console.log(formData);

      UserDataService.registerClient(formData)
          .then(() => {
            alert("Client added successfully!");
            this.$router.push("/users");
          })
          .catch((e) => {
            console.error("Couldn't create a new client:", e);
          });
    },

    clearIdentificationNumber() {
      this.form.identificationNumber = "";
      this.form.documentId = "";
    },

    validateIdentificationNumber() {
      const input = this.form.identificationNumber;

      const regex = /^\d*$/;
      if (!regex.test(input)) {
        this.form.identificationNumber = input.replace(/\D/g, "");
      }
      this.isIdentificationNumberValid = this.form.identificationNumber.length === 14;
    },

    validateDocumentId() {
      const numericPart = this.form.documentId.replace(/\D/g, "");

      this.form.documentId = numericPart.slice(0, 7);

      this.isDocumentIdValid = this.form.documentId.length === 7;
    },

    async fetchData() {
      this.regions = (await DirectoryDataService.getAllRegions()).data;

      this.districts = (await DirectoryDataService.getAllDistricts()).data;
      this.filteredDistricts = this.districts;
      this.cities = (await DirectoryDataService.getAllCities()).data;
      this.filteredCities = this.cities;
      this.streets = (await DirectoryDataService.getAllStreets()).data;
      this.filteredStreets = this.streets;
      this.setDefaultDates();
    },
    loadDistricts() {
      if (this.form.regionId === "null" || !this.form.regionId) {
        this.form.districtId = "null";
        this.filteredDistricts = this.districts;
        this.filteredCities = this.cities;
      } else {
        this.filteredDistricts = this.districts.filter(
            (district) => district.regionId === this.form.regionId
        );
        if (this.form.districtId && !this.filteredDistricts.some(d => d.id === this.form.districtId)) {
          this.form.districtId = null;
        }
        this.filteredCities = this.cities.filter(
            (city) => city.region?.id === this.form.regionId
        );
      }
      this.loadCities();
    },

    loadCities() {
      if (this.form.districtId === "null" || !this.form.districtId) {
        if (this.form.regionId === "null" || !this.form.regionId) {
          this.filteredCities = this.cities;
        } else {
          this.filteredCities = this.cities.filter(
              (city) => city.region?.id === this.form.regionId
          );
        }
      } else {
        this.filteredCities = this.cities.filter(
            (city) => city.district?.id === this.form.districtId
        );
      }
    },

    filterCities() {
      this.filteredCities = this.cities.filter(city =>
          city.name.toLowerCase().includes(this.citySearch.toLowerCase())
      );
      this.showCitySuggestions = !!this.filteredCities.length;
    },
    selectCity(city) {
      this.citySearch = city.name;
      this.form.cityId = city.id;
      this.showCitySuggestions = false;
      this.filteredStreets = this.streets.filter(
          (street) =>
              street.city.id === this.form.cityId
      );
    },
    hideSuggestionsWithDelay() {
      // Скрываем список предложений с задержкой, чтобы завершить клик
      setTimeout(() => {
        this.showCitySuggestions = false;
      }, 200);
    },
    filterStreets() {
      // Фильтруем список улиц по названию и городу
      this.filteredStreets = this.streets.filter(
          (street) =>
              street.name.toLowerCase().includes(this.streetSearch.toLowerCase()) &&
              street.city.id === this.form.cityId
      );
      this.showStreetSuggestions = !!this.filteredStreets.length;
    },
    selectStreet(street) {
      // Устанавливаем выбранную улицу
      this.streetSearch = street.name;
      this.form.streetId = street.id;
      this.showStreetSuggestions = false;
    },
    hideStreetSuggestionsWithDelay() {
      setTimeout(() => {
        this.showStreetSuggestions = false;
      }, 200);
    },

    resetForm() {
      this.imagePreviews = {};
      this.fullImageUrls = {};
      this.form = {
        firstName: "",
        lastName: "",
        middleName: "",
        personGender: "",
        nationality: "",
        dateOfBirth: "",
        identificationNumber: "",
        dateOfIssue: "",
        dateOfExpiry: "",
        documentId: "",
        issuingAuthority: "",
        regionId: null,
        districtId: null,
        cityId: null,
        streetId: null,
        house: "",
        apartment: "",
      };
      this.citySearch = "";
      this.streetSearch = "";

      this.$refs.idCardFront.value = null;
      this.$refs.idCardBack.value = null;
      this.$refs.idCardFace.value = null;
      this.isFileValid = {
        idCardFront: true,
        idCardBack: true,
        idCardFace: true
      };
    }
  },
  async mounted() {
    await this.fetchData();
  },

  computed: {
    isFormValid() {
      return (
          this.isIdentificationNumberValid &&
          this.isDocumentIdValid &&
          this.form.firstName &&
          this.form.lastName &&
          this.form.middleName &&
          this.form.personGender &&
          this.form.nationality &&
          this.form.dateOfBirth &&
          this.form.dateOfIssue &&
          this.form.dateOfExpiry &&
          this.form.issuingAuthority
      );
    },
  },

};
</script>

<style scoped>
.submit-form {
  padding: 20px;
  border-radius: 12px;
  border-color: #ccc;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

h1 {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.dropdown-container {
  position: relative;
}

.dropdown-input {
  width: 100%;
  box-sizing: border-box;
}

.dropdown-list {
  position: absolute;
  top: 100%;
  left: 0;
  width: 100%;
  background: white;
  border: 1px solid #ccc;
  border-radius: 4px;
  max-height: 200px;
  overflow-y: auto;
  list-style: none;
  margin: 0;
  padding: 0;
  z-index: 1000;
}

.dropdown-list li {
  padding: 8px 12px;
  cursor: pointer;
}

.dropdown-list li:hover {
  background-color: #1967D2;

}

label {
  font-weight: bold;
  display: block;
  margin-bottom: 5px;
}

.image-preview {
  margin-right: 10px;
  cursor: pointer;
}

.image-preview:hover {
  transform: scale(1.05);
}

</style>
<!-- 
<style scoped>
.dropdown-container {
  position: relative;
}

.dropdown-input {
  width: 100%;
  box-sizing: border-box;
}

.dropdown-list {
  position: absolute;
  top: 100%;
  left: 0;
  width: 100%;
  background: white;
  border: 1px solid #ccc;
  border-radius: 4px;
  max-height: 200px;
  overflow-y: auto;
  list-style: none;
  margin: 0;
  padding: 0;
  z-index: 1000;
}

.dropdown-list li {
  padding: 8px 12px;
  cursor: pointer;
}

.dropdown-list li:hover {
  background-color: #1967D2;
  color: white;
}
</style> -->
