<template>
  <div class="list row">
    <div class="col-md-8">
      <div class="input-group mb-3">
        <select class="form-control" v-model="role" @change="searchWithFilters">
          <option v-if="role === ''" value="" disabled selected>Выберите роль</option>
          <option value="ADMIN">Админ</option>
          <option value="MANAGER">Менеджер</option>
          <option value="REGISTRAR">Регистратор</option>
        </select>
        <input
            type="datetime-local"
            class="form-control"
            placeholder="Дата начала"
            v-model="startDate"
        />
        <input
            type="datetime-local"
            class="form-control"
            placeholder="Дата окончания"
            v-model="endDate"
        />
        <div class="input-group-append">
          <button class="btn btn-outline-secondary rounded me-2" type="button" @click="searchWithFilters">
            Поиск
          </button>
          <button class="btn btn-outline-danger rounded me-2" type="button" @click="resetFilters">
            Сбросить
          </button>
          <button class="btn btn-outline-success rounded" type="button" @click="downloadExcel">
            Скачать
          </button>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <h4>Список сотрудников</h4>
      <div class="list-group">
        <a
            class="list-group-item list-group-item-action " style="cursor: pointer"
            :class="{ active: index === currentIndex }"
            v-for="(user, index) in users"
            :key="user.id"
            @click="setActiveUser(user, index)"
        >
          {{ user.firstName }} {{ user.middleName }} {{ user.lastName }}
        </a>
      </div>
    </div>
    <div class="col-md-6">
      <div v-if="currentUser">
        <h4>Детали сотрудника</h4>
        <div>
          <label><strong>Полное имя:</strong></label>
          {{ currentUser.firstName }} {{ currentUser.middleName }} {{ currentUser.lastName }}
        </div>
        <div>
          <label><strong>Национальность:</strong></label> {{ currentUser.nationality }}
        </div>
        <div>
          <label><strong>Пол:</strong></label> {{ currentUser.personGender }}
        </div>
        <div class="d-flex gap-2 mt-3">
          <div class="m-3">
            <router-link :to="'/users/view/' + currentUser.id" class="badge badge-info p-2 rounded">
              Подробнее
            </router-link>
          </div>
          <div class="m-3">
            <router-link :to="'/users/edit/' + currentUser.id" class="badge badge-warning p-2 rounded">
              Редактировать
            </router-link>
          </div>
        </div>
        <div class="m-3">
          <button class="btn btn-outline-success rounded" type="button" @click="downloadAgreement(currentUser.id)">
            Пользовательское соглашение
          </button>
        </div>
      </div>
      <div v-else>
        <br/>
        <p>Пожалуйста, выберите сотрудника...</p>
      </div>
    </div>
  </div>
</template>
<script>

import UserDataService from "../services/UserDataService";

export default {
  name: "users-list",
  data() {
    return {
      users: [],
      currentUser: null,
      currentIndex: -1,
      statusId: null,
      startDate: "",
      endDate: "",
      role: ""
    };
  },
  methods: {
    retrieveUsers() {
      UserDataService.getAllUsers()
          .then((response) => {
            this.allUsers = response.data;
            this.users = this.allUsers.filter(user =>
                ["ADMIN", "MANAGER", "REGISTRAR"].includes(user.role) && user.status !== "PENDING"
            );
          })
          .catch((e) => {
            console.log(e);
          });
    },

    searchWithFilters() {
      if (this.role) {
        this.users = this.allUsers.filter(user => user.role === this.role);
      } else {
        this.users = this.allUsers.filter(user =>
            ["ADMIN", "MANAGER", "REGISTRAR"].includes(user.role)
        );
      }
    },
     fetchStatuses() {
      UserDataService.getStatuses()
        .then((response) => {
          this.statuses = response.data;
        })
        .catch((e) => {
          console.error("Error fetching statuses:", e);
        });
    },
    setActiveUser(user, index) {
      this.currentUser = user;
      this.currentIndex = index;
    },

    resetFilters() {
      this.statusId = null;
      this.startDate = "";
      this.endDate = "";
      this.role = "";
      this.retrieveUsers();
    },
    setDefaultDates() {
      const today = new Date();
      const day = today.getDay();
      const diffToMonday = today.getDate() - day + (day === 0 ? -6 : 1);
      const monday = new Date(today.setDate(diffToMonday));
      const sunday = new Date(today.setDate(monday.getDate() + 6));

      this.startDate = monday.toISOString().slice(0, 16);
      this.endDate = sunday.toISOString().slice(0, 16);
    },
    downloadExcel() {
      const clientList = this.users.map((user) => ({
        firstName: user.lastName,
        lastName: user.firstName,
        middleName: user.middleName,
        personGender: user.personGender,
        nationality: user.nationality,
        dateOfBirth: user.dateOfBirth,
        identificationNumber: user.identificationNumber,
        dateOfIssue: user.dateOfIssue,
        dateOfExpiry: user.dateOfExpiry,
        documentId: user.documentId,
        issuingAuthority: user.issuingAuthority,
        status: user.status, 
      }));

      if (!clientList.length) {
        alert("No data available for export.");
        return;
      }

      UserDataService.downloadExcel(clientList)
        .then((response) => {
          const url = window.URL.createObjectURL(new Blob([response.data]));
          const link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", "workers.xlsx");
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
        })
        .catch((e) => {
          console.error("Error during export:", e);
        });
    },
    async downloadAgreement(id) {
      try {
        const response = await UserDataService.downloadAgreement(id);
        const url = window.URL.createObjectURL(
            new Blob([response.data], {
              type: "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            })
        );
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", "user_agreement.docx");
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);
      } catch (error) {
        console.error("Error downloading agreement:", error);
      }
    },
  },
  mounted() {
    this.retrieveUsers();
    this.fetchStatuses();
    this.setDefaultDates();
  },
};
</script>
