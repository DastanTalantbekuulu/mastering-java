import http from "../http-common";

class WaitingListService {
    getClients() {
        return http.get("/waiting-list/client");
    }

    getEmployees() {
        return http.get("/waiting-list/staff");
    }

    approvePerson(id) {
        return http.post(`/approve/${id}`);
    }

    rejectPerson(id) {
        return http.post(`/reject/${id}`);
    }
}

export default new WaitingListService();
