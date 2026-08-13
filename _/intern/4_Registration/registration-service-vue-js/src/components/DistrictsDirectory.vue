<template>
  <div class="directory">
    <h1>Справочник: Районы</h1>

    <div class="controls">
      <input v-model="searchQuery" @input="filterItems" placeholder="Поиск по названию..." />
      <button class="button" @click="openForm">Добавить</button>
    </div>

    <table>
      <thead>
      <tr>
        <th>Название</th>
        <th>Область</th>
        <th>Действия</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="district in filteredDistricts" :key="district.id">
        <td>{{ district.name }}</td>
        <td>{{ district.region }}</td>
        <td>
          <button class="button" @click="editItem(district)">Редактировать</button>
          <button class="button" @click="deleteItem(district.id)">Удалить</button>
        </td>
      </tr>
      </tbody>
    </table>

    <div v-if="showForm" id="form-container">
      <h2>{{ editMode ? 'Редактировать' : 'Добавить' }} район</h2>
      <form @submit.prevent="saveItem">
        <input v-model="formData.name" placeholder="Название" name="name" required />
        <select v-model="formData.regionId" name="regionId" required>
          <option v-for="region in regions" :value="region.id" :key="region.id">
            {{ region.name }}
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
      districts: [],
      filteredDistricts: [],
      regions: [],
      showForm: false,
      editMode: false,
      formData: { name: "", regionId: null },
    };
  },
  methods: {
    async fetchDistricts() {
      const response = await DirectoryDataService.getAllDistricts();
      this.districts = response.data;
      this.filteredDistricts = this.districts;
    },
    async fetchRegions() {
      const response = await DirectoryDataService.getAllRegions();
      this.regions = response.data;
    },
    filterItems() {
      this.filteredDistricts = this.districts.filter((district) =>
          district.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
    async deleteItem(id) {
      await DirectoryDataService.deleteDistrict(id);
      this.fetchDistricts();
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
      this.formData = { name: "", regionId: null };
      this.scrollToForm();
    },
    editItem(district) {
      this.showForm = true;
      this.editMode = true;
      this.formData = {
        id: district.id,
        name: district.name,
        regionId: district.regionId,
      };
      this.scrollToForm();
    },
    async saveItem() {
      if (this.editMode) {
        await DirectoryDataService.updateDistrict(this.formData.id, this.formData);
      } else {
        await DirectoryDataService.addDistrict(this.formData);
      }
      this.closeForm();
      this.fetchDistricts();
    },
    closeForm() {
      this.showForm = false;
      this.formData = { name: "", regionId: null };
    },
  },
  async mounted() {
    await this.fetchDistricts();
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
