<template>
  <div class="directory">
    <h1>Справочник: Улицы</h1>

    <div class="controls">
      <input v-model="searchQuery" @input="filterItems" placeholder="Поиск по названию..." />
      <button class="button" @click="openForm">Добавить</button>
    </div>

    <table>
      <thead>
      <tr>
        <th>Название</th>
        <th>Город</th>
        <th>Действия</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="street in filteredStreets" :key="street.id">
        <td>{{ street.name }}</td>
        <td>{{ street.city }}</td>
        <td>
          <button class="button" @click="editItem(street)">Редактировать</button>
          <button class="button" @click="deleteItem(street.id)">Удалить</button>
        </td>
      </tr>
      </tbody>
    </table>

    <div v-if="showForm" id="form-container">
      <h2>{{ editMode ? 'Редактировать' : 'Добавить' }} улицу</h2>
      <form @submit.prevent="saveItem">
        <input v-model="formData.name" placeholder="Название" name="name" required />
        <select v-model="formData.cityId" name="cityId" required>
          <option v-for="city in cities" :value="city.id" :key="city.id">
            {{ city.name }}
          </option>
        </select>
        <button class="button" type="submit">Сохранить</button>
        <button class="button" @click="closeForm">Отмена</button>
      </form>
    </div>
  </div>
</template>

<script>
import DirectoryDataService from "../services/DirectoryDataService";

export default {
  data() {
    return {
      searchQuery: "",
      streets: [],
      filteredStreets: [],
      cities: [],
      showForm: false,
      editMode: false,
      formData: { name: "", cityId: null },
    };
  },
  methods: {
    async fetchStreets() {
      const response = await DirectoryDataService.getAllStreets();
      this.streets = response.data;
      this.filteredStreets = this.streets;
    },
    async fetchCities() {
      const response = await DirectoryDataService.getAllCities();
      this.cities = response.data;
    },
    filterItems() {
      this.filteredStreets = this.streets.filter((street) =>
          street.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
    async deleteItem(id) {
      await DirectoryDataService.deleteStreet(id);
      this.fetchStreets();
    },
    scrollToForm() {
      this.$nextTick(() => {
        const formElement = document.getElementById("form-container");
        if (formElement) {
          formElement.scrollIntoView({ behavior: "smooth" });
        }
      });
    },
    openForm() {
      this.showForm = true;
      this.editMode = false;
      this.formData = { name: "", cityId: null };
      this.scrollToForm();
    },
    editItem(street) {
      this.showForm = true;
      this.editMode = true;
      this.formData = {
        id: street.id,
        name: street.name,
        cityId: street.cityId,
      };
      this.scrollToForm();
    },
    async saveItem() {
      if (this.editMode) {
        await DirectoryDataService.updateStreet(this.formData.id, this.formData);
      } else {
        await DirectoryDataService.addStreet(this.formData);
      }
      this.closeForm();
      this.fetchStreets();
    },
    closeForm() {
      this.showForm = false;
      this.formData = { name: "", cityId: null };
    },
  },
  async mounted() {
    await this.fetchStreets();
    await this.fetchCities();
  },
};
</script>

<style scoped>
.directory {
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 20px;
}
.controls {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}
table {
  width: 100%;
  border-collapse: collapse;
}
th, td {
  padding: 10px;
  border: 1px solid #ccc;
}
.button {
  margin-right: 10px;
  border-radius: 5px; 
}
</style>
