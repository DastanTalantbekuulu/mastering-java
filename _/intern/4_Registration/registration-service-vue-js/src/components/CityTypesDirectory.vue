<template>
  <div class="directory">
    <h1>Справочник: Типы населенных пунктов</h1>

    <div class="controls">
      <input v-model="searchQuery" @input="filterItems" placeholder="Поиск по названию..." />
      <button class="button" @click="openForm">Добавить</button>
    </div>

    <table>
      <thead>
      <tr>
        <th>Название</th>
        <th>Действия</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="type in filteredCityTypes" :key="type.id">
        <td>{{ type.name }}</td>
        <td>
          <button class="button" @click="editItem(type)">Редактировать</button>
          <button class="button" @click="deleteItem(type.id)">Удалить</button>
        </td>
      </tr>
      </tbody>
    </table>

    <div v-if="showForm" id="form-container">
      <h2>{{ editMode ? 'Редактировать' : 'Добавить' }} тип населенного пункта</h2>
      <form @submit.prevent="saveItem">
        <input v-model="formData.name" placeholder="Название" name="name" required />
        <div>
          <label>
            <input
              type="checkbox"
              v-model="formData.requiresRegion"
            />
            Требуется область
          </label>
        </div>
        <div>
          <label>
            <input
              type="checkbox"
              v-model="formData.requiresDistrict"
            />
            Требуется район
          </label>
        </div>

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
      cityTypes: [],
      filteredCityTypes: [],
      showForm: false,
      editMode: false,
      formData: { 
        name: "",
        requiresRegion: false,
        requiresDistrict: false,
       },
    };
  },
  methods: {
    async fetchCityTypes() {
      const response = await DirectoryDataService.getAllCityTypes();
      this.cityTypes = response.data;
      this.filteredCityTypes = this.cityTypes;
    },
    filterItems() {
      this.filteredCityTypes = this.cityTypes.filter((type) =>
          type.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
    async deleteItem(id) {
      await DirectoryDataService.deleteCityType(id);
      this.fetchCityTypes();
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
      this.formData = { 
        name: "",
        requiresRegion: false,
        requiresDistrict: false,
       };
       this.scrollToForm();
      },
    editItem(type) {
      this.showForm = true;
      this.editMode = true;
      this.formData = { 
        id: type.id, 
        name: type.name,
        requiresRegion: type.requiresRegion,
        requiresDistrict: type.requiresDistrict, 
      };
      this.scrollToForm();
    },
    async saveItem() {
      if (this.editMode) {
        await DirectoryDataService.updateCityType(this.formData.id, this.formData);
      } else {
        await DirectoryDataService.addCityType(this.formData);
      }
      this.closeForm();
      this.fetchCityTypes();
    },
    closeForm() {
      this.showForm = false;
      this.formData = { 
        name: "",
        requiresRegion: false,
        requiresDistrict: false,
       };
    },
  },
  async mounted() {
    await this.fetchCityTypes();
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
form div {
  margin-bottom: 10px;
}

label {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 16px;
}
</style>
