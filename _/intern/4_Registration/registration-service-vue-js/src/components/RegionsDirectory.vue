<template>
  <div class="directory">
    <h1>Справочник: Области</h1>

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
      <tr v-for="region in filteredRegions" :key="region.id">
        <td>{{ region.name }}</td>
        <td>
          <button class="button" @click="editItem(region)">Редактировать</button>
          <button class="button" @click="deleteItem(region.id)">Удалить</button>
        </td>
      </tr>
      </tbody>
    </table>

    <div v-if="showForm" id="form-container">
      <h2>{{ editMode ? 'Редактировать' : 'Добавить' }} область</h2>
      <form @submit.prevent="saveItem">
        <input v-model="formData.name" placeholder="Название" name="name" required />
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
      regions: [],
      filteredRegions: [],
      showForm: false,
      editMode: false,
      formData: { name: "" },
    };
  },
  methods: {
    async fetchRegions() {
      const response = await DirectoryDataService.getAllRegions();
      this.regions = response.data;
      this.filteredRegions = this.regions;
    },
    filterItems() {
      this.filteredRegions = this.regions.filter((region) =>
          region.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
    async deleteItem(id) {
      await DirectoryDataService.deleteRegion(id);
      this.fetchRegions();
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
      this.formData = { name: "" };
      this.scrollToForm();
    },
    editItem(region) {
      this.showForm = true;
      this.editMode = true;
      this.formData = { id: region.id, name: region.name };
      this.scrollToForm();
    },
    async saveItem() {
      if (this.editMode) {
        await DirectoryDataService.updateRegion(this.formData.id, this.formData);
      } else {
        await DirectoryDataService.addRegion(this.formData);
      }
      this.closeForm();
      this.fetchRegions();
    },
    closeForm() {
      this.showForm = false;
      this.formData = { name: "" };
    },
  },
  async mounted() {
    await this.fetchRegions();
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
