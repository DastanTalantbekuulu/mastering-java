import http from "../http-common";

class DirectoryDataService {
    // ------------------- Населенные пункты -------------------

    getAllCities() {
        return http.get("/directory/cities");
    }

    addCity(data) {
        return http.post("/directory/cities", data);
    }

    updateCity(id, data) {
        return http.put(`/directory/cities/id/${id}`, data);
    }

    deleteCity(id) {
        return http.delete(`/directory/cities/id/${id}`);
    }

    // ------------------- Области -------------------

    getAllRegions() {
        return http.get("/directory/regions");
    }

    addRegion(data) {
        return http.post("/directory/regions", data);
    }

    updateRegion(id, data) {
        return http.put(`/directory/regions/id/${id}`, data);
    }

    deleteRegion(id) {
        return http.delete(`/directory/regions/id/${id}`);
    }

    // ------------------- Районы -------------------

    getAllDistricts() {
        return http.get("/directory/districts");
    }

    addDistrict(data) {
        return http.post("/directory/districts", data);
    }

    updateDistrict(id, data) {
        return http.put(`/directory/districts/id/${id}`, data);
    }

    deleteDistrict(id) {
        return http.delete(`/directory/districts/id/${id}`);
    }

    // ------------------- Типы населенных пунктов -------------------

    async getAllCityTypes() {
        return http.get("/directory/city-types");
    }

    async addCityType(data) {
        return http.post("/directory/city-types", data);
    }

    async updateCityType(id, data) {
        return http.put(`/directory/city-types/id/${id}`, data);
    }

    async deleteCityType(id) {
        return http.delete(`/directory/city-types/id/${id}`);
    }

    // ------------------- Улицы -------------------

    async getAllStreets() {
        return http.get("/directory/streets");
    }

    async addStreet(data) {
        return http.post("/directory/streets", data);
    }

    async updateStreet(id, data) {
        return http.put(`/directory/streets/id/${id}`, data);
    }

    async deleteStreet(id) {
        return http.delete(`/directory/streets/id/${id}`);
    }

}

export default new DirectoryDataService();
