import http from "../http-common";

class RegistrarDataService {
  getAllRegistrars() {
    return http.get("/registrars/list");
  }

  getRegistrarById(id) {
    return http.get(`/registrars/id/${id}`);
  }
  
  registerStaff(formData) {
    return http.post("/api/registrars", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  }
}
 
export default new RegistrarDataService();
