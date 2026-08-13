import http from "../http-common";

class UserDataService {
    getAllUsers() {
        return http.get("/users/list"); // Список пользователей
    }

    downloadExcel(clientList) {
        return http.post(
            `${http.defaults.baseURL}/users/export`,
            clientList,
            {
                responseType: "blob", // Для загрузки файла
            }
        );
    }

    updateUserImages(userId, passportFace, passportBack, passportFront) {
        let formData = new FormData();
        if (passportFace) formData.append("faceImage", passportFace);
        if (passportBack) formData.append("backImage", passportBack);
        if (passportFront) formData.append("frontImage", passportFront);

        return http.put(`${http.defaults.baseURL}/users/edit-image/${userId}`, formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        });
    }

    getImage(clientType, imageType, fileName) {
        return http.get(`${http.defaults.baseURL}/users/images/${clientType}/${imageType}/${fileName}`, {
            responseType: "blob", // Указываем, что ожидаем бинарные данные
        });
    }


    getUserById(id) {
        return http.get(`/users/id/${id}`);
    }

    // Обновить пользователя
    updateUser(id, userData) {
        return http.put(`/users/edit/${id}`, userData);
    }

    searchFiltr(status, startDate, endDate) {
        const formattedStartDate = startDate ? new Date(startDate).toISOString() : undefined;
        const formattedEndDate = endDate ? new Date(endDate).toISOString() : undefined;

        return http.get("/users/search", {
            params: {
                status: status || undefined,
                startDate: formattedStartDate,
                endDate: formattedEndDate,
            },
        });
    }

    getStatuses() {
        return http.get("/users/statuses");
    }

    getRoles() {
        return http.get("/users/roles");
    }

    showRegistrationForm() {
        return http.get(`/users/add`);
    }

    registerClient(formData) {
        return http.post("/users/registration", formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        });
    }

    downloadAgreement(id) {
        return http.get(`user/agreement/${id}`, {responseType: 'blob'});
    }
}


export default new UserDataService();
