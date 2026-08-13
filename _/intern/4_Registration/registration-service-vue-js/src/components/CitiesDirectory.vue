<template>
  <div class="directory">
    <h1>Справочник: Населенные пункты</h1>

    <div class="controls">
      <input v-model="searchQuery" @input="filterItems" placeholder="Поиск по названию..." />
      <button class="button" @click="openForm">Добавить</button>
    </div>

    <table>
      <thead>
      <tr>
        <th>Название</th>
        <th>Тип</th>
        <th>Область</th>
        <th>Район</th>
        <th>Действия</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="city in filteredCities" :key="city.id">
        <td>{{ city.name }}</td>
        <td>{{ city.cityType }}</td>
        <td>{{ city.region }}</td>
        <td>{{ city.district }}</td>
        <td>
          <button class="button" @click="editItem(city)">Редактировать</button>
          <button class="button" @click="deleteItem(city.id)">Удалить</button>
        </td>
      </tr>
      </tbody>
    </table>

    <div v-if="showForm" id="form-container">
      <h2>{{ editMode ? 'Редактировать' : 'Добавить' }} населенный пункт</h2>
      <form @submit.prevent="saveItem">
        <input v-model="formData.name" placeholder="Название" name="name" required />
        <select v-model="formData.cityTypeId" name="cityTypeId" required>
          <option v-for="type in cityTypes" :value="type.id" :key="type.id">
            {{ type.name }}
          </option>
        </select>
        <select v-model="formData.regionId" name="regionId" @change="loadDistricts">
          <option v-for="region in regions" :value="region.id" :key="region.id">
            {{ region.name }}
          </option>
        </select>
        <select v-model="formData.districtId" name="districtId">
          <option v-for="district in filteredDistricts" :value="district.id" :key="district.id">
            {{ district.name }}
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
      cities: [],
      filteredCities: [],
      regions: [],
      districts: [],
      filteredDistricts: [],
      cityTypes: [],
      showForm: false,
      editMode: false,
      formData: { name: "", cityTypeId: null, regionId: null, districtId: null },
    };
  },
  methods: {
    async fetchCities() {
      const response = await DirectoryDataService.getAllCities();
      this.cities = response.data;
      this.filteredCities = this.cities;
    },
    async fetchRegions() {
      const response = await DirectoryDataService.getAllRegions();
      this.regions = response.data;
    },
    async fetchDistricts() {
      const response = await DirectoryDataService.getAllDistricts();
      this.districts = response.data;
      this.filteredDistricts = this.districts;
    },
    async fetchCityTypes() {
      const response = await DirectoryDataService.getAllCityTypes();
      this.cityTypes = response.data;
    },
    filterItems() {
      this.filteredCities = this.cities.filter((city) =>
          city.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
    async deleteItem(id) {
      await DirectoryDataService.deleteCity(id);
      this.fetchCities();
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
      this.formData = { name: "", cityTypeId: null, regionId: null, districtId: null };
      this.scrollToForm();
    },
    editItem(city) {
      this.showForm = true;
      this.editMode = true;
      this.formData = {
        id: city.id,
        name: city.name,
        cityTypeId: city.cityTypeId,
        regionId: city.regionId,
        districtId: city.districtId,
      };
      this.loadDistricts();
      this.scrollToForm();
    },
    async saveItem() {
      if (this.editMode) {
        await DirectoryDataService.updateCity(this.formData.id, this.formData);
      } else {
        await DirectoryDataService.addCity(this.formData);
      }
      this.closeForm();
      this.fetchCities();
    },
    closeForm() {
      this.showForm = false;
      this.formData = { name: "", cityTypeId: null, regionId: null, districtId: null };
    },
    loadDistricts() {
      this.filteredDistricts = this.districts.filter(
          (district) => district.regionId === this.formData.regionId
      );
    },
  },
  async mounted() {
    await this.fetchCities();
    await this.fetchRegions();
    await this.fetchDistricts();
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
</style>
